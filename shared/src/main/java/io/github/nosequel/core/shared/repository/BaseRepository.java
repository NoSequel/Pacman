package io.github.nosequel.core.shared.repository;

import com.google.gson.JsonObject;
import io.github.nosequel.core.shared.CoreConstants;
import io.github.nosequel.core.shared.database.DatabaseHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Getter
@RequiredArgsConstructor
public abstract class BaseRepository<T> implements Repository<T> {

    private final String collectionName;
    private final DatabaseHandler handler;

    public abstract Class<T> getType();

    /**
     * Retrieve all objects inside of the repository
     *
     * @return the set of the objects inside of the repository
     */
    @Override
    public CompletableFuture<Set<T>> retrieve() {
        return CompletableFuture.supplyAsync(() -> {
            final Set<T> set = new HashSet<>();

            try {
                for (JsonObject jsonObject : handler.retrieveAll(this.collectionName).get()) {
                    set.add(CoreConstants.GSON.fromJson(jsonObject, this.getType()));
                }
            } catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }

            return set;
        });
    }

    /**
     * Retrieve a single object from the repository
     *
     * @param id the identifier of the object
     * @return the single object
     */
    @Override
    public CompletableFuture<T> retrieve(String id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return CoreConstants.GSON.fromJson(handler.retrieveOne(id, this.collectionName).get(), this.getType());
            } catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }

            return null;
        });
    }

    /**
     * Update an element inside of the repository
     *
     * @param value the value to update inside of the repository
     * @param id    the identifier of the value
     */
    @Override
    public void update(T value, String id) {
        this.handler.update(CoreConstants.PARSER.parse(CoreConstants.GSON.toJson(value)).getAsJsonObject(), this.collectionName, id);
    }

    /**
     * Delete an element from the repository
     *
     * @param id the identifier of the value
     */
    @Override
    public void delete(String id) {
        this.handler.delete(this.collectionName, id);
    }
}