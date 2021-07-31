package io.github.nosequel.core.shared.punishment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PunishmentType {

    BAN("banned"),
    MUTE("muted"),
    KICK("kicked"),
    WARNING("warned"),
    BLACKLIST("blacklisted");

    private final String fancyName;

}
