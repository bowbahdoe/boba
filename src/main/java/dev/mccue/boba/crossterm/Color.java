package dev.mccue.boba.crossterm;

public sealed interface Color {
    /// Resets the terminal color.
    record Reset() implements Color {}

    /// Black color.
    record Black() implements Color {}

    /// Dark grey color.
    record DarkGrey() implements Color {}

    /// Light red color.
    record Red() implements Color {}

    /// Dark red color.
    record DarkRed() implements Color {}

    /// Light green color.
    record Green() implements Color {}

    /// Dark green color.
    record DarkGreen() implements Color {}

    /// Light yellow color.
    record Yellow() implements Color {}

    /// Dark yellow color.
    record DarkYellow() implements Color {}

    /// Light blue color.
    record Blue() implements Color {}

    /// Dark blue color.
    record DarkBlue() implements Color {}

    /// Light magenta color.
    record Magenta() implements Color {}

    /// Dark magenta color.
    record DarkMagenta() implements Color {}

    /// Light cyan color.
    record Cyan() implements Color {}

    /// Dark cyan color.
    record DarkCyan() implements Color {}

    /// White color.
    record White() implements Color {}

    /// Grey color.
    record Grey() implements Color {}

    /// An RGB color. See [RGB color model](https://en.wikipedia.org/wiki/RGB_color_model) for more info.
    ///
    /// Most UNIX terminals and Windows 10 supported only.
    /// See [Platform-specific notes](enum.Color.html#platform-specific-notes) for more info.
    record Rgb(byte r, byte g, byte b) implements Color {}

    /// An ANSI color. See [256 colors - cheat sheet](https://jonasjacek.github.io/colors/) for more info.
    ///
    /// Most UNIX terminals and Windows 10 supported only.
    /// See [Platform-specific notes](enum.Color.html#platform-specific-notes) for more info.
    record AnsiValue(byte value) implements Color {}

    record TrueColor(int value) implements Color {}
}
