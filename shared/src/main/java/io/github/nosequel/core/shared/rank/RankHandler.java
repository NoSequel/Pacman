package io.github.nosequel.core.shared.rank;

import io.github.nosequel.core.shared.rank.metadata.Metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RankHandler {

    private final List<Rank> ranks = new ArrayList<>();

    /**
     * Register a new rank to the {@link RankHandler#ranks} list
     *
     * @param rank the rank to register
     */
    public Rank register(Rank rank) {
        this.ranks.add(rank);
        return rank;
    }

    /**
     * Find a rank by {@link Rank#getName()} using the provided {@link String} argument
     *
     * @param name the name to find the rank by
     * @return the optional of the rank
     */
    public Optional<Rank> find(String name) {
        return this.ranks.stream()
                .filter(rank -> rank.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Find a rank by {@link Rank#getUniqueId()} ()} using the provided {@link UUID} argument
     *
     * @param uuid the uuid to find the rank by
     * @return the optional of the rank
     */
    public Optional<Rank> find(UUID uuid) {
        return this.ranks.stream()
                .filter(rank -> rank.getUniqueId().equals(uuid))
                .findFirst();
    }

    /**
     * Find all of the ranks with a specific {@link Metadata} object.
     *
     * @param metadata the metadata the rank must have
     * @return the list of the ranks with the provided metadata
     */
    public List<Rank> findRankWithMetadata(Metadata metadata) {
        return this.ranks.stream()
                .filter(rank -> rank.hasMetadata(metadata))
                .collect(Collectors.toList());
    }

    /**
     * Find all of the default ranks registered.
     * <p>
     * Registers a new default rank if no default rank could
     * be found, using the {@link Rank#Rank(UUID, String, Metadata...)} constructor.
     *
     * @return the list of the default ranks
     */
    public List<Rank> findDefault() {
        final List<Rank> ranks = new ArrayList<>();

        for (Rank rank : this.ranks) {
            if(rank.hasMetadata(Metadata.DEFAULT)) {
                ranks.add(rank);
            }
        }

        if(ranks.isEmpty()) {
            ranks.add(this.register(new Rank(UUID.randomUUID(), "Default", Metadata.DEFAULT)));
        }

        return ranks;
    }
}