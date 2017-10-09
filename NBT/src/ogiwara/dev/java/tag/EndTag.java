package ogiwara.dev.java.tag;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class EndTag extends Tag<Object>{
    /**
     * Creates the tag.
     */
    public EndTag() {
        super(TagType.TAG_END);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "TAG_End";
    }

    public EndTag clone() {
        return new EndTag();
    }
}
