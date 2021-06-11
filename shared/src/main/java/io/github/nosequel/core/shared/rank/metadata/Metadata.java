package io.github.nosequel.core.shared.rank.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Metadata {

    DEFAULT("§fDefault", "GRASS"),
    DONATOR("§aDonator", "EMERALD"),
    STAFF("§9Staff", "DIAMOND"),
    HIDDEN("§7Hidden", "GLASS");

    private final String displayName;
    private final String displayItem;

}