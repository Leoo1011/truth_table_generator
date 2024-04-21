package com.leo;

public class BooleanLogicInterpreter {
    final Expr ast;
    BooleanLogicInterpreter(Expr parsedFormula) {
        ast = parsedFormula;
    }
}
