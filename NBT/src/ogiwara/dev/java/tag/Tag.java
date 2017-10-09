package ogiwara.dev.java.tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ogiwara on 2016/12/25.
 */
public abstract class Tag<T> implements Comparable<Tag<?>>{
    /**
     * The name of this tag.
     */
    private final String name;
    private final TagType type;

    /**
     * Creates the tag with no name.
     */
    public Tag(TagType type) {
        this(type, "");
    }

    /**
     * Creates the tag with the specified name.
     *
     * @param name The name.
     */
    public Tag(TagType type, String name) {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the name of this tag.
     *
     * @return The name of this tag.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the type of this tag
     *
     * @return The type of this tag.
     */
    public TagType getType() {
        return type;
    }

    /**
     * Gets the value of this tag.
     *
     * @return The value of this tag.
     */
    public abstract T getValue();

    /**
     * Clones a Map<String, Tag>
     *
     * @param map the map
     * @return a clone of the map
     */
    public static Map<String, Tag<?>> cloneMap(Map<String, Tag<?>> map) {
        if (map == null) {
            return null;
        }

        Map<String, Tag<?>> newMap = new HashMap<String, Tag<?>>();
        for (Map.Entry<String, Tag<?>> entry : map.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue().clone());
        }
        return newMap;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tag)) {
            return false;
        }
        Tag<?> tag = (Tag<?>) other;
        return getValue().equals(tag.getValue()) && getName().equals(tag.getName());
    }

    @Override
    public int compareTo(Tag other) {
        if (equals(other)) {
            return 0;
        } else {
            if (other.getName().equals(getName())) {
                throw new IllegalStateException("Cannot compare two Tags with the same name but different values for sorting");
            } else {
                return getName().compareTo(other.getName());
            }
        }
    }

    /**
     * Clones the Tag
     *
     * @return the clone
     */
    public abstract Tag<T> clone();
}
