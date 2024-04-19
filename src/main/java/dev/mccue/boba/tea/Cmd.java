package dev.mccue.boba.tea;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface Cmd {
    static Cmd none() {
        return new NoneCmd();
    }

    static Cmd batch(Cmd... cmds) {
        var cmdsArrayList = new ArrayList<Cmd>();
        for (var cmd : cmds) {
            if (cmd instanceof BatchCmd batchCmd) {
                cmdsArrayList.addAll(batchCmd.cmdList());
            }
            else {
                cmdsArrayList.add(cmd);
            }
        }
        return new BatchCmd(cmdsArrayList);
    }

    default Cmd map(Function<? super Msg, ? extends Msg> f) {
        return onMsg -> this.execute(msg -> onMsg.accept(f.apply(msg)));
    }

    /**
     * Executes the command.
     */
    void execute(Consumer<? super Msg> onMsg) throws Exception;
}
