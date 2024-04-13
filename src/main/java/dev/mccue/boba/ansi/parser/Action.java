package dev.mccue.boba.ansi.parser;

import dev.mccue.boba.Charm;

import java.util.Optional;

@Charm("https://github.com/charmbracelet/x/blob/main/exp/term/ansi/parser/const.go")
public enum Action {
    // These are the actions that the parser can take.
    NONE,
    CLEAR,
    COLLECT,
    MARKER,
    DISPATCH,
    EXECUTE,
    START, // Start of a data string
    PUT,   // Put into the data string
    PARAM,
    PRINT;

    public static final Action IGNORE = NONE;

    final byte value;

    Action() {
        this.value = (byte) this.ordinal();
    }
}
