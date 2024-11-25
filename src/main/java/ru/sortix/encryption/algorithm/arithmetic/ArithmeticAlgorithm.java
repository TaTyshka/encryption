package ru.sortix.encryption.algorithm.arithmetic;

public interface ArithmeticAlgorithm {
    default String execute(String u, String v, int base) {
        throw new UnsupportedOperationException("Не поддерживается для данного алгоритма.");
    }
}

