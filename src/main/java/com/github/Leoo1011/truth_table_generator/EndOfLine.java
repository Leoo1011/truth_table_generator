package com.github.Leoo1011.truth_table_generator;

import static com.github.Leoo1011.truth_table_generator.TokenType.*;

public class EndOfLine extends Token {
    public EndOfLine() {
        super(EOL);
    }

    @Override
    public String toString() {
        return "<EOL>";
    }
}
