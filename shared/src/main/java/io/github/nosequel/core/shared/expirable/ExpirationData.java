package io.github.nosequel.core.shared.expirable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpirationData {

    private final String reason;
    private final long date;

}
