package ogiwara.dev.java.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class ListTag<T extends Tag<?>> extends Tag<List<?>> {
    /**
     * The type of entries within this list.
     */
    private final Class<T> type;
    /**
     * The value.
     */
    private final List<T> value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param type The type of item in the list.
     * @param value The value.
     */
    public ListTag(String name, Class<T> type, List<T> value) {
        super(TagType.TAG_LIST, name);
        this.type = type;
        this.value = Collections.unmodifiableList(value);
    }

    /**
     * Gets the type of item in this list.
     *
     * @return The type of item in this list.
     */
    public Class<T> getElementType() {
        return type;
    }

    @Override
    public List<T> getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }

        StringBuilder bldr = new StringBuilder();
        bldr.append("TAG_List").append(append).append(": ").append(value.size()).append(" entries of type ").append(TagType.getByTagClass(type).getTypeName()).append("\r\n{\r\n");
        for (Tag t : value) {
            bldr.append("   ").append(t.toString().replaceAll("\r\n", "\r\n   ")).append("\r\n");
        }
        bldr.append("}");
        return bldr.toString();
    }

    @SuppressWarnings ("unchecked")
    public ListTag<T> clone() {
        List<T> newList = new ArrayList<T>();

        for (T v : value) {
            newList.add((T) v.clone());
        }

        return new ListTag<T>(getName(), type, newList);
    }
}
