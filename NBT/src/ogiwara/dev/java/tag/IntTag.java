package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class IntTag extends Tag<Integer>{
    /**
     * The value.
     */
    private final int value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public IntTag(String name, int value) {
        super(TagType.TAG_INT, name);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Int" + append + ": " + value;
    }

    public IntTag clone() {
        return new IntTag(getName(), value);
    }
}
