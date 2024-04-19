package dev.mccue.boba;

final class WindowsTerminal extends Terminal {
    @Override
    boolean isTerminal(int fd) {
        throw new UnsupportedOperationException();
    }

    @Override
    void makeRaw(int fd) {
        throw new UnsupportedOperationException();
    }
}
