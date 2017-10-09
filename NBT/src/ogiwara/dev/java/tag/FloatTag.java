package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class FloatTag extends Tag<Float>{
    /**
     * The value.
     */
    private final float value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public FloatTag(String name, float value) {
        super(TagType.TAG_FLOAT, name);
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Float" + append + ": " + value;
    }

    public FloatTag clone() {
        return new FloatTag(getName(), value);
    }
}
