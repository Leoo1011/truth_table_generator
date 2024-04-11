package com.leo;

public class ExprPrinter {
    static void printExpr(Expr expr) {
        if (expr instanceof Expr.Proposition prop) printExpr(prop);
        else if (expr instanceof Expr.UnaryOperation unaryOp) printExpr(unaryOp);
        else if (expr instanceof Expr.Grouping grouping) printExpr(grouping);
        else if (expr instanceof Expr.BinaryOperation binaryOp) printExpr(binaryOp);
    }

    static void printExpr(Expr.Proposition proposition) {
        System.out.print(proposition.propositionName.name);
    }

    static void printExpr(Expr.UnaryOperation unaryOperation) {
        System.out.print("!");
        printExpr(unaryOperation.expression);
    }

    static void printExpr(Expr.Grouping grouping) {
        System.out.print("(");
        printExpr(grouping.expression);
        System.out.print(")");
    }

    static void printExpr(Token operator) {
        System.out.print(operator.type);
    }

    static void printExpr(Expr.BinaryOperation binaryOperation) {
        printExpr(binaryOperation.left);
        printExpr(binaryOperation.operator);
        printExpr(binaryOperation.right);
    }
}