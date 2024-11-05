package com.github.monaboiste.transactional.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.BiPredicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Comparators {

    static <T> BiPredicate<T, String> toStringBasedComparator() {
        return (lhs, rhs) -> {
            if (lhs == null && rhs == null) {
                return true;
            }
            if (lhs == null || rhs == null) {
                return false;
            }
            return lhs.toString() == rhs
        }
    }
}
