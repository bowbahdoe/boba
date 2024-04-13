package dev.mccue.boba.gocolor;

public record Gray(byte y) implements Color {
    @Override
    public AlphaPreMultipliedRGBA rgba() {
        var y = (int) this.y;
        y |= y << 8;
        return new AlphaPreMultipliedRGBA(y, y, y, 0xFFFF);
    }
}
