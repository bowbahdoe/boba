package dev.mccue.boba.gocolor;

// the alpha-premultiplied red, green, blue and alpha values
// for the color. Each value ranges within [0, 0xffff], but is represented
// by a uint32 so that multiplying by a blend factor up to 0xffff will not
// overflow.
//
// An alpha-premultiplied color component c has been scaled by alpha (a),
// so has valid values 0 <= c <= a.
public record AlphaPreMultipliedRGBA(int r, int g, int b, int a) {
    @Override
    public String toString() {
        return "AlphaPreMultipliedRGBA[r="
               + Integer.toUnsignedString(r)
               + ", g="
               + Integer.toUnsignedString(g)
               + ", b="
               + Integer.toUnsignedString(b)
               + ", a="
               + Integer.toUnsignedString(a)
               + "]";
    }
}
