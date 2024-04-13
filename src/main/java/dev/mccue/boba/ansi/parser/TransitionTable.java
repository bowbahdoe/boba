package dev.mccue.boba.ansi.parser;

import dev.mccue.boba.Charm;

import java.util.HashMap;
import java.util.List;

@Charm("https://github.com/charmbracelet/x/blob/main/exp/term/ansi/parser/transition_table.go")
public final class TransitionTable {

    private record Key(State state, byte code) {}

    private final HashMap<Key, Transition> table;
    public static final TransitionTable DEFAULT_TABLE = TransitionTable.generate();

    TransitionTable() {
        this.table = new HashMap<>();
    }

    void addOne(byte code, State state, Action action, State next) {
        table.put(new Key(state, code), new Transition(next, action));
    }

    void addOne(int code, State state, Action action, State next) {
        addOne((byte) code, state, action, next);
    }

    void addMany(int[] codes, State state, Action action, State next) {
        for (var code : codes) {
            addOne(code, state, action, next);
        }
    }

    void addRange(int start, int end, State state, Action action, State next) {
        for (var i = start; i <= end; i++) {
            addOne(i, state, action, next);
        }
    }

    public Transition transition(State state, byte code) {
        return table.getOrDefault(new Key(state, code), new Transition(State.GROUND, Action.NONE));
    }

