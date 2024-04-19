package dev.mccue.boba.tea;

public interface Program<Model, View> {
    Cmd update(Model model, Msg msg);

    View view(Model model);
}
