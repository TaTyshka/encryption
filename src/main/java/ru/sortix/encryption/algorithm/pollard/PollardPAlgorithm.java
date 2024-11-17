package ru.sortix.encryption.algorithm.pollard;

import javafx.collections.ObservableList;

import java.math.BigInteger;

public class PollardPAlgorithm {

    private final BigInteger n;
    private final BigInteger c;
    private final PollardFunction f;

    public PollardPAlgorithm(BigInteger n, BigInteger c, PollardFunction f) {
        this.n = n;
        this.c = c;
        this.f = f;
    }

    public static class PollardStep {
        private final int i;
        private final BigInteger a;
        private final BigInteger b;
        private final BigInteger d;

        public PollardStep(int i, BigInteger a, BigInteger b, BigInteger d) {
            this.i = i;
            this.a = a;
            this.b = b;
            this.d = d;
        }

        public int getI() {
            return i;
        }

        public String getA() {
            return a.toString();
        }

        public String getB() {
            return b.toString();
        }

        public String getD() {
            return d.toString();
        }
    }

    public BigInteger run(ObservableList<PollardStep> steps) {
        BigInteger a = c;
        BigInteger b = c;
        int i = 1;

        while (true) {
            // Вычисляем a <- f(a) (mod n) и b <- f(f(b)) (mod n)
            a = f.apply(a).mod(n);
            b = f.apply(f.apply(b)).mod(n);

            // НОД(а - b, n)
            BigInteger d = a.subtract(b).gcd(n);

            // Добавляем шаг в таблицу
            steps.add(new PollardStep(i++, a, b, d));

            // Если найден делитель
            if (d.compareTo(BigInteger.ONE) > 0 && d.compareTo(n) < 0) {
                return d;
            }

            // Если делитель не найден
            if (d.equals(n)) {
                return n;
            }
        }
    }
}
