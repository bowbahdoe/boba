package dev.mccue.boba.ansi.parser;

import dev.mccue.boba.ansi.Sequence;

public interface ParserDispatcher {
    void dispatch(Sequence sequence);
}
