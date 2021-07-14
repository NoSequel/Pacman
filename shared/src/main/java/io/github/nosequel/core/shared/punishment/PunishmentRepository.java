package io.github.nosequel.core.shared.punishment;

import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.repository.BaseRepository;

public class PunishmentRepository extends BaseRepository<Punishment> {

    public PunishmentRepository(DatabaseHandler handler) {
        super("punishments", handler);
    }

    @Override
    public Class<Punishment> getType() {
        return Punishment.class;
    }
}
