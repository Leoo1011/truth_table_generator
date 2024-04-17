package com.leo;

import java.util.Objects;

public class ExprPrinter {
    public static void printExpr(Expr expr) {
        Objects.requireNonNull(expr);
        switch (expr) {
            case Expr.Proposition prop -> printExpr(prop);
            case Expr.UnaryOperation unaryOp -> printExpr(unaryOp);
            case Expr.Grouping grouping -> printExpr(grouping);
            case Expr.BinaryOperation binaryOp -> printExpr(binaryOp);
            default -> {}
        }
    }

    static void printExpr(Expr.Proposition proposition) {
        System.out.print(proposition.propositionName.name);
    }

    static void printExpr(Expr.UnaryOperation unaryOperation) {
        System.out.print("(not ");
        printExpr(unaryOperation.expression);
        System.out.print(")");
    }

    static void printExpr(Expr.Grouping grouping) {
        System.out.print("(grouping ");
        printExpr(grouping.expression);
        System.out.print(")");
    }

    static void printExpr(Token operator) {
        System.out.print(operator.type);
    }

    static void printExpr(Expr.BinaryOperation binaryOperation) {
        System.out.print("(");
        printExpr(binaryOperation.operator);
        System.out.print(" ");
        printExpr(binaryOperation.left);
        System.out.print(" ");
        printExpr(binaryOperation.right);
        System.out.print(")");
    }
}
