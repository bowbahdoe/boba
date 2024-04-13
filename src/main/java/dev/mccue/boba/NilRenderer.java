package dev.mccue.boba;

import java.util.EventObject;

@Charm("https://github.com/charmbracelet/bubbletea/blob/491eda41276c3419d519bc8c622725fa587b7e37/nil_renderer.go")
public final class NilRenderer implements Renderer {
    private static final NilRenderer INSTANCE = new NilRenderer();

    private NilRenderer() {}

    public static NilRenderer instance() {
        return INSTANCE;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
    }

    @Override
    public void kill() {

    }

    @Override
    public void write(String frame) {

    }

    @Override
    public void repaint() {

    }

    @Override
    public void clearScreen() {

    }

    @Override
    public boolean altScreen() {
        return false;
    }

    @Override
    public void enterAltScreen() {

    }

    @Override
    public void exitAltScreen() {

    }

    @Override
    public void showCursor() {

    }

    @Override
    public void hideCursor() {

    }

    @Override
    public void enableMouseCellMotion() {

    }

    @Override
    public void disableMouseCellMotion() {

    }

    @Override
    public void enableMouseAllMotion() {

    }

    @Override
    public void disableMouseAllMotion() {

    }

    @Override
    public void enableMouseSGRMode() {

    }

    @Override
    public void disableMouseSGRMode() {

    }

    @Override
    public void enableBracketedPaste() {

    }

    @Override
    public void disableBracketedPaste() {

    }

    @Override
    public boolean bracketedPasteActive() {
        return false;
    }
}
