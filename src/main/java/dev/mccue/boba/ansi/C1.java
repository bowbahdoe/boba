package dev.mccue.boba.ansi;

import dev.mccue.boba.Charm;

// C1 control characters.
//
// These range from (0x80-0x9F) as defined in ISO 6429 (ECMA-48).
// See: https://en.wikipedia.org/wiki/C0_and_C1_control_codes
@Charm("https://github.com/charmbracelet/x/blob/main/exp/term/ansi/c1.go")
public final class C1 {
    private C1() {}

    // PAD is the padding character.
    public static final int PAD = 0x80;
    // HOP is the high octet preset character.
    public static final int HOP = 0x81;
    // BPH is the break permitted here character.
    public static final int BPH = 0x82;
    // NBH is the no break here character.
    public static final int NBH = 0x83;
    // IND is the index character.
    public static final int IND = 0x84;
    // NEL is the next line character.
    public static final int NEL = 0x85;
    // SSA is the start of selected area character.
    public static final int SSA = 0x86;
    // ESA is the end of selected area character.
    public static final int ESA = 0x87;
    // HTS is the horizontal tab set character.
    public static final int HTS = 0x88;
    // HTJ is the horizontal tab with justification character.
    public static final int HTJ = 0x89;
    // VTS is the vertical tab set character.
    public static final int VTS = 0x8A;
    // PLD is the partial line forward character.
    public static final int PLD = 0x8B;
    // PLU is the partial line backward character.
    public static final int PLU = 0x8C;
    // RI is the reverse index character.
    public static final int RI = 0x8D;
    // SS2 is the single shift 2 character.
    public static final int SS2 = 0x8E;
    // SS3 is the single shift 3 character.
    public static final int SS3 = 0x8F;
    // DCS is the device control string character.
    public static final int DCS = 0x90;
    // PU1 is the private use 1 character.
    public static final int PU1 = 0x91;
    // PU2 is the private use 2 character.
    public static final int PU2 = 0x92;
    // STS is the set transmit state character.
    public static final int STS = 0x93;
    // CCH is the cancel character.
    public static final int CCH = 0x94;
    // MW is the message waiting character.
    public static final int MW = 0x95;
    // SPA is the start of guarded area character.
    public static final int SPA = 0x96;
    // EPA is the end of guarded area character.
    public static final int EPA = 0x97;
    // SOS is the start of string character.
    public static final int SOS = 0x98;
    // SGCI is the single graphic character introducer character.
    public static final int SGCI = 0x99;
    // SCI is the single character introducer character.
    public static final int SCI = 0x9A;
    // CSI is the control sequence introducer character.
    public static final int CSI = 0x9B;
    // ST is the string terminator character.
    public static final int ST = 0x9C;
    // OSC is the operating system command character.
    public static final int OSC = 0x9D;
    // PM is the privacy message character.
    public static final int PM = 0x9E;
    // APC is the application program command character.
    public static final int APC = 0x9F;
}
