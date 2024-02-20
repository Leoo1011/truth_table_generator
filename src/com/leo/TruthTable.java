package com.leo;

import java.util.Objects;

public class TruthTable {
    public TruthTable() {
    }

    /**
     * Pads string s from the left, with fillChar.
     * @param text String to pad
     * @param length total desired length of the returned String
     * @return the String s, padded from the left, with 0s
     */
    private static String zeroPadLeft(String text, int length) {
        String str = Objects.requireNonNull(text);
        String padding = "0";
        if (str.length() < length) {
            int lengthDiff = length - str.length();
            return padding.repeat(lengthDiff) + str;
        }
        return str;
    }

    /**
     * Generates every permutation of values for the propositions in a truth table
     * with nProps propositions.
     * @param nProps the amount of propositions.
     * @return String array containing each permutation in the form of 1's (true) and 0's (false).
     * An example is:
     * <code>generatePropositionPermutations(2) == {"00", "01", "10", "11"}</code>
     */
    public static String[] generatePropositionPermutations(int nProps) {
        int numPerms = ((int) Math.pow(2, nProps));
        String[] permutations = new String[numPerms];
        for (int i = 0; i < numPerms; i++) {
            String bin = Integer.toBinaryString(i);
            permutations[i] = zeroPadLeft(bin, nProps);
        }
        return permutations;
    }
}
