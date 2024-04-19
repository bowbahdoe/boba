package dev.mccue.boba.simpletea;

import dev.mccue.boba.tea.Cmd;
import dev.mccue.boba.tea.Msg;
import dev.mccue.boba.tea.Program;

public class Counter implements Program<Model, String> {
    @Override
    public Cmd update(Model model, Msg msg) {
        switch (msg) {
            case Increment _ -> {
                model.count++;
            }
            case Decrement _ -> {
                model.count--;
            }
            default -> {}
        }

        return Cmd.none();
    }

    @Override
    public String view(Model model) {
        return Integer.toString(model.count);
    }
}
