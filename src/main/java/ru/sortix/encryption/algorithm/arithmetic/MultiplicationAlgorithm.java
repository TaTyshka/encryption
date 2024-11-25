package ru.sortix.encryption.algorithm.arithmetic;

public class MultiplicationAlgorithm implements ArithmeticAlgorithm {
    @Override
    public String execute(String u, String v, int base) {
        int n = u.length();
        int m = v.length();
        int[] result = new int[n + m];

        for (int i = n - 1; i >= 0; i--) {
            int carry = 0;

            for (int j = m - 1; j >= 0; j--) {
                int digitU = Character.getNumericValue(u.charAt(i));
                int digitV = Character.getNumericValue(v.charAt(j));

                int sum = result[i + j + 1] + digitU * digitV + carry;
                result[i + j + 1] = sum % base;
                carry = sum / base;
            }

            result[i] += carry;
        }

        StringBuilder resultString = new StringBuilder();
        for (int num : result) {
            if (!(resultString.length() == 0 && num == 0)) {
                resultString.append(num);
            }
        }

        return resultString.length() == 0 ? "0" : resultString.toString();
    }
}

