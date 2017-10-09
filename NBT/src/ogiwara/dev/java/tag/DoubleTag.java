package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class DoubleTag extends Tag<Double>{
    /**
     * The value.
     */
    private final double value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public DoubleTag(String name, double value) {
        super(TagType.TAG_DOUBLE, name);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Double" + append + ": " + value;
    }

    public DoubleTag clone() {
        return new DoubleTag(getName(), value);
    }
}
