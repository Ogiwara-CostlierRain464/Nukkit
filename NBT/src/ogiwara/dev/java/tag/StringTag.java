package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class StringTag extends Tag<String> {
    /**
     * The value.
     */
    private final String value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public StringTag(String name, String value) {
        super(TagType.TAG_STRING, name);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_String" + append + ": " + value;
    }

    public StringTag clone() {
        return new StringTag(getName(), value);
    }
}
