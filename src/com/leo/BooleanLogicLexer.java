package com.leo;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BooleanLogicLexer {
    private BooleanLogicLexer() {
    }

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
                tokenQualifier = "u"; // 'u' for 'unknown'
            }
            if (matcher.group("name") != null) {
                tokenQualifier = "v"; // 'v' for 'var'/'varname'
            }
            tokens.add(tokenQualifier + f.substring(start, end));
        }
        return tokens.toArray(new String[0]);
    }
}
