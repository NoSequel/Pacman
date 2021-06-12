package io.github.nosequel.core.shared.repository;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface Repository<T> {

    /**
     * Retrieve all objects inside of the repository
     *
     * @return the set of the objects inside of the repository
     */
    CompletableFuture<Set<T>> retrieve();

    /**
     * Retrieve a single object from the repository
     *
     * @param id the identifier of the object
     * @return the single object
     */
    CompletableFuture<T> retrieve(String id);

    /**
     * Update an element inside of the repository
     *
     * @param value the value to update inside of the repository
     * @param id    the identifier of the value
     */
    void update(T value, String id);

    /**
     * Delete an element from the repository
     *
     * @param id the identifier of the value
     */
    void delete(String id);

}