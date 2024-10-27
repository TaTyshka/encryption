package ru.sortix.encryption.algorithm.probabilistic;

/**
 * Реализует вычисление символа Якоби, используемого в тестах на простоту.
 * Символ Якоби является расширением символа Лежандра и используется для проверки чисел.
 */
public class JacobiSymbol {

    private JacobiSymbol() {}

    /**
     * Вычисляет символ Якоби (a/n).
     * @param a Число, для которого вычисляется символ Якоби.
     * @param n Модуль для вычисления (должен быть нечетным и больше нуля).
     * @return Значение символа Якоби (1, -1 или 0).
     */
    public static int compute(int a, int n) {
        int result = 1;
        a = a % n;
        while (a != 0) {
            while (a % 2 == 0) {  // Учитываем четные a
                a /= 2;
                int nMod8 = n % 8;
                if (nMod8 == 3 || nMod8 == 5) {
                    result = -result;  // Меняем знак в зависимости от n mod 8
                }
            }
            int temp = a;
            a = n;
            n = temp;
            if (a % 4 == 3 && n % 4 == 3) {
                result = -result;  // Меняем знак при обоих нечетных модулях
            }
            a = a % n;
        }
        return n == 1 ? result : 0;
    }
}
