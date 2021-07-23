package io.github.nosequel.core.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.LongSerializationPolicy;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PacmanConstants {

    public JsonParser PARSER = new JsonParser();
    public Gson GSON = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .setPrettyPrinting().create();

}
