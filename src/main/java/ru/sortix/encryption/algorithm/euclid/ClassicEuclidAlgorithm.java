package ru.sortix.encryption.algorithm.euclid;

public class ClassicEuclidAlgorithm implements EuclidAlgorithm {

    @Override
    public String findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return "НОД: " + a;
    }
}