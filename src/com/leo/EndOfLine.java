package com.leo;

import static com.leo.TokenType.*;

public class EndOfLine extends Token {
    EndOfLine() {
        super(EOL);
    }

    @Override
    public String toString() {
        return "<EOL>";
    }
}
