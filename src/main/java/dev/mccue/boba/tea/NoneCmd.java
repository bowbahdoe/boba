package dev.mccue.boba.tea;

import java.util.function.Consumer;

record NoneCmd() implements Cmd {
    @Override
    public void execute(Consumer<? super Msg> onMsg) {

    }
}
