package com.github.monaboiste.transactional.util

import com.github.monaboiste.transactional.domain.Email
import groovy.transform.PackageScope
import lombok.AccessLevel
import lombok.AllArgsConstructor
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import org.jeasy.random.api.Randomizer

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@PackageScope
final class EmailRandomizer implements Randomizer<Email> {

    @Override
    Email getRandomValue() {
        def textGenerator = RandomStringUtils.insecure()
        def localPart = textGenerator.nextAlphabetic(10)
        def extension = sample(["com", "net", "org", "gov", "edu"])
        def domain = "${textGenerator.nextAlphabetic(10)}.${extension}"
        return new Email("${localPart}@${domain}")
    }

    private static <T> T sample(Collection<T> dataSet) {
        def numberGenerator = RandomUtils.insecure()
        def index = Integer.valueOf(numberGenerator.randomInt(0, dataSet.size()))
        return dataSet[index]
    }
}
