package dev.mccue.boba;

import dev.mccue.boba.c.ioctl_h;
import dev.mccue.boba.c.termios;
import dev.mccue.boba.org.apache.commons.lang3.SystemUtils;

import java.lang.foreign.Arena;

public final class Terminal {
    private Terminal() {}

    public static boolean isTerminal(int fd) {
        if (SystemUtils.IS_OS_UNIX) {
            var invoker = ioctl_h.ioctl.makeInvoker(termios.layout());
            if (SystemUtils.IS_OS_MAC
                || SystemUtils.IS_OS_FREE_BSD
                || SystemUtils.IS_OS_NET_BSD
                || SystemUtils.IS_OS_OPEN_BSD
            ) {
                try (var arena = Arena.ofConfined()) {
                    var t = termios.allocate(arena);
                    var result = invoker.apply(fd, ioctl_h.TIOCGETA(), t);
                    return result == 0;
                }
            }
            else {
                throw new IllegalStateException(SystemUtils.OS_NAME);
            }
        }

        return false;
    }

    public static void makeRaw(int fd) {
        try (var arena = Arena.ofConfined()) {
            var t = termios.allocate(arena);
            var invoker = ioctl_h.ioctl.makeInvoker(termios.layout());
            invoker.apply(fd, ioctl_h.TIOCGETA(), t);

            var iflag = termios.c_iflag(t);
            iflag &= ~(ioctl_h.IGNBRK()
                     | ioctl_h.BRKINT()
                     | ioctl_h.PARMRK()
                     | ioctl_h.ISTRIP()
                     | ioctl_h.INLCR()
                     | ioctl_h.IGNCR()
                     | ioctl_h.ICRNL()
                     | ioctl_h.IXON());
            termios.c_iflag(t, iflag);

            var oflag = termios.c_oflag(t);
            oflag &= ~ioctl_h.OPOST();
            termios.c_oflag(t, oflag);

            var lflag = termios.c_lflag(t);
            lflag &= ~(ioctl_h.ECHO() | ioctl_h.ECHONL() | ioctl_h.ICANON() | ioctl_h.ISIG() | ioctl_h.IEXTEN());
            termios.c_lflag(t, lflag);

            var cflag = termios.c_cflag(t);
            cflag &= ~(ioctl_h.CSIZE() | ioctl_h.PARENB());
            cflag |= ioctl_h.CS8();
            termios.c_cflag(t, cflag);

            termios.c_cc(t, ioctl_h.VMIN(), (byte) 1);
            termios.c_cc(t, ioctl_h.VTIME(), (byte) 0);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Terminal.isTerminal(4));
        Terminal.makeRaw(0);

        Thread.sleep(44000);
    }
}