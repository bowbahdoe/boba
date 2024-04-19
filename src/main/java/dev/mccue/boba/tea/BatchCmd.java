package dev.mccue.boba.tea;

import java.util.List;
import java.util.function.Consumer;

record BatchCmd(List<Cmd> cmdList) implements Cmd {
    BatchCmd(List<Cmd> cmdList) {
        this.cmdList = List.copyOf(cmdList);
    }

    @Override
    public void execute(Consumer<? super Msg> onMsg) throws Exception {
        for (var cmd : cmdList) {
            cmd.execute(onMsg);
        }
    }
}
