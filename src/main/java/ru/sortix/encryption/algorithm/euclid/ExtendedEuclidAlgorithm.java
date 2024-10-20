package ru.sortix.encryption.algorithm.euclid;

public class ExtendedEuclidAlgorithm implements EuclidAlgorithm {

    @Override
    public String findGCD(int a, int b) {
        int x0 = 1, x1 = 0, y0 = 0, y1 = 1;
        int originalA = a, originalB = b;  // Сохранение оригинальных значений для вывода

        // Основной цикл алгоритма Евклида
        while (b != 0) {
            int q = a / b;  // Частное
            int r = a % b;  // Остаток

            // Замена значений a и b
            a = b;
            b = r;

            // Обновление коэффициентов для x
            int xTemp = x0 - q * x1;
            x0 = x1;
            x1 = xTemp;

            // Обновление коэффициентов для y
            int yTemp = y0 - q * y1;
            y0 = y1;
            y1 = yTemp;
        }

        // Вывод результатов
        return String.format("НОД: %d\nx = %d, y = %d\nФормула: %d * %d + %d * %d = %d",
                a, x0, y0, originalA, x0, originalB, y0, a);
    }
}
