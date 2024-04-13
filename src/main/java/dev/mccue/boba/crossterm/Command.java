package dev.mccue.boba.crossterm;

import java.io.Writer;

public sealed abstract class Command
        permits MoveToNextLine {

    abstract void writeAnsi(Writer writer);

    abstract void executeWinApi();

    boolean isAnsiCodeSupported() {
        return AnsiSupport.supportsAnsi();
    }
}
