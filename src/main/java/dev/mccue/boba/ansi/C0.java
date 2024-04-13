package dev.mccue.boba.ansi;


import dev.mccue.boba.Charm;

// C0 control characters.
//
// These range from (0x00-0x1F) as defined in ISO 646 (ASCII).
// See: https://en.wikipedia.org/wiki/C0_and_C1_control_codes
@Charm("https://github.com/charmbracelet/x/blob/main/exp/term/ansi/c0.go")
public final class C0 {
    private C0() {}

    // NUL is the null character (Caret: ^@, Char: \0).
    public static final int NUL = 0x00;
    // SOH is the start of heading character (Caret: ^A).
    public static final int SOH = 0x01;
    // STX is the start of text character (Caret: ^B).
    public static final int STX = 0x02;
    // ETX is the end of text character (Caret: ^C).
    public static final int ETX = 0x03;
    // EOT is the end of transmission character (Caret: ^D).
    public static final int EOT = 0x04;
    // ENQ is the enquiry character (Caret: ^E).
    public static final int ENQ = 0x05;
    // ACK is the acknowledge character (Caret: ^F).
    public static final int ACK = 0x06;
    // BEL is the bell character (Caret: ^G, Char: \a).
    public static final int BEL = 0x07;
    // BS is the backspace character (Caret: ^H, Char: \b).
    public static final int BS = 0x08;
    // HT is the horizontal tab character (Caret: ^I, Char: \t).
    public static final int HT = 0x09;
    // LF is the line feed character (Caret: ^J, Char: \n).
    public static final int LF = 0x0A;
    // VT is the vertical tab character (Caret: ^K, Char: \v).
    public static final int VT = 0x0B;
    // FF is the form feed character (Caret: ^L, Char: \f).
    public static final int FF = 0x0C;
    // CR is the carriage return character (Caret: ^M, Char: \r).
    public static final int CR = 0x0D;
    // SO is the shift out character (Caret: ^N).
    public static final int SO = 0x0E;
    // SI is the shift in character (Caret: ^O).
    public static final int SI = 0x0F;
    // DLE is the data link escape character (Caret: ^P).
    public static final int DLE = 0x10;
    // DC1 is the device control 1 character (Caret: ^Q).
    public static final int DC1 = 0x11;
    // DC2 is the device control 2 character (Caret: ^R).
    public static final int DC2 = 0x12;
    // DC3 is the device control 3 character (Caret: ^S).
    public static final int DC3 = 0x13;
    // DC4 is the device control 4 character (Caret: ^T).
    public static final int DC4 = 0x14;
    // NAK is the negative acknowledge character (Caret: ^U).
    public static final int NAK = 0x15;
    // SYN is the synchronous idle character (Caret: ^V).
    public static final int SYN = 0x16;
    // ETB is the end of transmission block character (Caret: ^W).
    public static final int ETB = 0x17;
    // CAN is the cancel character (Caret: ^X).
    public static final int CAN = 0x18;
    // EM is the end of medium character (Caret: ^Y).
    public static final int EM = 0x19;
    // SUB is the substitute character (Caret: ^Z).
    public static final int SUB = 0x1A;
    // ESC is the escape character (Caret: ^[, Char: \e).
    public static final int ESC = 0x1B;
    // FS is the file separator character (Caret: ^\).
    public static final int FS = 0x1C;
    // GS is the group separator character (Caret: ^]).
    public static final int GS = 0x1D;
    // RS is the record separator character (Caret: ^^).
    public static final int RS = 0x1E;
    // US is the unit separator character (Caret: ^_).
    public static final int US = 0x1F;
}
