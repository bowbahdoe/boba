// Generated by jextract

package dev.mccue.boba.c.linux;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct termios {
 *     tcflag_t c_iflag;
 *     tcflag_t c_oflag;
 *     tcflag_t c_cflag;
 *     tcflag_t c_lflag;
 *     cc_t c_line;
 *     cc_t c_cc[32];
 *     speed_t c_ispeed;
 *     speed_t c_ospeed;
 * }
 * }
 */
public class termios {

    termios() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        ioctl_h.C_INT.withName("c_iflag"),
        ioctl_h.C_INT.withName("c_oflag"),
        ioctl_h.C_INT.withName("c_cflag"),
        ioctl_h.C_INT.withName("c_lflag"),
        ioctl_h.C_CHAR.withName("c_line"),
        MemoryLayout.sequenceLayout(32, ioctl_h.C_CHAR).withName("c_cc"),
        MemoryLayout.paddingLayout(3),
        ioctl_h.C_INT.withName("c_ispeed"),
        ioctl_h.C_INT.withName("c_ospeed")
    ).withName("termios");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt c_iflag$LAYOUT = (OfInt)$LAYOUT.select(groupElement("c_iflag"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * tcflag_t c_iflag
     * }
     */
    public static final OfInt c_iflag$layout() {
        return c_iflag$LAYOUT;
    }

    private static final long c_iflag$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * tcflag_t c_iflag
     * }
     */
    public static final long c_iflag$offset() {
        return c_iflag$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * tcflag_t c_iflag
     * }
     */
    public static int c_iflag(MemorySegment struct) {
        return struct.get(c_iflag$LAYOUT, c_iflag$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * tcflag_t c_iflag
     * }
     */
    public static void c_iflag(MemorySegment struct, int fieldValue) {
        struct.set(c_iflag$LAYOUT, c_iflag$OFFSET, fieldValue);
    }

    private static final OfInt c_oflag$LAYOUT = (OfInt)$LAYOUT.select(groupElement("c_oflag"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * tcflag_t c_oflag
     * }
     */
    public static final OfInt c_oflag$layout() {
        return c_oflag$LAYOUT;
    }

    private static final long c_oflag$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * tcflag_t c_oflag
     * }
     */
    public static final long c_oflag$offset() {
        return c_oflag$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * tcflag_t c_oflag
     * }
     */
    public static int c_oflag(MemorySegment struct) {
        return struct.get(c_oflag$LAYOUT, c_oflag$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * tcflag_t c_oflag
     * }
     */
    public static void c_oflag(MemorySegment struct, int fieldValue) {
        struct.set(c_oflag$LAYOUT, c_oflag$OFFSET, fieldValue);
    }

    private static final OfInt c_cflag$LAYOUT = (OfInt)$LAYOUT.select(groupElement("c_cflag"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * tcflag_t c_cflag
     * }
     */
    public static final OfInt c_cflag$layout() {
        return c_cflag$LAYOUT;
    }

    private static final long c_cflag$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * tcflag_t c_cflag
     * }
     */
    public static final long c_cflag$offset() {
        return c_cflag$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * tcflag_t c_cflag
     * }
     */
    public static int c_cflag(MemorySegment struct) {
        return struct.get(c_cflag$LAYOUT, c_cflag$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * tcflag_t c_cflag
     * }
     */
    public static void c_cflag(MemorySegment struct, int fieldValue) {
        struct.set(c_cflag$LAYOUT, c_cflag$OFFSET, fieldValue);
    }

    private static final OfInt c_lflag$LAYOUT = (OfInt)$LAYOUT.select(groupElement("c_lflag"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * tcflag_t c_lflag
     * }
     */
    public static final OfInt c_lflag$layout() {
        return c_lflag$LAYOUT;
    }

    private static final long c_lflag$OFFSET = 12;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * tcflag_t c_lflag
     * }
     */
    public static final long c_lflag$offset() {
        return c_lflag$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * tcflag_t c_lflag
     * }
     */
    public static int c_lflag(MemorySegment struct) {
        return struct.get(c_lflag$LAYOUT, c_lflag$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * tcflag_t c_lflag
     * }
     */
    public static void c_lflag(MemorySegment struct, int fieldValue) {
        struct.set(c_lflag$LAYOUT, c_lflag$OFFSET, fieldValue);
    }

    private static final OfByte c_line$LAYOUT = (OfByte)$LAYOUT.select(groupElement("c_line"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * cc_t c_line
     * }
     */
    public static final OfByte c_line$layout() {
        return c_line$LAYOUT;
    }

    private static final long c_line$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * cc_t c_line
     * }
     */
    public static final long c_line$offset() {
        return c_line$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * cc_t c_line
     * }
     */
    public static byte c_line(MemorySegment struct) {
        return struct.get(c_line$LAYOUT, c_line$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * cc_t c_line
     * }
     */
    public static void c_line(MemorySegment struct, byte fieldValue) {
        struct.set(c_line$LAYOUT, c_line$OFFSET, fieldValue);
    }

    private static final SequenceLayout c_cc$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("c_cc"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * cc_t c_cc[32]
     * }
     */
    public static final SequenceLayout c_cc$layout() {
        return c_cc$LAYOUT;
    }

    private static final long c_cc$OFFSET = 17;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * cc_t c_cc[32]
     * }
     */
    public static final long c_cc$offset() {
        return c_cc$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * cc_t c_cc[32]
     * }
     */
    public static MemorySegment c_cc(MemorySegment struct) {
        return struct.asSlice(c_cc$OFFSET, c_cc$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * cc_t c_cc[32]
     * }
     */
    public static void c_cc(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, c_cc$OFFSET, c_cc$LAYOUT.byteSize());
    }

    private static long[] c_cc$DIMS = { 32 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * cc_t c_cc[32]
     * }
     */
    public static long[] c_cc$dimensions() {
        return c_cc$DIMS;
    }
    private static final VarHandle c_cc$ELEM_HANDLE = c_cc$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * cc_t c_cc[32]
     * }
     */
    public static byte c_cc(MemorySegment struct, long index0) {
        return (byte)c_cc$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * cc_t c_cc[32]
     * }
     */
    public static void c_cc(MemorySegment struct, long index0, byte fieldValue) {
        c_cc$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
    }

    private static final OfInt c_ispeed$LAYOUT = (OfInt)$LAYOUT.select(groupElement("c_ispeed"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * speed_t c_ispeed
     * }
     */
    public static final OfInt c_ispeed$layout() {
        return c_ispeed$LAYOUT;
    }

    private static final long c_ispeed$OFFSET = 52;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * speed_t c_ispeed
     * }
     */
    public static final long c_ispeed$offset() {
        return c_ispeed$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * speed_t c_ispeed
     * }
     */
    public static int c_ispeed(MemorySegment struct) {
        return struct.get(c_ispeed$LAYOUT, c_ispeed$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * speed_t c_ispeed
     * }
     */
    public static void c_ispeed(MemorySegment struct, int fieldValue) {
        struct.set(c_ispeed$LAYOUT, c_ispeed$OFFSET, fieldValue);
    }

    private static final OfInt c_ospeed$LAYOUT = (OfInt)$LAYOUT.select(groupElement("c_ospeed"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * speed_t c_ospeed
     * }
     */
    public static final OfInt c_ospeed$layout() {
        return c_ospeed$LAYOUT;
    }

    private static final long c_ospeed$OFFSET = 56;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * speed_t c_ospeed
     * }
     */
    public static final long c_ospeed$offset() {
        return c_ospeed$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * speed_t c_ospeed
     * }
     */
    public static int c_ospeed(MemorySegment struct) {
        return struct.get(c_ospeed$LAYOUT, c_ospeed$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * speed_t c_ospeed
     * }
     */
    public static void c_ospeed(MemorySegment struct, int fieldValue) {
        struct.set(c_ospeed$LAYOUT, c_ospeed$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() { return layout().byteSize(); }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}

