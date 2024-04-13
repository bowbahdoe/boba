package dev.mccue.boba.ansi;

import dev.mccue.boba.Charm;
import dev.mccue.boba.ansi.parser.Action;
import dev.mccue.boba.ansi.parser.Parser;
import dev.mccue.boba.ansi.parser.State;
import dev.mccue.boba.ansi.parser.TransitionTable;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Charm("https://github.com/charmbracelet/x/blob/main/exp/term/ansi/width.go")
public final class Width {
    /*
// Strip removes ANSI escape codes from a string.
func Strip(s string) string {
var (
    buf    bytes.Buffer         // buffer for collecting printable characters
    ri     int                  // rune index
    rw     int                  // rune width
    pstate = parser.GroundState // initial state
)

// This implements a subset of the Parser to only collect runes and
// printable characters.
for i := 0; i < len(s); i++ {
    var state, action byte
    if pstate != parser.Utf8State {
        state, action = parser.Table.Transition(pstate, s[i])
    }

    switch {
    case pstate == parser.Utf8State:
        // During this state, collect rw bytes to form a valid rune in the
        // buffer. After getting all the rune bytes into the buffer,
        // transition to GroundState and reset the counters.
        buf.WriteByte(s[i])
        ri++
        if ri < rw {
            continue
        }
        pstate = parser.GroundState
        ri = 0
        rw = 0
    case action == parser.PrintAction:
        // This action happens when we transition to the Utf8State.
        if w := utf8ByteLen(s[i]); w > 1 {
            rw = w
            buf.WriteByte(s[i])
            ri++
            break
        }
        fallthrough
    case action == parser.ExecuteAction:
        // collects printable ASCII and non-printable characters
        buf.WriteByte(s[i])
    }

    // Transition to the next state.
    // The Utf8State is managed separately above.
    if pstate != parser.Utf8State {
        pstate = state
    }
}

return buf.String()
}
 */
    public static String strip(String s) {
        var buf = new ByteArrayOutputStream();
        int ri = 0;
        int rw = 0;
        var pstate = State.GROUND;

        var bytes = s.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bytes.length; i++) {
            var state = State.GROUND;
            var action = Action.NONE;

            if (pstate != State.UTF8) {
                var transition = TransitionTable.DEFAULT_TABLE.transition(pstate, bytes[i]);
                state = transition.state();
                action = transition.action();
            }

            if (pstate == State.UTF8) {
                // During this state, collect rw bytes to form a valid rune in the
                // buffer. After getting all the rune bytes into the buffer,
                // transition to GroundState and reset the counters.
                buf.write(bytes[i]);
                ri++;
                if (ri < rw) {
                    continue;
                }
                pstate = State.GROUND;
                ri = 0;
                rw = 0;
            }
            else {
                switch (action) {
                    case Action.PRINT:
                        int w = Parser.utf8ByteLen(bytes[i]);
                        if (w > 1) {
                            rw = w;
                            buf.write(bytes[i]);
                            ri++;
                            break;
                        }
                    case Action.EXECUTE:
                        buf.write(bytes[i]);
                }
            }

            if (pstate != State.UTF8) {
                pstate = state;
            }
        }

        return buf.toString(StandardCharsets.UTF_8);
    }

    public static int width(String s) {
        return width(s, Locale.getDefault());
    }

    public static int width(String s, Locale locale) {
        var stripped = strip(s);
        return stripped.codePoints()
                .reduce(0, (w, codePoint) -> w + WCWidth.wcwidth(codePoint));
    }


}
