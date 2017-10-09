package ogiwara.dev.java;

import ogiwara.dev.java.tag.TagType;

import java.nio.charset.Charset;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class NBTConstants {
    /**
     * The character set used by NBT (UTF-8).
     */
    public static final Charset CHARSET = Charset.forName("UTF-8");
    /**
     * Tag type constants.
     */
    @Deprecated
    public static final int TYPE_END = TagType.TAG_END.getId(),
            TYPE_BYTE = TagType.TAG_BYTE.getId(),
            TYPE_SHORT = TagType.TAG_SHORT.getId(),
            TYPE_INT = TagType.TAG_INT.getId(),
            TYPE_LONG = TagType.TAG_LONG.getId(),
            TYPE_FLOAT = TagType.TAG_FLOAT.getId(),
            TYPE_DOUBLE = TagType.TAG_DOUBLE.getId(),
            TYPE_BYTE_ARRAY = TagType.TAG_BYTE_ARRAY.getId(),
            TYPE_STRING = TagType.TAG_STRING.getId(),
            TYPE_LIST = TagType.TAG_LIST.getId(),
            TYPE_COMPOUND = TagType.TAG_COMPOUND.getId(),
            TYPE_INT_ARRAY = TagType.TAG_INT_ARRAY.getId(),
            TYPE_SHORT_ARRAY = TagType.TAG_SHORT_ARRAY.getId();

    /**
     * Default private constructor.
     */
    private NBTConstants(){

    }
}
