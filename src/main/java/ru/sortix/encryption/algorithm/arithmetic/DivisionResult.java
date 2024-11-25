package ru.sortix.encryption.algorithm.arithmetic;

public class DivisionResult {
    private final int[] quotient;
    private final int[] remainder;

    public DivisionResult(int[] quotient, int[] remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }

    public int[] getQuotient() {
        return quotient;
    }

    public int[] getRemainder() {
        return remainder;
    }
}
