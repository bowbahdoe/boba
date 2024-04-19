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

    void addMany(byte[] codes, State state, Action action, State next) {
        for (var code : codes) {
            addOne(code, state, action, next);
        }
    }

    void addRange(byte start, byte end, State state, Action action, State next) {
        for (var i = Byte.toUnsignedInt(start); i < Byte.toUnsignedInt(end); i++) {
            addOne((byte) i, state, action, next);
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
            table.addMany(new byte[]{(byte) 0x18, (byte) 0x1a, (byte) 0x99, (byte) 0x9a}, state, Action.EXECUTE, State.GROUND);
            table.addRange((byte) 0x80, (byte) 0x8F, state, Action.EXECUTE, State.GROUND);
            table.addRange((byte) 0x90, (byte) 0x97, state, Action.EXECUTE, State.GROUND);
            table.addOne((byte) 0x9C, state, Action.IGNORE, State.GROUND);
            // Anywhere -> Escape
            table.addOne((byte) 0x1B, state, Action.CLEAR, State.ESCAPE);
            // Anywhere -> SosStringState
            table.addOne((byte) 0x98, state, Action.START, State.SOS_STRING);
            // Anywhere -> PmStringState
            table.addOne((byte) 0x9E, state, Action.START, State.PM_STRING);
            // Anywhere -> ApcStringState
            table.addOne((byte) 0x9F, state, Action.START, State.APC_STRING);
            // Anywhere -> CsiEntry
            table.addOne((byte) 0x9B, state, Action.CLEAR, State.CSI_ENTRY);
            // Anywhere -> DcsEntry
            table.addOne((byte) 0x90, state, Action.CLEAR, State.DCS_ENTRY);
            // Anywhere -> OscString
            table.addOne((byte) 0x9D, state, Action.START, State.OSC_STRING);
            // Anywhere -> Utf8
            table.addRange((byte) 0xC2, (byte) 0xDF, state, Action.PRINT, State.UTF8); // UTF8 2 byte sequence
            table.addRange((byte) 0xE0, (byte) 0xEF, state, Action.PRINT, State.UTF8); // UTF8 3 byte sequence
            table.addRange((byte) 0xF0, (byte) 0xF4, state, Action.PRINT, State.UTF8); // UTF8 4 byte sequence
        }
        // Ground
        table.addRange((byte) 0x00, (byte) 0x17, State.GROUND, Action.EXECUTE, State.GROUND);
        table.addOne((byte) 0x19, State.GROUND, Action.EXECUTE, State.GROUND);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.GROUND, Action.EXECUTE, State.GROUND);
        table.addRange((byte) 0x20, (byte) 0x7F, State.GROUND, Action.PRINT, State.GROUND);

        // EscapeIntermediate
        table.addRange((byte) 0x00, (byte) 0x17, State.ESCAPE_INTERMEDIATE, Action.EXECUTE, State.ESCAPE_INTERMEDIATE);
        table.addOne((byte) 0x19, State.ESCAPE_INTERMEDIATE, Action.EXECUTE, State.ESCAPE_INTERMEDIATE);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.ESCAPE_INTERMEDIATE, Action.EXECUTE, State.ESCAPE_INTERMEDIATE);
        table.addRange((byte) 0x20, (byte) 0x2F, State.ESCAPE_INTERMEDIATE, Action.COLLECT, State.ESCAPE_INTERMEDIATE);
        table.addOne((byte) 0x7F, State.ESCAPE_INTERMEDIATE, Action.IGNORE, State.ESCAPE_INTERMEDIATE);
        // EscapeIntermediate -> Ground
        table.addRange((byte) 0x30, (byte) 0x7E, State.ESCAPE_INTERMEDIATE, Action.DISPATCH, State.GROUND);

        // Escape
        table.addRange((byte) 0x00, (byte) 0x17, State.ESCAPE, Action.EXECUTE, State.ESCAPE);
        table.addOne((byte) 0x19, State.ESCAPE, Action.EXECUTE, State.ESCAPE);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.ESCAPE, Action.EXECUTE, State.ESCAPE);
        table.addOne((byte) 0x7F, State.ESCAPE, Action.IGNORE, State.ESCAPE);
        // Escape -> Ground
        table.addRange((byte) 0x30, (byte) 0x4F, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addRange((byte) 0x51, (byte) 0x57, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addOne((byte) 0x59, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addOne((byte) 0x5A, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addOne((byte) 0x5C, State.ESCAPE, Action.DISPATCH, State.GROUND);
        table.addRange((byte) 0x60, (byte) 0x7E, State.ESCAPE, Action.DISPATCH, State.GROUND);
        // Escape -> Escape_intermediate
        table.addRange((byte) 0x20, (byte) 0x2F, State.ESCAPE, Action.COLLECT, State.ESCAPE_INTERMEDIATE);
        // Escape -> Sos_pm_apc_string
        table.addOne((byte) 'X', State.ESCAPE, Action.START, State.SOS_STRING); // SOS
        table.addOne((byte) '^', State.ESCAPE, Action.START, State.PM_STRING);  // PM
        table.addOne((byte) '_', State.ESCAPE, Action.START, State.APC_STRING); // APC
        // Escape -> Dcs_entry
        table.addOne((byte) 'P', State.ESCAPE, Action.CLEAR, State.APC_STRING);
        // Escape -> Csi_entry
        table.addOne((byte) '[', State.ESCAPE, Action.CLEAR, State.CSI_ENTRY);
        // Escape -> Osc_string
        table.addOne((byte) ']', State.ESCAPE, Action.START, State.OSC_STRING);

        // Sos_pm_apc_string
        for (var state : List.of(
                State.SOS_STRING,
                State.PM_STRING,
                State.APC_STRING
        )) {
            table.addRange((byte) 0x00, (byte) 0x17, state, Action.PUT, state);
            table.addOne((byte) 0x19, state, Action.PUT, state);
            table.addRange((byte) 0x1C, (byte) 0x1F, state, Action.PUT, state);
            table.addRange((byte) 0x20, (byte) 0x7F, state, Action.PUT, state);
            // ESC, ST, CAN, and SUB terminate the sequence
            table.addOne((byte) 0x1B, state, Action.DISPATCH, State.ESCAPE);
            table.addOne((byte) 0x9C, state, Action.DISPATCH, State.GROUND);
            table.addMany(new byte[]{(byte) 0x18, (byte) 0x1A}, state, Action.IGNORE, State.GROUND);
        }

        // Dcs_entry
        table.addRange((byte) 0x00, (byte) 0x07, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        table.addRange((byte) 0x0E, (byte) 0x17, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        table.addOne((byte) 0x19, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        table.addOne((byte) 0x7F, State.DCS_ENTRY, Action.IGNORE, State.DCS_ENTRY);
        // Dcs_entry -> Dcs_intermediate
        table.addRange((byte) 0x20, (byte) 0x2F, State.DCS_ENTRY, Action.COLLECT, State.DCS_INTERMEDIATE);
        // Dcs_entry -> Dcs_param
        table.addRange((byte) 0x30, (byte) 0x3B, State.DCS_ENTRY, Action.PARAM, State.DCS_PARAM);
        table.addRange((byte) 0x3C, (byte) 0x3F, State.DCS_ENTRY, Action.MARKER, State.DCS_PARAM);
        // Dcs_entry -> Dcs_passthrough
        table.addRange((byte) 0x08, (byte) 0x0D, State.DCS_ENTRY, Action.PUT, State.DCS_STRING); // Follows ECMA-48 § 8.3.27
        // XXX: allows passing ESC (not a ECMA-48 standard) this to allow for
        // passthrough of ANSI sequences like in Screen or Tmux passthrough mode.
        table.addOne((byte) 0x1B, State.DCS_ENTRY, Action.PUT, State.DCS_STRING);
        table.addRange((byte) 0x40, (byte) 0x7E, State.DCS_ENTRY, Action.START, State.DCS_STRING);

        // Dcs_intermediate
        table.addRange((byte) 0x00, (byte) 0x17, State.DCS_INTERMEDIATE, Action.IGNORE, State.DCS_INTERMEDIATE);
        table.addOne((byte) 0x19, State.DCS_INTERMEDIATE, Action.IGNORE, State.DCS_INTERMEDIATE);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.DCS_INTERMEDIATE, Action.IGNORE, State.DCS_INTERMEDIATE);
        table.addRange((byte) 0x20, (byte) 0x2F, State.DCS_INTERMEDIATE, Action.COLLECT, State.DCS_INTERMEDIATE);
        table.addOne((byte) 0x7F, State.DCS_INTERMEDIATE, Action.IGNORE, State.DCS_INTERMEDIATE);
        // Dcs_intermediate -> Dcs_passthrough
        table.addRange((byte) 0x30, (byte) 0x3F, State.DCS_INTERMEDIATE, Action.START, State.DCS_STRING);
        table.addRange((byte) 0x40, (byte) 0x7E, State.DCS_INTERMEDIATE, Action.START, State.DCS_STRING);

        // Dcs_param
        table.addRange((byte) 0x00, (byte) 0x17, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        table.addOne((byte) 0x19, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        table.addRange((byte) 0x30, (byte) 0x3B, State.DCS_PARAM, Action.PARAM, State.DCS_PARAM);
        table.addOne((byte) 0x7F, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        table.addRange((byte) 0x3C, (byte) 0x3F, State.DCS_PARAM, Action.IGNORE, State.DCS_PARAM);
        // Dcs_param -> Dcs_intermediate
        table.addRange((byte) 0x20, (byte) 0x2F, State.DCS_PARAM, Action.COLLECT, State.DCS_INTERMEDIATE);
        // Dcs_param -> Dcs_passthrough
        table.addRange((byte) 0x40, (byte) 0x7E, State.DCS_PARAM, Action.START, State.DCS_STRING);

        // Dcs_passthrough
        table.addRange((byte) 0x00, (byte) 0x17, State.DCS_STRING, Action.PUT, State.DCS_STRING);
        table.addOne((byte) 0x19, State.DCS_STRING, Action.PUT, State.DCS_STRING);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.DCS_STRING, Action.PUT, State.DCS_STRING);
        table.addRange((byte) 0x20, (byte) 0x7E, State.DCS_STRING, Action.PUT, State.DCS_STRING);
        table.addOne((byte) 0x7F, State.DCS_STRING, Action.IGNORE, State.DCS_STRING);
        table.addRange((byte) 0x80, (byte) 0xFF, State.DCS_STRING, Action.PUT, State.DCS_STRING); // Allow Utf8 characters by extending the printable range from 0x7F to 0xFF
        // ST, CAN, SUB, and ESC terminate the sequence
        table.addOne((byte) 0x1B, State.DCS_STRING, Action.DISPATCH, State.ESCAPE);
        table.addOne((byte) 0x9C, State.DCS_STRING, Action.DISPATCH, State.GROUND);
        table.addMany(new byte[]{(byte) 0x18, (byte) 0x1A}, State.DCS_STRING, Action.IGNORE, State.GROUND);

        // Csi_param
        table.addRange((byte) 0x00, (byte) 0x17, State.CSI_PARAM, Action.EXECUTE, State.CSI_PARAM);
        table.addOne((byte) 0x19, State.CSI_PARAM, Action.EXECUTE, State.CSI_PARAM);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.CSI_PARAM, Action.EXECUTE, State.CSI_PARAM);
        table.addRange((byte) 0x30, (byte) 0x3B, State.CSI_PARAM, Action.PARAM, State.CSI_PARAM);
        table.addOne((byte) 0x7F, State.CSI_PARAM, Action.IGNORE, State.CSI_PARAM);
        table.addRange((byte) 0x3C, (byte) 0x3F, State.CSI_PARAM, Action.IGNORE, State.CSI_PARAM);
        // Csi_param -> Ground
        table.addRange((byte) 0x40, (byte) 0x7E, State.CSI_PARAM, Action.DISPATCH, State.GROUND);
        // Csi_param -> Csi_intermediate
        table.addRange((byte) 0x20, (byte) 0x2F, State.CSI_PARAM, Action.COLLECT, State.CSI_INTERMEDIATE);

        // Csi_intermediate
        table.addRange((byte) 0x00, (byte) 0x17, State.CSI_INTERMEDIATE, Action.EXECUTE, State.CSI_INTERMEDIATE);
        table.addOne((byte) 0x19, State.CSI_INTERMEDIATE, Action.EXECUTE, State.CSI_INTERMEDIATE);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.CSI_INTERMEDIATE, Action.EXECUTE, State.CSI_INTERMEDIATE);
        table.addRange((byte) 0x20, (byte) 0x2F, State.CSI_INTERMEDIATE, Action.COLLECT, State.CSI_INTERMEDIATE);
        table.addOne((byte) 0x7F, State.CSI_INTERMEDIATE, Action.IGNORE, State.CSI_INTERMEDIATE);
        // Csi_intermediate -> Ground
        table.addRange((byte) 0x40, (byte) 0x7E, State.CSI_INTERMEDIATE, Action.DISPATCH, State.GROUND);
        // Csi_intermediate -> Csi_ignore
        table.addRange((byte) 0x30, (byte) 0x3F, State.CSI_INTERMEDIATE, Action.IGNORE, State.GROUND);

        // Csi_entry
        table.addRange((byte) 0x00, (byte) 0x17, State.CSI_ENTRY, Action.EXECUTE, State.CSI_ENTRY);
        table.addOne((byte) 0x19, State.CSI_ENTRY, Action.EXECUTE, State.CSI_ENTRY);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.CSI_ENTRY, Action.EXECUTE, State.CSI_ENTRY);
        table.addOne((byte) 0x7F, State.CSI_ENTRY, Action.IGNORE, State.CSI_ENTRY);
        // Csi_entry -> Ground
        table.addRange((byte) 0x40, (byte) 0x7E, State.CSI_ENTRY, Action.DISPATCH, State.GROUND);
        // Csi_entry -> Csi_intermediate
        table.addRange((byte) 0x20, (byte) 0x2F, State.CSI_ENTRY, Action.COLLECT, State.CSI_INTERMEDIATE);
        // Csi_entry -> Csi_param
        table.addRange((byte) 0x30, (byte) 0x3B, State.CSI_ENTRY, Action.PARAM, State.CSI_PARAM);
        table.addRange((byte) 0x3C, (byte) 0x3F, State.CSI_ENTRY, Action.MARKER, State.CSI_PARAM);

        // Osc_string
        table.addRange((byte) 0x00, (byte) 0x06, State.OSC_STRING, Action.IGNORE, State.OSC_STRING);
        table.addRange((byte) 0x08, (byte) 0x17, State.OSC_STRING, Action.IGNORE, State.OSC_STRING);
        table.addOne((byte) 0x19, State.OSC_STRING, Action.IGNORE, State.OSC_STRING);
        table.addRange((byte) 0x1C, (byte) 0x1F, State.OSC_STRING, Action.IGNORE, State.OSC_STRING);
        table.addRange((byte) 0x20, (byte) 0xFF, State.OSC_STRING, Action.PUT, State.OSC_STRING); // Allow Utf8 characters by extending the printable range from 0x7F to 0xFF

        // ST, CAN, SUB, ESC, and BEL terminate the sequence
        table.addOne((byte) 0x1B, State.OSC_STRING, Action.DISPATCH, State.ESCAPE);
        table.addOne((byte) 0x07, State.OSC_STRING, Action.DISPATCH, State.GROUND);
        table.addOne((byte) 0x9C, State.OSC_STRING, Action.DISPATCH, State.GROUND);
        table.addMany(new byte[]{(byte) 0x18, (byte) 0x1A}, State.OSC_STRING, Action.IGNORE, State.GROUND);


        return table;
    }

}
