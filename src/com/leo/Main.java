package com.leo;

public class Main {
    public static void main(String[] args) {
        String[] tokens = BooleanLogicLexer.analyze("P -> (Q <-> !R)");
        for (String s : tokens) {
            System.out.println("'" + s + "'");
        }
    }
}
