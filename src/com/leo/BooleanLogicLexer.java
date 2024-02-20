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
     * (not assumed to be well-formed). Prefaces "variable" (proposition) names
     * with "v" and unknown symbols with "u". Recognized symbols are & (and), | (or), ^ (xor),
     * ! (not), -> (implication), <-> (double implication),and parentheses. Spaces are ignored.
     *
     * @param formula the formula to analyze
     * @return array of String's, each a token in the formula (connectors, propositions, parens, etc.).
     */
    public static String[] analyze(String formula) {
        String f = Objects.requireNonNull(formula).replaceAll(" ", "");
        String regex = "(?<name>\\w+)|[&|^!()]|->|<->|(?<unknown>.)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(f);

        ArrayList<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            String tokenQualifier = "";
            if (matcher.group("unknown") != null) {
                tokenQualifier = "u";
            }
            else if (matcher.group("name") != null) {
                tokenQualifier = "v";
            }
            tokens.add(tokenQualifier + f.substring(start, end));
        }
        return tokens.toArray(new String[0]);
    }
}
