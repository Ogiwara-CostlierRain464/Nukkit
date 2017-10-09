package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class LongTag extends Tag<Long> {
    /**
     * The value.
     */
    private final long value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public LongTag(String name, long value) {
        super(TagType.TAG_LONG, name);
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Long" + append + ": " + value;
    }

    public LongTag clone() {
        return new LongTag(getName(), value);
    }
}
