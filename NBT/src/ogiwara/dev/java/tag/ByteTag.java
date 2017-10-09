package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class ByteTag extends Tag<Byte>{
    /**
     * The value.
     */
    private final byte value;

    /**
     * Creates the tag.<br> Boolean true is stored as 1 and boolean false is stored as 0.
     *
     * @param name The name.
     * @param value The value.
     */
    public ByteTag(String name, boolean value) {
        this(name, (byte) (value ? 1 : 0));
    }

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public ByteTag(String name, byte value) {
        super(TagType.TAG_BYTE, name);
        this.value = value;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    public boolean getBooleanValue() {
        return value != 0;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Byte" + append + ": " + value;
    }

    public ByteTag clone() {
        return new ByteTag(getName(), value);
    }

    public static Boolean getBooleanValue(Tag<?> t) {
        if (t == null) {
            return null;
        }
        try {
            ByteTag byteTag = (ByteTag) t;
            return byteTag.getBooleanValue();
        } catch (ClassCastException e) {
            return null;
        }
    }
}
