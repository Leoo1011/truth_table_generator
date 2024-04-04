package com.leo;

public enum TokenType {
    PROP_NAME,
    OR,
    AND,
    XOR,
    THEN, // "implies" == ->
    IFF,  // <->
    NOT,
    LEFT_PAREN,
    RIGHT_PAREN,
    EOL   // "end of line"
}
