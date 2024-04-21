package com.leo;

public class TruthTable {
    private TruthTable() {
    }

    /**
     * Generates every permutation of values for the propositions in a truth table
     * with nProps propositions.
     * @param nProps the amount of propositions.
     * @return array of arrays of ints containing each permutation in the form of 1's (true) and 0's (false).
     * An example is:
     * {@code generatePropositionPermutations(2) == {{0, 0}, {0, 1}, {1, 0}, {1, 1}}}
     *
     * @implNote Counting from 0 to 3 (in binary) is:
     * <ul>
     * <li>00 (F, F)</li>
     * <li>01 (F, T)</li>
     * <li>10 (T, F)</li>
     * <li>11 (T, T)</li>
     * </ul>
     * This algorithm uses this fact, along with bitwise operations (and masking) to extract each individual
     * bit (truth value).
     */
    public static int[][] generateTruthValuePermutations(int nProps) {
        int nPerms = ((int) Math.pow(2, nProps));
        int[][] permutations = new int[nPerms][nProps];
        for (int num = 0; num < nPerms; num++) {  // binary counter
            for (int i = 0; i < nProps; i++) {
                int mask = 1 << (nProps - i - 1); // To extract bits from right to left.
                // '&' extracts the bit, dividing by 'mask' trims trailing 0s (shifting).
                int extractedBit = (num & mask) / mask;
                if (extractedBit == 0) continue;  // no need to set the bit, since arrays are zero-initialized
                permutations[num][i] = extractedBit;
            }
        }
        return permutations;
    }
}
