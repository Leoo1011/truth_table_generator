package com.leo.main;

/**
 * Abstract class used to represent the tokens returned by the {@link BooleanLogicLexer},
 * inherited by {@link PropositionName}, {@link Connective} and {@link EndOfLine}.
 */
public abstract class Token {
    public final TokenType type;
    Token(TokenType tokenType) {
        type = tokenType;
    }
}
