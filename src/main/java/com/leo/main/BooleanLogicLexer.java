package com.leo.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BooleanLogicLexer {
    private BooleanLogicLexer() {
    }

    public static class LexError extends Exception {
        public LexError(String message) {
            super(message);
        }
    }

    /**
     * Performs lexical analysis ("tokenization") on a boolean logic expression
     * (not assumed to be well-formed). Recognized symbols are names (alphanumeric) & (and), | (or), ^ (xor),
     * ! (not), -> (implication), <-> (double implication),and parentheses. Spaces are ignored.
     *
     * @param formula the formula to analyze
     * @return array of {@link Token}s ({@link PropositionName}s, {@link Connective}s and an {@link EndOfLine}
     * at the end), to be used by the {@link BooleanLogicParser}.
     * @throws LexError if there's any extraneous token.
     */
    public static Token[] tokenize(String formula) throws LexError {
        String f = Objects.requireNonNull(formula).replaceAll(" ", "");
        String regex = "(?<name>\\w+)|[&|^!()]|->|<->|(?<unknown>.)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(f);

        ArrayList<Token> tokens = new ArrayList<>();
        HashMap<String, TokenType> tokenTypeHashMap = TokenTypeHashMap.getHashMap();
        while (matcher.find()) {
            String tokenString = f.substring(matcher.start(), matcher.end());

            Token result;
            if (matcher.group("unknown") != null) {
                throw new LexError("Unknown symbol: " + tokenString);
            }
            else if (matcher.group("name") != null) {
                result = new PropositionName(tokenString);
            }
            else {
                TokenType tokenType = tokenTypeHashMap.get(tokenString);
                result = new Connective(tokenType);
            }
            tokens.add(result);
        }

        tokens.add(new EndOfLine());
        return tokens.toArray(new Token[0]);
    }
}
