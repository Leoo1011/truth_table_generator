package com.leo.main;

import static com.leo.main.TokenType.PROP_NAME;

public class PropositionName extends Token {
    String name;
    public PropositionName(String propName) {
        super(PROP_NAME);
        name = propName;
    }

    @Override
    public String toString() {
        return "Proposition{ name = " + name + " }";
    }
}
