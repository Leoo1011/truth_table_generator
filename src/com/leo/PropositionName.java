package com.leo;

import static com.leo.TokenType.PROP_NAME;

public class PropositionName extends Token {
    String name;
    PropositionName(String propName) {
        super(PROP_NAME);
        name = propName;
    }

    @Override
    public String toString() {
        return "Proposition{ name = " + name + " }";
    }
}
