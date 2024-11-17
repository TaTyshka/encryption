package ru.sortix.encryption.algorithm.pollard;

import java.math.BigInteger;
import java.util.List;

public class PollardLogMethod {

    public static class Step {
        private final int i;
        private final BigInteger c;
        private final String logC;
        private final BigInteger d;
        private final String logD;

        public Step(int i, BigInteger c, String logC, BigInteger d, String logD) {
            this.i = i;
            this.c = c;
            this.logC = logC;
            this.d = d;
            this.logD = logD;
        }

        public int getI() {
            return i;
        }

        public String getC() {
            return c.toString();
        }

        public String getLogC() {
            return logC;
        }

        public String getD() {
            return d.toString();
        }

        public String getLogD() {
            return logD;
        }
    }

    private final BigInteger p;
    private final BigInteger a;
    private final BigInteger b;
    private final BigInteger r;

    public PollardLogMethod(BigInteger p, BigInteger a, BigInteger b, BigInteger r) {
        this.p = p;
        this.a = a;
        this.b = b;
        this.r = r;
    }

    public BigInteger solve(PollardFunction f, List<Step> steps) {
        BigInteger u1 = BigInteger.valueOf(2); // Начальные значения u и v
        BigInteger v1 = BigInteger.valueOf(2);
        BigInteger c = a.pow(u1.intValue()).multiply(b.pow(v1.intValue())).mod(p);
        BigInteger u2 = u1;
        BigInteger v2 = v1;
        BigInteger d = c;

        int stepCounter = 0;

        while (true) {
            stepCounter++;

            // Обновляем c
            BigInteger prevC = c;
            c = f.apply(c).mod(p);

            // Обновляем u1 и v1 на основе функции f
            if (prevC.compareTo(r) < 0) {
                u1 = u1.add(BigInteger.ONE).mod(r);
            } else {
                v1 = v1.add(BigInteger.ONE).mod(r);
            }

            // Обновляем d (двойной шаг)
            BigInteger prevD1 = d;
            d = f.apply(d).mod(p);

            BigInteger prevD2 = d;
            d = f.apply(d).mod(p);

            // Обновляем u2 и v2 для первого применения f
            if (prevD1.compareTo(r) < 0) {
                u2 = u2.add(BigInteger.ONE).mod(r);
            } else {
                v2 = v2.add(BigInteger.ONE).mod(r);
            }

            // Обновляем u2 и v2 для второго применения f
            if (prevD2.compareTo(r) < 0) {
                u2 = u2.add(BigInteger.ONE).mod(r);
            } else {
                v2 = v2.add(BigInteger.ONE).mod(r);
            }

            // Добавляем шаги в список
            steps.add(new Step(stepCounter, c, u1 + "+" + v1 + "x", d, u2 + "+" + v2 + "x"));

            // Если c == d, решаем уравнение
            if (c.equals(d)) {
                // Решаем уравнение: u1 + v1*x ≡ u2 + v2*x (mod r)
                BigInteger deltaU = u2.subtract(u1).mod(r);
                BigInteger deltaV = v1.subtract(v2).mod(r);

                if (deltaV.equals(BigInteger.ZERO)) {
                    return null; // Решений нет
                }

                return deltaU.multiply(deltaV.modInverse(r)).mod(r);
            }
        }
    }
}