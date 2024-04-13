package dev.mccue.boba.ansi;

import java.nio.charset.StandardCharsets;
import java.util.List;

public record Rune(int codePoint) implements Sequence {
    @Override
    public String toString() {
        return new String(Character.toChars(codePoint));
    }

    static List<Rune> split(String s) {
        return s.codePoints()
                .mapToObj(Rune::new)
                .toList();
    }

    @Override
    public String string() {
        return new String(Character.toChars(codePoint));
    }

    @Override
    public byte[] bytes() {
        return string().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Sequence copy() {
        return this;
    }
}
