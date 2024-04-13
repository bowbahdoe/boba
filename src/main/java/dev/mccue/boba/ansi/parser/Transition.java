package dev.mccue.boba.ansi.parser;

import java.util.Objects;

public record Transition(State state, Action action) {
    public Transition {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(action, "action");
    }
}
