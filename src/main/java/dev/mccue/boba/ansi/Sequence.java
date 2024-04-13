package dev.mccue.boba.ansi;

import dev.mccue.boba.Charm;

@Charm("https://github.com/charmbracelet/x/blob/main/exp/term/ansi/sequence.go")
public interface Sequence {
    String string();

    byte[] bytes();

    Sequence copy();
}
