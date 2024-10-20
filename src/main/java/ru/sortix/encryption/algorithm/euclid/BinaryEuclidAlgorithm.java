package ru.sortix.encryption.algorithm.euclid;

public class BinaryEuclidAlgorithm implements EuclidAlgorithm {

    @Override
    public String findGCD(int a, int b) {
        if (a == 0) return "НОД: " + b;
        if (b == 0) return "НОД: " + a;

        int shift;
        for (shift = 0; ((a | b) & 1) == 0; ++shift) {
            a >>= 1;
            b >>= 1;
        }

        while ((a & 1) == 0) a >>= 1;

        do {
            while ((b & 1) == 0) b >>= 1;

            if (a > b) {
                int temp = b;
                b = a;
                a = temp;
            }

            b = b - a;
        } while (b != 0);

        return "НОД: " + (a << shift);
    }
}