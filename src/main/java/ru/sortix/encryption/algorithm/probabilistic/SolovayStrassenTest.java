package ru.sortix.encryption.algorithm.probabilistic;

import java.math.BigInteger;
import java.util.Random;

/**
 * Реализует тест Соловея-Штрассена для проверки числа на простоту.
 * Этот тест использует символ Якоби для вероятностной проверки простоты числа.
 */
public class SolovayStrassenTest implements PrimeTest {

    private final Random random = new Random();

    /**
     * Проверяет, является ли число вероятно простым с использованием теста Соловея-Штрассена.
     * @param n Число, которое нужно проверить.
     * @param iterations Число итераций для увеличения точности.
     * @return true, если число вероятно простое, иначе false.
     */
    @Override
    public boolean isProbablePrime(int n, int iterations) {
        if (n < 5 || n % 2 == 0) return false;  // Отсекаем числа меньше 5 и четные числа.

        for (int i = 0; i < iterations; i++) {
            int a = random.nextInt(n - 3) + 2;  // Выбираем случайное основание в диапазоне [2, n-2]
            int jacobiSymbol = JacobiSymbol.compute(a, n);
            if (jacobiSymbol == 0) return false;  // Если символ Якоби равен 0, число составное

            BigInteger expResult = BigInteger.valueOf(a).modPow(BigInteger.valueOf((n - 1) / 2), BigInteger.valueOf(n));
            if (!expResult.equals(BigInteger.valueOf(jacobiSymbol < 0 ? n - 1 : jacobiSymbol))) {
                return false;  // Проверка условия Соловея-Штрассена
            }
        }
        return true;
    }
}
