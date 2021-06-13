package io.github.nosequel.core.bukkit.grant.menu.duration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantDurationType {

    DAY("1 day", 60 * 60 * 24),
    WEEK("1 week", 60 * 60 * 24 * 7),
    MONTH("1 month", 60 * 60 * 24 * 7 * 4),
    YEAR("1 year", 60 * 60 * 24 * 7 * 4 * 12);

    private final String display;
    private final long duration;

}