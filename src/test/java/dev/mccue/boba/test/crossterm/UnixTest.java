package dev.mccue.boba.test.crossterm;

import dev.mccue.boba.crossterm.Unix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledOnOs({ OS.LINUX, OS.MAC })
public class UnixTest {
    @Test
    public void isatty() {
        assertTrue(Unix.isatty(0) == 0 || Unix.isatty(0) == 1);
    }
}
