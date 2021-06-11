package io.github.nosequel.core.shared.database.mongo;

import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import io.github.nosequel.core.shared.CoreConstants;
import io.github.nosequel.core.shared.database.DatabaseHandler;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MongoDatabaseHandler implements DatabaseHandler {

    private final MongoDatabase database;
    private final Map<String, MongoCollection<Document>> collections = new HashMap<>();

    /**
     * Constructor to make a new mongo database handler
     *
     * @param hostname       the hostname of the database ot connect to
     * @param port           the port to use to connect to the hostname
     * @param database       the database name to connect to
     * @param username       the username to use for authentication administrator
     * @param password       the password of the provided user
     * @param authentication whether it should use authentication or not
     */
    public MongoDatabaseHandler(String hostname, int port, String database, String username, String password, boolean authentication) {
        final MongoClient client;

        if (authentication) {
            client = new MongoClient(
                    new ServerAddress(hostname, port),
                    Collections.singletonList(
                            MongoCredential.createCredential(
                                    username,
                                    database,
                                    password.toCharArray()
                            )
                    )
            );
        } else {
            client = new MongoClient(hostname, port);
        }

        this.database = client.getDatabase(database);
    }

    /**
     * Get a collection from the database.
     *
     * @param collectionName the name of the collection
     * @return the found collection
     */
    private MongoCollection<Document> getCollection(String collectionName) {
        if (!(this.collections.containsKey(collectionName))) {
            this.collections.put(collectionName, this.database.getCollection(collectionName));
        }

        return this.collections.get(collectionName);
    }

    /**
     * Retrieve all json objects from the database
     *
     * @param collectionName the collection to find the objects in
     * @return the set of the objects
     */
    @Override
    public CompletableFuture<Set<JsonObject>> retrieveAll(String collectionName) {
        return CompletableFuture.supplyAsync(() -> {
            final MongoCollection<Document> collection = this.getCollection(collectionName);
            final Set<JsonObject> objects = new HashSet<>();

            for (Document document : collection.find()) {
                objects.add(CoreConstants.PARSER.parse(document.toJson()).getAsJsonObject());
            }

            return objects;
        });
    }

    /**
     * Retrieve a single json object from the database.
     *
     * @param id             the identifier to find it by
     * @param collectionName the collection to find the object in
     * @return the singular json object
     */
    @Override
    public CompletableFuture<JsonObject> retrieveOne(String id, String collectionName) {
        return CompletableFuture.supplyAsync(() -> {
            final MongoCollection<Document> collection = this.getCollection(collectionName);
            final Document document = collection.find(Filters.eq("_id", id)).first();

            if (document == null) {
                return null;
            }

            return CoreConstants.PARSER.parse(document.toJson()).getAsJsonObject();
        });
    }

    /**
     * Update a singular json object to the database.
     *
     * @param object         the object to update
     * @param collectionName the name of the collection to update it in
     * @param id             the identifier of the object
     * @return whether updating was successful or not
     */
    @Override
    public CompletableFuture<Boolean> update(JsonObject object, String collectionName, String id) {
        return CompletableFuture.supplyAsync(() -> {
            final MongoCollection<Document> collection = this.getCollection(collectionName);

            return collection.updateOne(
                    Filters.eq("_id", id),
                    new Document("\\$set", object),
                    new UpdateOptions().upsert(true)
            ).wasAcknowledged();
        });
    }

    /**
     * Delete a singular json object from the database.
     *
     * @param collectionName the name of the collection to delete it from
     * @param id             the identifier of the object
     * @return whether deleting was successful or not
     */
    @Override
    public CompletableFuture<Boolean> delete(String collectionName, String id) {
        return CompletableFuture.supplyAsync(() -> {
            final MongoCollection<Document> collection = this.getCollection(collectionName);
            return collection.deleteOne(Filters.eq("_id", id)).wasAcknowledged();
        });
    }
}