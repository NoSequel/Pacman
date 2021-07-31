package io.github.nosequel.core.bukkit.util;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Function;

@UtilityClass
public class JavaUtil {

    public <T, R> R ifPresentOrElse(Optional<T> optional, Function<T, R> present, Function<T, R> absent) {
        if(optional.isPresent()) {
            return present.apply(optional.get());
        } else {
            return absent.apply(null);
        }
    }
}