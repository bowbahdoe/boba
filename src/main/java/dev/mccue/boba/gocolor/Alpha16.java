package dev.mccue.boba.gocolor;

public record Alpha16(short a) implements Color {
    @Override
    public AlphaPreMultipliedRGBA rgba() {
        var a = (int) this.a;
        return new AlphaPreMultipliedRGBA(a, a, a, a);
    }
}
