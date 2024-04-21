package com.leo;

/**
 * Enum to represent token types, used by the {@link BooleanLogicLexer}.
 */
public enum TokenType {
    PROP_NAME,
    OR("|"),
    AND("&"),
    XOR("^"),
    THEN("->"), // "implies" == ->
    IFF("<->"),  // <->
    NOT("!"),
    LEFT_PAREN,
    RIGHT_PAREN,
    EOL;   // "end of line"

    TokenType() {
        this.prettyName = null;
    }

    public final String prettyName;
    TokenType(String prettyName) {
        this.prettyName = prettyName;
    }

    public String toString() {
        return this.name().toLowerCase();
    }
}
