package com.leo;

import static com.leo.TokenType.PROP_NAME;

public class Proposition extends Token {
    String name;
    Proposition(String propName) {
        super(PROP_NAME);
        name = propName;
    }

    @Override
    public String toString() {
        return "Proposition{ name = " + name + " }";
    }
}
