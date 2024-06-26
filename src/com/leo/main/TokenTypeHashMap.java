package com.leo.main;

import java.util.HashMap;

import static com.leo.main.TokenType.*;

/**
 * Helper singleton to convert symbol literals to their enum counterpart.
 */
public class TokenTypeHashMap {
    private static HashMap<String, TokenType> tokenTypeHashMap;
    private TokenTypeHashMap() {
    }

    public static HashMap<String, TokenType> getHashMap() {
        if (tokenTypeHashMap == null) {
            tokenTypeHashMap = new HashMap<>();

            tokenTypeHashMap.put("|", OR);
            tokenTypeHashMap.put("&", AND);
            tokenTypeHashMap.put("^", XOR);
            tokenTypeHashMap.put("->", THEN);
            tokenTypeHashMap.put("<->", IFF);
            tokenTypeHashMap.put("!", NOT);
            tokenTypeHashMap.put("(", LEFT_PAREN);
            tokenTypeHashMap.put(")", RIGHT_PAREN);
        }
        return tokenTypeHashMap;
    }
}
