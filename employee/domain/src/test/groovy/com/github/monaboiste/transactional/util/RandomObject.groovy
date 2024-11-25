package com.github.monaboiste.transactional.util

import lombok.AccessLevel
import lombok.AllArgsConstructor
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters

@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class RandomObject {

    private static final EasyRandomParameters parameters = new EasyRandomParameters()
            .randomize(field -> field.getName().toLowerCase().contains("email"), new EmailRandomizer())
    private static final EasyRandom easyRandom = new EasyRandom(parameters)

    static <T> T generate(final Class<T> clazz) {
        return easyRandom.nextObject(clazz);
    }
}
