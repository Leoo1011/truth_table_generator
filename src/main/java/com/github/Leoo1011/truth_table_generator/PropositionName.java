package com.github.Leoo1011.truth_table_generator;

import static com.github.Leoo1011.truth_table_generator.TokenType.PROP_NAME;

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
