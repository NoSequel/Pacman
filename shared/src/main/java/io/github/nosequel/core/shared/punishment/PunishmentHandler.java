package io.github.nosequel.core.shared.punishment;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PunishmentHandler {

    private final PunishmentRepository repository;
    private final List<Punishment> punishments = new ArrayList<>();

}
