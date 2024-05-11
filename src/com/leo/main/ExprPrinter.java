package com.leo.main;

import java.io.PrintStream;
import java.util.Objects;

public class ExprPrinter {
    public static void printExpr(Expr expr) {
        printExpr(expr, System.out);
    }

    public static void printExpr(Expr expr, PrintStream out) {
        Objects.requireNonNull(expr);
        switch (expr) {
            case Expr.Proposition prop -> printExpr(prop, out);
            case Expr.UnaryOperation unaryOp -> printExpr(unaryOp, out);
            case Expr.Grouping grouping -> printExpr(grouping, out);
            case Expr.BinaryOperation binaryOp -> printExpr(binaryOp, out);
            default -> {}
        }
    }

    static void printExpr(Expr.Proposition proposition, PrintStream out) {
        out.print(proposition.propositionName.name);
    }

    static void printExpr(Expr.UnaryOperation unaryOperation, PrintStream out) {
        out.print("(not ");
        printExpr(unaryOperation.expression);
        out.print(")");
    }

    static void printExpr(Expr.Grouping grouping, PrintStream out) {
        out.print("(grouping ");
        printExpr(grouping.expression, out);
        out.print(")");
    }

    static void printExpr(Token operator, PrintStream out) {
        out.print(operator.type);
    }

    static void printExpr(Expr.BinaryOperation binaryOperation, PrintStream out) {
        out.print("(");
        printExpr(binaryOperation.operator, out);
        out.print(" ");
        printExpr(binaryOperation.left, out);
        out.print(" ");
        printExpr(binaryOperation.right, out);
        out.print(")");
    }
}
