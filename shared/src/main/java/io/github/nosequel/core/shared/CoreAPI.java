package io.github.nosequel.core.shared;

import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.database.mongo.MongoDatabaseHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoreAPI {

    private final DatabaseHandler databaseHandler;

    public CoreAPI() {
        this.databaseHandler = new MongoDatabaseHandler("127.0.0.1", 27017, "pacman", "", "", false);
    }
}