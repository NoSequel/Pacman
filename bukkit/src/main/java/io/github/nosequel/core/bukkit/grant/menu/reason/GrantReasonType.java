package io.github.nosequel.core.bukkit.grant.menu.reason;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantReasonType {

    PURCHASED("Purchased"),
    GIVEAWAY("Giveaway Winner"),
    DEMOTION("Demoted"),
    PROMOTION("Promoted"),
    EVENT("Won an Event"),
    ISSUES("An issue occurred");

    private final String display;

}
