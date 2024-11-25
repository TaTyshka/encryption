package ru.sortix.encryption.algorithm.arithmetic;

public class AdditionAlgorithm implements ArithmeticAlgorithm {
    @Override
    public String execute(String u, String v, int base) {
        int n = u.length();
        int carry = 0;
        StringBuilder result = new StringBuilder();

        for (int i = n - 1; i >= 0; i--) {
            int digitU = Character.getNumericValue(u.charAt(i));
            int digitV = Character.getNumericValue(v.charAt(i));

            int sum = digitU + digitV + carry;
            result.append(sum % base);
            carry = sum / base;
        }

        result.append(carry); // Добавляем старший разряд, если он есть
        return result.reverse().toString();
    }
}
