package io.github.nosequel.core.shared.rank;

import com.google.gson.annotations.SerializedName;
import io.github.nosequel.core.shared.rank.metadata.Metadata;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Rank {

    @SerializedName("_id")
    private final UUID uniqueId;

    private String name;

    private String prefix = "";
    private String suffix = "";
    private String color = "§f";

    private int weight = 0;

    private final Set<String> permissions = new HashSet<>();
    private final Set<Metadata> metadatum = new HashSet<>();

    /**
     * Constructor to make a new rank object
     *
     * @param uniqueId the unique identifier of the rank
     * @param name     the name of the rank
     */
    public Rank(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    /**
     * Constructor to make a new rank object
     *
     * @param uniqueId the unique identifier of the rank
     * @param name     the name of the rank
     */
    public Rank(UUID uniqueId, String name, Metadata... metadatum) {
        this.uniqueId = uniqueId;
        this.name = name;

        this.metadatum.addAll(Arrays.asList(metadatum));
    }


    /**
     * Check if the rank has a {@link Metadata} value
     *
     * @param metadata the metadata to check if the rank has
     * @return false if the rank doesn't have the metadata, or true if it does.
     */
    public boolean hasMetadata(Metadata metadata) {
        return this.metadatum.contains(metadata);
    }

    /**
     * Get the display name of the rank
     * <p>
     * This returns {@link Rank#color} + {@link Rank#name}
     * which is replaced by {@code String#replace("&", "§")} and {@code String#replace("_", " ")}
     *
     * @return the formatted display name.
     */
    public String getDisplayName() {
        return (this.color + this.name).replace("&", "§").replace("_", " ");
    }
}
