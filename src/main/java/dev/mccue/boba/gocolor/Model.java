package dev.mccue.boba.gocolor;

public interface Model {
    Color convert(Color c);

    Model RGBA = new ModelFunc("RGBA", c -> {
        return c;
    });

    Model RGBA64 = new ModelFunc("RGBA64", c -> {
        return c;
    });

    Model NRGBA = new ModelFunc("NRGBA", c -> {
        return c;
    });

    Model NRGBA64 = new ModelFunc("NRGBA64", c -> {
        return c;
    });

    Model Alpha = new ModelFunc("Alpha", c -> {
        return c;
    });

    Model Alpha16 = new ModelFunc("Alpha16", c -> {
        return c;
    });

    Model Gray = new ModelFunc("Gray", c -> {
        return c;
    });

    Model Gray16 = new ModelFunc("Gray16", c -> {
        return c;
    });
}
