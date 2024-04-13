package dev.mccue.boba.crossterm;

import org.fusesource.jansi.Ansi;

import java.io.Writer;

final class MoveToNextLine extends Command {
    private final int lines;

    MoveToNextLine(int lines) {
        this.lines = lines;
    }

    @Override
    void writeAnsi(Writer writer) {
        Ansi.ansi()
                .cursorDownLine(lines)
                .toString();
    }

    @Override
    void executeWinApi() {

    }

}
