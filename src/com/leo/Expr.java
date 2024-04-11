package com.leo;

abstract class Expr {
    static class UnaryOperation extends Expr {
        final Expr expression;
        UnaryOperation(Expr expression) {
            this.expression = expression;
        }
    }

    static class BinaryOperation extends Expr {
        final Expr left;
        final Token operator;
        final Expr right;
        BinaryOperation(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
    }

    static class Grouping extends Expr {
        final Expr expression;
        Grouping(Expr expression) {
            this.expression = expression;
        }
    }

    static class Proposition extends Expr {
        final PropositionName propositionName;
        Proposition(PropositionName propositionName) {
            this.propositionName = propositionName;
        }
    }
}
