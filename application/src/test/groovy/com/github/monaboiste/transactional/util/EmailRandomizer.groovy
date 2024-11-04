package com.github.monaboiste.transactional.util

import com.github.monaboiste.transactional.domain.employee.Email
import groovy.transform.PackageScope
import lombok.AccessLevel
import lombok.AllArgsConstructor
import org.apache.commons.lang3.RandomStringUtils
import org.jeasy.random.api.Randomizer

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@PackageScope
final class EmailRandomizer implements Randomizer<Email> {

    @Override
    Email getRandomValue() {
        def rd = new Random()
        def localPart = RandomStringUtils.randomAlphanumeric(10)
        def tlds = ["com", "net", "org", "gov", "edu"]
        def tld = tlds.get(rd.nextInt(tlds.size()))
        def domain = "${RandomStringUtils.randomAlphanumeric(10)}.${tld}"
        return new Email("${localPart}@${domain}")
    }
}
