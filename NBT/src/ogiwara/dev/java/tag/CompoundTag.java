package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class CompoundTag extends Tag<CompoundMap>{
    /**
     * The value.
     */
    private final CompoundMap value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public CompoundTag(String name, CompoundMap value) {
        super(TagType.TAG_COMPOUND, name);
        //this.value = (CompoundMap) Collections.unmodifiableMap(value); This doesn't work anymore, needs a new solution
        this.value = value;
    }

    @Override
    public CompoundMap getValue() {
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
        bldr.append("TAG_Compound").append(append).append(": ").append(value.size()).append(" entries\r\n{\r\n");
        for (Tag entry : value.values()) {
            bldr.append("   ").append(entry.toString().replaceAll("\r\n", "\r\n   ")).append("\r\n");
        }
        bldr.append("}");
        return bldr.toString();
    }

    public CompoundTag clone() {
        CompoundMap map = new CompoundMap(value);
        return new CompoundTag(getName(), map);
    }
}
