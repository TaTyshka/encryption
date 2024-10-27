package ru.sortix.encryption.algorithm.probabilistic;

import java.math.BigInteger;
import java.util.Random;

/**
 * Реализует тест Ферма для проверки числа на простоту.
 * Этот тест является вероятностным, поэтому определяет число как вероятно простое.
 * Повышение числа итераций увеличивает точность теста.
 */
public class FermatTest implements PrimeTest {

    private final Random random = new Random();

    /**
     * Проверяет, является ли число вероятно простым с использованием теста Ферма.
     * @param n Число, которое нужно проверить.
     * @param iterations Число итераций для увеличения точности.
     * @return true, если число вероятно простое, иначе false.
     */
    @Override
    public boolean isProbablePrime(int n, int iterations) {
        if (n < 5 || n % 2 == 0) return false;  // Отсекаем числа меньше 5 и четные числа.

        for (int i = 0; i < iterations; i++) {
            int a = random.nextInt(n - 4) + 2;  // Выбираем случайное основание в диапазоне [2, n-2]
            BigInteger base = BigInteger.valueOf(a);
            BigInteger result = base.modPow(BigInteger.valueOf(n - 1L), BigInteger.valueOf(n));
            if (!result.equals(BigInteger.ONE)) {  // Проверка условия Ферма
                return false;
            }
        }
        return true;
    }
}
