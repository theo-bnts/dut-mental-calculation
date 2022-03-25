package fr.bnts.mentalcalculation.entities;

public enum Operation {

    ADD('+'),
    MULTIPLY('x'),
    SUBTRACT('-'),
    DIVIDE('/');

    private final char symbol;

    Operation(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

}