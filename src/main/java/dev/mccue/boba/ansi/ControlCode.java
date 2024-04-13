package dev.mccue.boba.ansi;

import dev.mccue.boba.Charm;

import java.nio.charset.StandardCharsets;

// ControlCode represents a control code character. This is a character that
// is not printable and is used to control the terminal. This would be a
// character in the C0 or C1 set in the range of 0x00-0x1F and 0x80-0x9F.
@Charm("https://github.com/charmbracelet/x/blob/main/exp/term/ansi/sequence.go#L40")
public record ControlCode(byte value) implements Sequence {

    @Override
    public String string() {
        return new String(bytes(), StandardCharsets.UTF_8);
    }

    @Override
    public byte[] bytes() {
        return new byte[] { value };
    }

    @Override
    public Sequence copy() {
        return this;
    }
}
