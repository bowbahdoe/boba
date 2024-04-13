package dev.mccue.boba.gocolor;

public record Alpha(byte a) implements Color {
    @Override
    public AlphaPreMultipliedRGBA rgba() {
        var a = (int) this.a;
        a |= a << 8;
        return new AlphaPreMultipliedRGBA(a, a, a, a);
    }
}
