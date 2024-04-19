package dev.mccue.boba.color;

record ANSIColor(int value) implements Color {
    @Override
    public String toString() {
        return "ANSIColor[value=" + Integer.toUnsignedString(value) + "]";
    }
}
