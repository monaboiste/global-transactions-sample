package com.github.monaboiste.transactional.tracing;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

class RandomTraceIdGenerator implements TraceIdGenerator {
    private static final char[] CHARACTERS = "abcdef0123456789".toCharArray();
    private static final int LENGTH = 14;

    @Override
    @NotNull
    public Trace generate() {
        ThreadLocalRandom rd = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS[rd.nextInt(CHARACTERS.length)]);
        }
        return new Trace(sb.toString());
    }
}