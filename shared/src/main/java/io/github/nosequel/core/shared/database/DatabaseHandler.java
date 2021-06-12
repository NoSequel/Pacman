package io.github.nosequel.core.shared.database;

import com.google.gson.JsonObject;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface DatabaseHandler {

    /**
     * Retrieve all json objects from the database
     *
     * @param collection the collection to find the objects in
     * @return the set of the objects
     */
    CompletableFuture<Set<JsonObject>> retrieveAll(String collection);

    /**
     * Retrieve a single json object from the database.
     *
     * @param id         the identifier to find it by
     * @param collection the collection to find the object in
     * @return the singular json object
     */
    CompletableFuture<JsonObject> retrieveOne(String id, String collection);

    /**
     * Update a singular json object to the database.
     *
     * @param object         the object to update
     * @param collectionName the name of the collection to update it in
     * @param id             the identifier of the object
     */
    void update(JsonObject object, String collectionName, String id);

    /**
     * Delete a singular json object from the database.
     *
     * @param collectionName the name of the collection to delete it from
     * @param id             the identifier of the object
     */
    void delete(String collectionName, String id);

}