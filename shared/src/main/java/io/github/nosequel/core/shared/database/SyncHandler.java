package io.github.nosequel.core.shared.database;

import com.google.gson.*;

import io.github.nosequel.core.shared.database.sync.DataSyncHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SyncHandler {

    public static Gson GSON = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .setPrettyPrinting().create();

    public static JsonParser PARSER = new JsonParser();

    private final List<DataSyncHandler<?>> syncHandlers = new ArrayList<>();
    private io.github.nosequel.core.shared.database.sync.SyncHandler syncHandler;

    /**
     * Push an {@link Object} as data to the {@link io.github.nosequel.core.shared.database.sync.SyncHandler}.
     * This method automatically parses the {@link Object} into a {@link JsonObject}
     * using {@link Gson} and {@link JsonParser}.
     *
     * @param object the object to parse and publish
     */
    public void pushData(Object object) {
        System.out.println("test");

        final String jsonString = GSON.toJson(object);
        final JsonObject jsonObject = PARSER.parse(jsonString).getAsJsonObject();

        this.syncHandler.publishData(jsonObject);
    }
}