package dev.mccue.boba.crossterm;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public final class Unix {
    private static final MethodHandle isatty_MH;

    static {
        var descriptor = FunctionDescriptor.of(
                ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT
        );

        var linker = Linker.nativeLinker();
        var lookup = linker.defaultLookup();
        var isatty = lookup
                .find("isatty")
                .orElseThrow();
        isatty_MH = linker.downcallHandle(isatty, descriptor);
    }

    public static int isatty(int fd) {
        try {
            return (int) isatty_MH.invokeExact(fd);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
