package ru.sortix.encryption.algorithm.pollard;

import java.math.BigInteger;

public interface PollardFunction {
    BigInteger apply(BigInteger x);
}
