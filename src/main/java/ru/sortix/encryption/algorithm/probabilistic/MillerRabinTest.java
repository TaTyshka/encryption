package ru.sortix.encryption.algorithm.probabilistic;

import java.math.BigInteger;
import java.util.Random;

/**
 * Реализует тест Миллера-Рабина для проверки числа на простоту.
 * Этот вероятностный тест точно определяет простоту числа при увеличении числа итераций.
 */
public class MillerRabinTest implements PrimeTest {

    private final Random random = new Random();

    /**
     * Проверяет, является ли число вероятно простым с использованием теста Миллера-Рабина.
     * @param n Число, которое нужно проверить.
     * @param iterations Число итераций для увеличения точности.
     * @return true, если число вероятно простое, иначе false.
     */
    @Override
    public boolean isProbablePrime(int n, int iterations) {
        if (n < 5 || n % 2 == 0) return false;  // Отсекаем числа меньше 5 и четные числа.

        int d = n - 1;
        int s = 0;
        while (d % 2 == 0) {  // Представляем n-1 как d * 2^s
            d /= 2;
            s++;
        }

        for (int i = 0; i < iterations; i++) {
            int a = random.nextInt(n - 4) + 2;  // Выбираем случайное основание в диапазоне [2, n-2]
            BigInteger base = BigInteger.valueOf(a);
            BigInteger nBig = BigInteger.valueOf(n);

            BigInteger x = base.modPow(BigInteger.valueOf(d), nBig);
            if (x.equals(BigInteger.ONE) || x.equals(nBig.subtract(BigInteger.ONE))) continue;

            boolean passed = false;
            for (int r = 0; r < s - 1; r++) {  // Проверяем значения последовательности x^2 mod n
                x = x.modPow(BigInteger.TWO, nBig);
                if (x.equals(nBig.subtract(BigInteger.ONE))) {
                    passed = true;
                    break;
                }
            }
            if (!passed) return false;  // Если ни одно из условий не выполнено, число составное
        }
        return true;
    }
}
