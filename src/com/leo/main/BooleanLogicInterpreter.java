package com.leo.main;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class BooleanLogicInterpreter {
    private BooleanLogicInterpreter() {
    }

    private static Map<String, Integer> propValues;
    private static Expr ast;
    public static int interpret(final Expr ast, int[] truthValues) {
        BooleanLogicInterpreter.ast = Objects.requireNonNull(ast);

        propValues = new LinkedHashMap<>();
        String[] propNames = BooleanLogicParser.getPropositionNames().toArray(new String[0]);
        for (int i = 0; i < propNames.length; i++) {
            propValues.put(propNames[i], truthValues[i]);
        }
        return interpret(ast);
    }

    private static int interpret(final Expr ast) {
        return switch (ast) {
            case Expr.BinaryOperation binaryOp -> interpret(binaryOp);
            case Expr.UnaryOperation unaryOp -> interpret(unaryOp);
            case Expr.Grouping grouping -> interpret(grouping);
            case Expr.Proposition proposition -> interpret(proposition);
            default -> throw new IllegalStateException("Unexpected value: " + ast);
        };
    }

    private static int not(int a) {
        return a ^ 1;
    }

    private static int interpret(final Expr.BinaryOperation binaryOp) {
        int left = interpret(binaryOp.left);
        int right = interpret(binaryOp.right);

        return switch (binaryOp.operator.type) {
            case OR   -> left | right;
            case AND  -> left & right;
            case XOR  -> left ^ right;
            case THEN -> not(left) | right;
            case IFF  -> not(left ^ right);
            case NOT, PROP_NAME, LEFT_PAREN, RIGHT_PAREN, EOL ->
                throw new IllegalStateException("Unexpected operator type: '" + binaryOp.operator.type + "'");
        };
    }

    private static int interpret(final Expr.UnaryOperation unaryOp) {
        int propValue = interpret(unaryOp.expression);
        return not(propValue);
    }

    private static int interpret(final Expr.Grouping grouping) {
        return interpret(grouping.expression);
    }

    private static int interpret(final Expr.Proposition prop) {
        return propValues.get(prop.propositionName.name);
    }
}