    public static TransitionTable generate() {
        var table = new TransitionTable();

        // Anywhere
        for (var state : State.values()) {
            // Anywhere -> Ground
            table.addMany(new int[]{0x18, 0x1a, 0x99, 0x9a}, state, Action.EXECUTE, State.GROUND);
            table.addRange(0x80, 0x8F, state, Action.EXECUTE, State.GROUND);
            table.addRange(0x90, 0x97, state, Action.EXECUTE, State.GROUND);
            table.addOne(0x9C, state, Action.IGNORE, State.GROUND);
            // Anywhere -> Escape
            table.addOne(0x1B, state, Action.CLEAR, State.ESCAPE);
            // Anywhere -> SosStringState
            table.addOne(0x98, state, Action.START, State.SOS_STRING);
            // Anywhere -> PmStringState
            table.addOne(0x9E, state, Action.START, State.PM_STRING);
            // Anywhere -> ApcStringState
            table.addOne(0x9F, state, Action.START, State.APC_STRING);
            // Anywhere -> CsiEntry
            table.addOne(0x9B, state, Action.CLEAR, State.CSI_ENTRY);
            // Anywhere -> DcsEntry
            table.addOne(0x90, state, Action.CLEAR, State.DCS_ENTRY);
            // Anywhere -> OscString
            table.addOne(0x9D, state, Action.START, State.OSC_STRING);
            // Anywhere -> Utf8
            table.addRange(0xC2, 0xDF, state, Action.PRINT, State.UTF8); // UTF8 2 byte sequence
            table.addRange(0xE0, 0xEF, state, Action.PRINT, State.UTF8); // UTF8 3 byte sequence
            table.addRange(0xF0, 0xF4, state, Action.PRINT, State.UTF8); // UTF8 4 byte sequence
        }
        // Ground
        table.addRange(0x00, 0x17, State.GROUND, Action.EXECUTE, State.GROUND);
        table.addOne(0x19, State.GROUND, Action.EXECUTE, State.GROUND);
        table.addRange(0x1C, 0x1F, State.GROUND, Action.EXECUTE, State.GROUND);
        table.addRange(0x20, 0x7F, State.GROUND, Action.PRINT, State.GROUND);

        // EscapeIntermediate
        table.addRange(0x00, 0x17, State.ESCAPE_INTERMEDIATE, Action.EXECUTE, State.ESCAPE_INTERMEDIATE);
        table.addOne(0x19, State.ESCAPE_INTERMEDIATE, Action.EXECUTE, State.ESCAPE_INTERMEDIATE);
        table.addRange(0x1C, 0x1F, State.ESCAPE_INTERMEDIATE, Action.EXECUTE, State.ESCAPE_INTERMEDIATE);
        table.addRange(0x20, 0x2F, State.ESCAPE_INTERMEDIATE, Action.COLLECT, State.ESCAPE_INTERMEDIATE);
        table.addOne(0x7F, State.ESCAPE_INTERMEDIATE, Action.IGNORE, State.ESCAPE_INTERMEDIATE);
        // EscapeIntermediate -> Ground
        table.addRange(0x30, 0x7E, State.ESCAPE_INTERMEDIATE, Action.DISPATCH, State.GROUND);

        // Escape
        table.addRange(0x00, 0x17, State.ESCAPE, Action.EXECUTE, State.ESCAPE);
        table.addOne(0x19, State.ESCAPE, Action.EXECUTE, State.ESCAPE);
        table.addRange(0x1C, 0x1F, State.ESCAPE, Action.EXECUTE, State.ESCAPE);
        table.addOne(0x7F, State.ESCAPE, Action.IGNORE, State.ESCAPE);
        // Escape -> Ground
        table.addRange(0x30, 0x4F, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addRange(0x51, 0x57, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addOne(0x59, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addOne(0x5A, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addOne(0x5C, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addRange(0x60, 0x7E, State.ESCAPE, Action.DISPATCH, State.GROUND);
        // Escape -> Escape_intermediate
        table.addRange(0x20, 0x2F, State.ESCAPE, Action.COLLECT, State.ESCAPE_INTERMEDIATE);
        // Escape -> Sos_pm_apc_string
        table.addOne('X', State.ESCAPE, Action.START, State.SOS_STRING); // SOS
        table.addOne('^', State.ESCAPE, Action.START, State.PM_STRING);  // PM
        table.addOne('_', State.ESCAPE, Action.START, State.APC_STRING); // APC
        // Escape -> Dcs_entry
        table.addOne('P', State.ESCAPE, Action.CLEAR, State.APC_STRING);
        // Escape -> Csi_entry
        table.addOne('[', State.ESCAPE, Action.CLEAR, State.CSI_ENTRY);
        // Escape -> Osc_string
        table.addOne(']', State.ESCAPE, Action.START, State.OSC_STRING);

        // Sos_pm_apc_string
        for (var state : List.of(
                State.SOS_STRING,
                State.PM_STRING,
                State.APC_STRING
        )) {
            table.addRange(0x00, 0x17, state, Action.PUT, state);
            table.addOne(0x19, state, Action.PUT, state);
            table.addRange(0x1C, 0x1F, state, Action.PUT, state);
            table.addRange(0x20, 0x7F, state, Action.PUT, state);
            // ESC, ST, CAN, and SUB terminate the sequence
            table.addOne(0x1B, state, Action.DISPATCH, State.ESCAPE);
            table.addOne(0x9C, state, Action.DISPATCH, State.GROUND);
            table.addMany(new int[]{0x18, 0x1A}, state, Action.IGNORE, State.GROUND);
        }

        // Dcs_entry
        table.addRange(0x00, 0x07, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        table.addRange(0x0E, 0x17, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        table.addOne(0x19, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        table.addRange(0x1C, 0x1F, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        table.addOne(0x7F, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        // Dcs_entry -> Dcs_intermediate
        table.addRange(0x20, 0x2F, State.DCS_ENTRY, Action.COLLECT, State.DCS_INTERMEDIATE);
        // Dcs_entry -> Dcs_param
        table.addRange(0x30, 0x3B, State.DCS_ENTRY, Action.PARAM, State.DCS_PARAM);
        table.addRange(0x3C, 0x3F, State.DCS_ENTRY, Action.MARKER, State.DCS_PARAM);
        // Dcs_entry -> Dcs_passthrough
        table.addRange(0x08, 0x0D, State.DCS_ENTRY, Action.PUT, State.DCS_STRING); // Follows ECMA-48 § 8.3.27
        // XXX: allows passing ESC (not a ECMA-48 standard) this to allow for
        // passthrough of ANSI sequences like in Screen or Tmux passthrough mode.
        table.addOne(0x1B, State.DCS_ENTRY, Action.PUT, State.DCS_STRING);
        table.addRange(0x40, 0x7E, State.DCS_ENTRY, Action.START, State.DCS_STRING);

        // Dcs_intermediate
        table.addRange(0x00, 0x17, State.DCS_INTERMEDIATE, Action.IGNORE, State.DCS_INTERMEDIATE);
        table.addOne(0x19, State.DCS_INTERMEDIATE, Action.IGNORE, State.DCS_INTERMEDIATE);
        table.addRange(0x1C, 0x1F, State.DCS_INTERMEDIATE, Action.IGNORE, State.DCS_INTERMEDIATE);
        table.addRange(0x20, 0x2F, State.DCS_INTERMEDIATE, Action.COLLECT, State.DCS_INTERMEDIATE);
        table.addOne(0x7F, State.DCS_INTERMEDIATE, Action.IGNORE, State.DCS_INTERMEDIATE);
        // Dcs_intermediate -> Dcs_passthrough
        table.addRange(0x30, 0x3F, State.DCS_INTERMEDIATE, Action.START, State.DCS_STRING);
        table.addRange(0x40, 0x7E, State.DCS_INTERMEDIATE, Action.START, State.DCS_STRING);

        // Dcs_param
        table.addRange(0x00, 0x17, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        table.addOne(0x19, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        table.addRange(0x1C, 0x1F, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        table.addRange(0x30, 0x3B, State.DCS_PARAM, Action.PARAM, State.DCS_PARAM);
        table.addOne(0x7F, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        table.addRange(0x3C, 0x3F, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        // Dcs_param -> Dcs_intermediate
        table.addRange(0x20, 0x2F, State.DCS_PARAM, Action.COLLECT, State.DCS_INTERMEDIATE);
        // Dcs_param -> Dcs_passthrough
        table.addRange(0x40, 0x7E, State.DCS_PARAM, Action.START, State.DCS_STRING);

        // Dcs_passthrough
        table.addRange(0x00, 0x17, State.DCS_STRING, Action.PUT, State.DCS_STRING);
        table.addOne(0x19, State.DCS_STRING, Action.PUT, State.DCS_STRING);
        table.addRange(0x1C, 0x1F, State.DCS_STRING, Action.PUT, State.DCS_STRING);
        table.addRange(0x20, 0x7E, State.DCS_STRING, Action.PUT, State.DCS_STRING);
        table.addOne(0x7F, State.DCS_STRING, Action.IGNORE, State.DCS_STRING);
        table.addRange(0x80, 0xFF, State.DCS_STRING, Action.PUT, State.DCS_STRING); // Allow Utf8 characters by extending the printable range from 0x7F to 0xFF
        // ST, CAN, SUB, and ESC terminate the sequence
        table.addOne(0x1B, State.DCS_STRING, Action.DISPATCH, State.ESCAPE);
        table.addOne(0x9C, State.DCS_STRING, Action.DISPATCH, State.GROUND);
        table.addMany(new int[]{0x18, 0x1A}, State.DCS_STRING, Action.IGNORE, State.GROUND);

        // Csi_param
        table.addRange(0x00, 0x17, State.CSI_PARAM, Action.EXECUTE, State.CSI_PARAM);
        table.addOne(0x19, State.CSI_PARAM, Action.EXECUTE, State.CSI_PARAM);
        table.addRange(0x1C, 0x1F, State.CSI_PARAM, Action.EXECUTE, State.CSI_PARAM);
        table.addRange(0x30, 0x3B, State.CSI_PARAM, Action.PARAM, State.CSI_PARAM);
        table.addOne(0x7F, State.CSI_PARAM, Action.IGNORE, State.CSI_PARAM);
        table.addRange(0x3C, 0x3F, State.CSI_PARAM, Action.IGNORE, State.CSI_PARAM);
        // Csi_param -> Ground
        table.addRange(0x40, 0x7E, State.CSI_PARAM, Action.DISPATCH, State.GROUND);
        // Csi_param -> Csi_intermediate
        table.addRange(0x20, 0x2F, State.CSI_PARAM, Action.COLLECT, State.CSI_INTERMEDIATE);

        // Csi_intermediate
        table.addRange(0x00, 0x17, State.CSI_INTERMEDIATE, Action.EXECUTE, State.CSI_INTERMEDIATE);
        table.addOne(0x19, State.CSI_INTERMEDIATE, Action.EXECUTE, State.CSI_INTERMEDIATE);
        table.addRange(0x1C, 0x1F, State.CSI_INTERMEDIATE, Action.EXECUTE, State.CSI_INTERMEDIATE);
        table.addRange(0x20, 0x2F, State.CSI_INTERMEDIATE, Action.COLLECT, State.CSI_INTERMEDIATE);
        table.addOne(0x7F, State.CSI_INTERMEDIATE, Action.IGNORE, State.CSI_INTERMEDIATE);
        // Csi_intermediate -> Ground
        table.addRange(0x40, 0x7E, State.CSI_INTERMEDIATE, Action.DISPATCH, State.GROUND);
        // Csi_intermediate -> Csi_ignore
        table.addRange(0x30, 0x3F, State.CSI_INTERMEDIATE, Action.IGNORE, State.GROUND);

        // Csi_entry
        table.addRange(0x00, 0x17, State.CSI_ENTRY, Action.EXECUTE, State.CSI_ENTRY);
        table.addOne(0x19, State.CSI_ENTRY, Action.EXECUTE, State.CSI_ENTRY);
        table.addRange(0x1C, 0x1F, State.CSI_ENTRY, Action.EXECUTE, State.CSI_ENTRY);
        table.addOne(0x7F, State.CSI_ENTRY, Action.IGNORE, State.CSI_ENTRY);
        // Csi_entry -> Ground
        table.addRange(0x40, 0x7E, State.CSI_ENTRY, Action.DISPATCH, State.GROUND);
        // Csi_entry -> Csi_intermediate
        table.addRange(0x20, 0x2F, State.CSI_ENTRY, Action.COLLECT, State.CSI_INTERMEDIATE);
        // Csi_entry -> Csi_param
        table.addRange(0x30, 0x3B, State.CSI_ENTRY, Action.PARAM, State.CSI_PARAM);
        table.addRange(0x3C, 0x3F, State.CSI_ENTRY, Action.MARKER, State.CSI_PARAM);

        // Osc_string
        table.addRange(0x00, 0x06, State.OSC_STRING, Action.IGNORE, State.OSC_STRING);
        table.addRange(0x08, 0x17, State.OSC_STRING, Action.IGNORE, State.OSC_STRING);
        table.addOne(0x19, State.OSC_STRING, Action.IGNORE, State.OSC_STRING);
        table.addRange(0x1C, 0x1F, State.OSC_STRING, Action.IGNORE, State.OSC_STRING);
        table.addRange(0x20, 0xFF, State.OSC_STRING, Action.PUT, State.OSC_STRING); // Allow Utf8 characters by extending the printable range from 0x7F to 0xFF

        // ST, CAN, SUB, ESC, and BEL terminate the sequence
        table.addOne(0x1B, State.OSC_STRING, Action.DISPATCH, State.ESCAPE);
        table.addOne(0x07, State.OSC_STRING, Action.DISPATCH, State.GROUND);
        table.addOne(0x9C, State.OSC_STRING, Action.DISPATCH, State.GROUND);
        table.addMany(new int[]{0x18, 0x1A}, State.OSC_STRING, Action.IGNORE, State.GROUND);


        return table;
    }

}
