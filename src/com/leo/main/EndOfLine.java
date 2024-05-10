package com.leo.main;

import static com.leo.main.TokenType.*;

public class EndOfLine extends Token {
    EndOfLine() {
        super(EOL);
    }

    @Override
    public String toString() {
        return "<EOL>";
    }
}
