package dev.mccue.boba.ansi.parser;

final class Seq {
    private Seq() {}
    /*
    // Shift and masks for sequence parameters and intermediates.
const (
	MarkerShift    = 8
	IntermedShift  = 16
	CommandMask    = 0xff
	HasMoreFlag    = math.MinInt32
	ParamMask      = ^HasMoreFlag
	MissingParam   = ParamMask
	MissingCommand = MissingParam
	MaxParam       = math.MaxUint16 // the maximum value a parameter can have
)
     */

    static final int MARKER_SHIFT = 8;
    static final int INTERMED_SHIFT = 16;
    static final int COMMAND_MASK = 0xff;
    static final int HAS_MORE_FLAG = Integer.MIN_VALUE;
    static final int PARAM_MASK = ~HAS_MORE_FLAG;
    static final int MISSING_PARAM = PARAM_MASK;
    static final int MISSING_COMMAND = MISSING_PARAM;
    static final int MAX_PARAM = Character.MAX_VALUE;


}
