package io.github.nosequel.core.bukkit.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class TemporaryPlayerObject {

    private final String name;
    private final UUID uniqueId;

}
