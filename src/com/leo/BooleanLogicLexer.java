package com.leo;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BooleanLogicLexer {
    private BooleanLogicLexer() {
    }

    /**
     * Performs lexical analysis ("tokenization") on a boolean logic expression
     * (not assumed to be well-formed). Recognized symbols are & (and), | (or), ^ (xor),
     * ! (not), -> (implication), <-> (double implication),and parentheses. Spaces are ignored.
     *
     * @param formula the formula to analyze
     * @return array of arrays of length 2, each sub-array of the form {tokenQualifier, token},
     * where tokenQualifier is either "v" (variable name), "u" (unknown symbol) or "s" (known symbol),
     * and provides some lexical information (to be used by {@link BooleanLogicParser}) alongside the token.
     *
     * An example output would be:
     * <code>analyze("P -> Q"); // outputs {{"v", "P"}, {"s", "->"}, {"v", "Q"}}</code>
     */
    public static String[][] analyze(String formula) {
        String f = Objects.requireNonNull(formula).replaceAll(" ", "");
        String regex = "(?<name>\\w+)|[&|^!()]|->|<->|(?<unknown>.)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(f);

        ArrayList<String[]> tokens = new ArrayList<>();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            String tokenQualifier = "s";
            if (matcher.group("unknown") != null) {
                tokenQualifier = "u";
            }
            else if (matcher.group("name") != null) {
                tokenQualifier = "v";
            }

            String[] result = {tokenQualifier, f.substring(start, end)};
            tokens.add(result);
        }

        return tokens.toArray(new String[0][0]);
    }
}
