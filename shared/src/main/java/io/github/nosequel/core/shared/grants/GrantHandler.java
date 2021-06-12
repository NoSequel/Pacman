package io.github.nosequel.core.shared.grants;

import io.github.nosequel.core.shared.CoreAPI;
import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.repository.BaseRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GrantHandler {

    private final GrantRepository repository;
    private final Set<Grant> grants = new HashSet<>();

    public void load() {
        this.repository.retrieve().thenAccept(grants::addAll);
    }

    /**
     * Register a new grant to the grant handler
     *
     * @param grant the grant to register
     */
    public void register(Grant grant) {
        this.grants.add(grant);
        this.repository.update(grant, grant.getUniqueId().toString());
    }

    /**
     * Delete a grant from the repository
     *
     * @param grant the grant to delete
     */
    public void delete(Grant grant) {
        this.grants.remove(grant);
        this.repository.delete(grant.getUniqueId().toString());
    }

    /**
     * Find the most prominent grant for a player
     *
     * @param uuid the unique identifier of the player
     * @return the most prominent grant
     */
    public Grant findProminentGrant(UUID uuid) {
        for (Grant grant : this.findGrants(uuid)) {
            if (grant.isActive()) {
                return grant;
            }
        }

        for (Rank rank : CoreAPI.getCoreAPI().getDefaultRanks()) {
            this.register(new Grant(uuid, rank.getUniqueId(), UUID.randomUUID(), UUID.randomUUID(), "New player", null));
        }

        return this.findProminentGrant(uuid);
    }

    /**
     * Find all of a player's grants
     *
     * @param uuid the unique identifier of the user to find it by
     * @return the collection of the gratns
     */
    public Collection<Grant> findGrants(UUID uuid) {
        return this.grants.stream()
                .filter(grant -> grant.getTarget().equals(uuid)).sorted(Comparator.comparingInt(grant -> -grant.getRank().getWeight()))
                .collect(Collectors.toList());
    }

    public static class GrantRepository extends BaseRepository<Grant> {

        public GrantRepository(DatabaseHandler handler) {
            super("grants", handler);
        }

        @Override
        public Class<Grant> getType() {
            return Grant.class;
        }
    }
}