package ru.sortix.encryption.algorithm.arithmetic;

public class FastMultiplicationAlgorithm implements ArithmeticAlgorithm {
    @Override
    public String execute(String u, String v, int base) {
        int n = u.length(); // длина первого числа
        int m = v.length(); // длина второго числа
        int[] result = new int[n + m]; // массив для хранения результата

        // Основной цикл вычисления
        for (int i = 0; i < n; i++) {
            int digitU = Character.getNumericValue(u.charAt(n - 1 - i)); // текущий разряд u
            int carry = 0;

            for (int j = 0; j < m; j++) {
                int digitV = Character.getNumericValue(v.charAt(m - 1 - j)); // текущий разряд v

                // Умножение и добавление к текущему разряду результата
                int sum = result[i + j] + digitU * digitV + carry;
                result[i + j] = sum % base; // записываем остаток
                carry = sum / base;        // перенос в старший разряд
            }

            // Если после обработки всех разрядов v остался перенос
            if (carry > 0) {
                result[i + m] += carry;
            }
        }

        // Преобразуем результат в строку
        StringBuilder resultString = new StringBuilder();
        boolean leadingZero = true; // флаг для пропуска ведущих нулей

        for (int i = result.length - 1; i >= 0; i--) {
            if (leadingZero && result[i] == 0) {
                continue; // пропускаем ведущие нули
            }
            leadingZero = false;
            resultString.append(result[i]);
        }

        return resultString.isEmpty() ? "0" : resultString.toString(); // если все цифры нули, возвращаем "0"
    }
}

