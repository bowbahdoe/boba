package dev.mccue.boba.gocolor;

record ModelFunc(String name, Model model) implements Model {
    @Override
    public Color convert(Color c) {
        return model.convert(c);
    }

    @Override
    public String toString() {
        return name;
    }
}
