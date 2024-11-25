package ru.sortix.encryption.algorithm.arithmetic;

import java.math.BigInteger;

public class DivisionAlgorithm implements ArithmeticAlgorithm {

    @Override
    public String execute(String u, String v, int base) {
        // Преобразуем входные строки в числа BigInteger в указанной системе счисления
        BigInteger U = new BigInteger(u, base);
        BigInteger V = new BigInteger(v, base);

        // Проверка деления на ноль
        if (V.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }

        // Создаем объекты для частного и остатка
        BigInteger q = BigInteger.ZERO; // Частное
        BigInteger r = U;               // Остаток

        // Основной процесс деления
        while (r.compareTo(V) >= 0) {
            // Вычисляем разряд частного
            BigInteger q_digit = r.divide(V);
            q = q.add(q_digit);

            // Обновляем остаток
            r = r.subtract(q_digit.multiply(V));
        }

        // Возвращаем результат в виде строки в нужной системе счисления
        return "Quotient: " + q.toString(base) + ", Remainder: " + r.toString(base);
    }
}
