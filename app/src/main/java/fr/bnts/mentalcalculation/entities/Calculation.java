package fr.bnts.mentalcalculation.entities;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import fr.bnts.mentalcalculation.Tools;

public class Calculation {

    private int firstMember;
    private int secondMember;
    private Operation operationType;
    private float result;

    public Calculation(boolean unsignedIntegerResult) {
        super();

        do {
            this.firstMember = getRandom();
            this.secondMember = getRandom();

            Operation[] operationTypes = { Operation.ADD, Operation.SUBTRACT, Operation.MULTIPLY, Operation.DIVIDE };

            Random random = new Random();
            int randomIndex = random.nextInt(operationTypes.length);
            this.operationType = operationTypes[randomIndex];

            this.compute();
        } while (unsignedIntegerResult && (this.result < 0 || !Tools.isInteger(this.result)));
    }

    private int getRandom() {
        int randomMinimumValue = 1;
        int randomMaximumValue = 10;

        return ThreadLocalRandom.current().nextInt(randomMinimumValue, randomMaximumValue + 1);
    }

    private void compute() {
        switch (this.operationType) {
            case ADD:
                this.result = this.firstMember + this.secondMember;
                break;
            case SUBTRACT:
                this.result = this.firstMember - this.secondMember;
                break;
            case MULTIPLY:
                this.result = this.firstMember * this.secondMember;
                break;
            case DIVIDE:
                this.result = (float)this.firstMember / this.secondMember;
        }
    }

    public String serialize() {
        String serializedFirstRandom = Integer.toString(this.firstMember);
        String serializedSecondRandom = Integer.toString(this.secondMember);

        return serializedFirstRandom +  ' ' + this.operationType.getSymbol() + ' ' + serializedSecondRandom;
    }

    public float getResult() {
        return result;
    }

}