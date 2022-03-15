package fr.bnts.mentalcalculation;

public enum Operation {
    ADD('+'),
    MULTIPLY('x'),
    SUBSTRACT('-'),
    DIVIDE('/');

    private char symbol;

    Operation(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
