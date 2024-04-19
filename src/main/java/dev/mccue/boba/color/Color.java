package dev.mccue.boba.color;

import java.util.Optional;

public sealed interface Color permits ANSIColor {
    static Color ansi(int value) {
        return new ANSIColor(value);
    }
}
