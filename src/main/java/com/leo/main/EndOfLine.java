package com.leo.main;

import static com.leo.main.TokenType.*;

public class EndOfLine extends Token {
    public EndOfLine() {
        super(EOL);
    }

    @Override
    public String toString() {
        return "<EOL>";
    }
}
