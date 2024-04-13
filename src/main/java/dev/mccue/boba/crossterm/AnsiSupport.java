package dev.mccue.boba.crossterm;

import java.util.concurrent.atomic.AtomicBoolean;

final class AnsiSupport {
    private AnsiSupport() {}

    static {
        if (!System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            throw new ExceptionInInitializerError("AnsiSupport only works on Windows");
        }
    }

    static final AtomicBoolean SUPPORTS_ANSI_ESCAPE_CODES = new AtomicBoolean(false);


    static boolean supportsAnsi() {
        return false;
    }
}
