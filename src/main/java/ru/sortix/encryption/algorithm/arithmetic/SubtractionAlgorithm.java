package ru.sortix.encryption.algorithm.arithmetic;

public class SubtractionAlgorithm implements ArithmeticAlgorithm {
    @Override
    public String execute(String u, String v, int base) {
        int n = u.length();
        int borrow = 0;
        StringBuilder result = new StringBuilder();

        for (int i = n - 1; i >= 0; i--) {
            int digitU = Character.getNumericValue(u.charAt(i));
            int digitV = Character.getNumericValue(v.charAt(i)) + borrow;

            if (digitU < digitV) {
                digitU += base;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.append(digitU - digitV);
        }

        return result.reverse().toString();
    }
}
