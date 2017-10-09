package ogiwara.dev.java.tag;

import java.util.Arrays;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class ByteArrayTag extends Tag<byte[]>{
    /**
     * The value.
     */
    private final byte[] value;

    /**
     * Creates the tag.
     *
     * @param name The name.
     * @param value The value.
     */
    public ByteArrayTag(String name, byte[] value) {
        super(TagType.TAG_BYTE_ARRAY, name);
        this.value = value;
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder hex = new StringBuilder();
        for (byte b : value) {
            String hexDigits = Integer.toHexString(b).toUpperCase();
            if (hexDigits.length() == 1) {
                hex.append("0");
            }
            hex.append(hexDigits).append(" ");
        }

        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_Byte_Array" + append + ": " + hex.toString();
    }

    public ByteArrayTag clone() {
        byte[] clonedArray = cloneArray(value);

        return new ByteArrayTag(getName(), clonedArray);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ByteArrayTag)) {
            return false;
        }

        ByteArrayTag tag = (ByteArrayTag) other;
        return Arrays.equals(value, tag.value) && getName().equals(tag.getName());
    }

    private byte[] cloneArray(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        } else {
            int length = byteArray.length;
            byte[] newArray = new byte[length];
            System.arraycopy(byteArray, 0, newArray, 0, length);
            return newArray;
        }
    }
}
