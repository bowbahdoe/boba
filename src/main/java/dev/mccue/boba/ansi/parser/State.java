package dev.mccue.boba.ansi.parser;

import dev.mccue.boba.Charm;

import java.util.Optional;

@Charm("https://github.com/charmbracelet/x/blob/main/exp/term/ansi/parser/const.go")
public enum State {
    GROUND,
    CSI_ENTRY,
    CSI_INTERMEDIATE,
    CSI_PARAM,
    DCS_ENTRY,
    DCS_INTERMEDIATE,
    DCS_PARAM,
    DCS_STRING,
    ESCAPE,
    ESCAPE_INTERMEDIATE,
    OSC_STRING,
    SOS_STRING,
    PM_STRING,
    APC_STRING,
    // Utf8State is not part of the DEC ANSI standard. It is used to handle
    // UTF-8 sequences.
    UTF8;

    private static final State[] VALUES = State.values();

    final byte value;

    State() {
        this.value = (byte) this.ordinal();
    }

    static Optional<State> fromValue(byte value) {
        if (value >= 0 && value < VALUES.length) {
            return Optional.of(VALUES[value]);
        }
        else {
            return Optional.empty();
        }
    }
}
