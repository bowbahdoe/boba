package dev.mccue.boba.ansi.parser;

// Parser represents a DEC ANSI compatible sequence parser.
//
// It uses a state machine to parse ANSI escape sequences and control
// characters. The parser is designed to be used with a terminal emulator or
// similar application that needs to parse ANSI escape sequences and control
// characters.
// See package [parser] for more information.
public final class Parser {
    // Params contains the raw parameters of the sequence.
    // These parameters used when constructing CSI and DCS sequences.
    int[] params;

    // Data contains the raw data of the sequence.
    // These data used when constructing OSC, DCS, SOS, PM, and APC sequences.
    byte[] data;

    // DataLen keeps track of the length of the data buffer.
    // If DataLen is -1, the data buffer is unlimited and will grow as needed.
    // Otherwise, DataLen is limited by the size of the Data buffer.
    int dataLen;

    // ParamsLen keeps track of the number of parameters.
    // This is limited by the size of the Params buffer.
    int paramsLen;

    // Cmd contains the raw command along with the private marker and
    // intermediate bytes of the sequence.
    // The first lower byte contains the command byte, the next byte contains
    // the private marker, and the next byte contains the intermediate byte.
    int cmd;

    // RuneLen keeps track of the number of bytes collected for a UTF-8 rune.
    int runeLen;

    // RuneBuf contains the bytes collected for a UTF-8 rune.
    // RuneBuf [utf8.MaxRune]byte
    byte[] runeBuf;

    // State is the current state of the parser.
    State state;

    // Reset resets the parser to its initial state.
    public void reset() {
        clear();
        state = State.GROUND;
    }

    // clear clears the parser parameters and command.
    public void clear() {
        if (params.length > 0) {
            params[0] = Seq.MISSING_PARAM;
        }
        paramsLen = 0;
        cmd = 0;
        runeLen = 0;
    }

    public static int utf8ByteLen(byte b) {
        if (Byte.compareUnsigned(b, (byte) 0b0111_1111) <= 0) { // 0x00-0x7F
            return 1;
        } else if (
                Byte.compareUnsigned(b, (byte) 0b1100_0000) >= 0
                && Byte.compareUnsigned(b, (byte) 0b1101_1111) <=0
        ) { // 0xC0-0xDF
            return 2;
        } else if (
                Byte.compareUnsigned(b, (byte) 0b1110_0000) >= 0
                && Byte.compareUnsigned(b, (byte) 0b1110_1111) <=0
        ) { // 0xE0-0xEF
            return 3;
        } else if (
                Byte.compareUnsigned(b, (byte) 0b1111_0000) >= 0
                && Byte.compareUnsigned(b, (byte) 0b1111_0111) <=0
        ) { // 0xF0-0xF7
            return 4;
        }
        return -1;
    }
}
