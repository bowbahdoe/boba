package dev.mccue.boba.gocolor;

public record Gray16(short y) implements Color {
    @Override
    public AlphaPreMultipliedRGBA rgba() {
        var y = (int) this.y;
        return new AlphaPreMultipliedRGBA(y, y, y, 0xFFFF);
    }
}
