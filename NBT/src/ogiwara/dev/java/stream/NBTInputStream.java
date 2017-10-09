package ogiwara.dev.java.stream;

import ogiwara.dev.java.NBTConstants;
import ogiwara.dev.java.tag.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class NBTInputStream implements Cloneable{
    /**
     * The data input stream.
     */
    private final EndianSwitchableInputStream is;

    /**
     * Creates a new {@link NBTInputStream}, which will source its data from the specified input stream. This assumes the stream is compressed.
     *
     * @param is The input stream.
     * @throws java.io.IOException if an I/O error occurs.
     */
    public NBTInputStream(InputStream is) throws IOException {
        this(is, true, ByteOrder.BIG_ENDIAN);
    }

    /**
     * Creates a new {@link NBTInputStream}, which sources its data from the specified input stream. A flag must be passed which indicates if the stream is compressed with GZIP or not. This assumes the
     * stream uses big endian encoding.
     *
     * @param is The input stream.
     * @param compressed A flag indicating if the stream is compressed.
     * @throws java.io.IOException if an I/O error occurs.
     */
    public NBTInputStream(InputStream is, boolean compressed) throws IOException {
        this(is, compressed, ByteOrder.BIG_ENDIAN);
    }

    /**
     * Creates a new {@link NBTInputStream}, which sources its data from the specified input stream. A flag must be passed which indicates if the stream is compressed with GZIP or not.
     *
     * @param is The input stream.
     * @param compressed A flag indicating if the stream is compressed.
     * @param endianness Whether to read numbers from the InputStream with little endian encoding.
     * @throws java.io.IOException if an I/O error occurs.
     */
    public NBTInputStream(InputStream is, boolean compressed, ByteOrder endianness) throws IOException {
        this.is = new EndianSwitchableInputStream(compressed ? new GZIPInputStream(is) : is, endianness);
    }

    /**
     * Reads an NBT {@link Tag} from the stream.
     *
     * @return The tag that was read.
     * @throws java.io.IOException if an I/O error occurs.
     */
    public Tag readTag() throws IOException {
        return readTag(0);
    }

    /**
     * Reads an NBT {@link Tag} from the stream.
     *
     * @param depth The depth of this tag.
     * @return The tag that was read.
     * @throws java.io.IOException if an I/O error occurs.
     */
    private Tag readTag(int depth) throws IOException {
        int typeId = is.readByte() & 0xFF;
        TagType type = TagType.getById(typeId);

        String name;
        if (type != TagType.TAG_END) {
            int nameLength = is.readShort() & 0xFFFF;
            byte[] nameBytes = new byte[nameLength];
            is.readFully(nameBytes);
            name = new String(nameBytes, NBTConstants.CHARSET.name());
        } else {
            Log("<END>");
            name = "";
        }

        return readTagPayload(type, name, depth);
    }

    /**
     * Reads the payload of a {@link Tag}, given the name and type.
     *
     * @param type The type.
     * @param name The name.
     * @param depth The depth.
     * @return The tag.
     * @throws java.io.IOException if an I/O error occurs.
     */
    @SuppressWarnings ({"unchecked", "rawtypes"})
    private Tag readTagPayload(TagType type, String name, int depth) throws IOException {
        switch (type) {
            case TAG_END:
                Log("<END/>");
                if (depth == 0) {
                    throw new IOException("TAG_End found without a TAG_Compound/TAG_List tag preceding it.");
                } else {
                    return new EndTag();
                }

            case TAG_BYTE:
                Log("<BYTE>");
                return new ByteTag(name, is.readByte());

            case TAG_SHORT:
                Log("<SHORT>");
                return new ShortTag(name, is.readShort());

            case TAG_INT:
                Log("<INT>");
                return new IntTag(name, is.readInt());

            case TAG_LONG:
                Log("<LONG>");
                return new LongTag(name, is.readLong());

            case TAG_FLOAT:
                Log("<FLOAT>");
                return new FloatTag(name, is.readFloat());

            case TAG_DOUBLE:
                Log("<DOUBLE>");
                return new DoubleTag(name, is.readDouble());

            case TAG_BYTE_ARRAY:
                Log("<BYTE-ARRAY>");
                int length = is.readInt();
                byte[] bytes = new byte[length];
                is.readFully(bytes);
                return new ByteArrayTag(name, bytes);

            case TAG_STRING:
                Log("<STRING>");
                length = is.readShort();
                bytes = new byte[length];
                is.readFully(bytes);
                return new StringTag(name, new String(bytes, NBTConstants.CHARSET.name()));

            case TAG_LIST:
                Log("<LIST>");
                TagType childType = TagType.getById(is.readByte());
                length = is.readInt();

                Class<? extends Tag> clazz = childType.getTagClass();
                List<Tag> tagList = new ArrayList<Tag>(length);
                for (int i = 0; i < length; i++) {
                    Tag tag = readTagPayload(childType, "", depth + 1);
                    if (tag instanceof EndTag) {
                        throw new IOException("TAG_End not permitted in a list.");
                    } else if (!clazz.isInstance(tag)) {
                        throw new IOException("Mixed tag types within a list.");
                    }
                    tagList.add(tag);
                }

                return new ListTag(name, clazz, tagList);

            case TAG_COMPOUND:
                Log("<COMPOUND>");
                CompoundMap compoundTagList = new CompoundMap();
                while (true) {
                    Tag tag = readTag(depth + 1);
                    if (tag instanceof EndTag) {
                        break;
                    } else {
                        compoundTagList.put(tag);
                    }
                }

                return new CompoundTag(name, compoundTagList);

            case TAG_INT_ARRAY:
                Log("<INT-ARRAY>");
                length = is.readInt();
                int[] ints = new int[length];
                for (int i = 0; i < length; i++) {
                    ints[i] = is.readInt();
                }
                return new IntArrayTag(name, ints);

            case TAG_SHORT_ARRAY:
                Log("<SHORT-ARRAY>");
                length = is.readInt();
                short[] shorts = new short[length];
                for (int i = 0; i < length; i++) {
                    shorts[i] = is.readShort();
                }
                return new ShortArrayTag(name, shorts);

            default:
                throw new IOException("Invalid tag type: " + type + ".");
        }
    }

    public void close() throws IOException {
        is.close();
    }

    public void Log(String log){
        System.out.print(log);
    }

    /**
     * @return whether this NBTInputStream reads numbers in little-endian format.
     */
    public ByteOrder getByteOrder() {
        return is.getEndianness();
    }
}
