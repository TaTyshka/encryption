package ru.sortix.encryption.algorithm.euclid;

public class ExtendedBinaryEuclidAlgorithm implements EuclidAlgorithm {

    @Override
    public String findGCD(int a, int b) {
        int originalA = a, originalB = b;  // Сохранение оригинальных значений для вывода
        int g = 1;  // Начальный множитель g

        // Убираем общие множители 2
        while (a % 2 == 0 && b % 2 == 0) {
            a /= 2;
            b /= 2;
            g *= 2;
        }

        int u = a, v = b;
        int A = 1, B = 0, C = 0, D = 1;

        // Основной цикл бинарного алгоритма Евклида
        while (u != 0) {
            // Убираем четность из u
            while (u % 2 == 0) {
                u /= 2;
                if (A % 2 == 0 && B % 2 == 0) {
                    A /= 2;
                    B /= 2;
                } else {
                    A = (A + b) / 2;
                    B = (B - a) / 2;
                }
            }

            // Убираем четность из v
            while (v % 2 == 0) {
                v /= 2;
                if (C % 2 == 0 && D % 2 == 0) {
                    C /= 2;
                    D /= 2;
                } else {
                    C = (C + b) / 2;
                    D = (D - a) / 2;
                }
            }

            // Сравнение u и v и их обновление
            if (u >= v) {
                u -= v;
                A -= C;
                B -= D;
            } else {
                v -= u;
                C -= A;
                D -= B;
            }
        }

        // Вычисление НОД и коэффициентов x и y
        int gcd = g * v;
        int x = C;
        int y = D;

        // Возврат результата
        return String.format("НОД: %d\nx = %d, y = %d\nФормула: %d * %d + %d * %d = %d",
                gcd, x, y, originalA, x, originalB, y, gcd);
    }
}
