package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class ShortTag extends Tag<Short> {
    /**
     * The value.
     */
    private final short value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public ShortTag(String name, short value) {
        super(TagType.TAG_SHORT, name);
        this.value = value;
    }

    @Override
    public Short getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Short" + append + ": " + value;
    }

    public ShortTag clone() {
        return new ShortTag(getName(), value);
    }
}
