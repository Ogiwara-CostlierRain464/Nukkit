package ogiwara.dev.java.stream;

import java.io.*;
import java.nio.ByteOrder;

/**
 * Created by ogiwara on 2016/12/25.
 */
public class EndianSwitchableOutputStream extends FilterOutputStream implements DataOutput{
    private final ByteOrder endianness;

    public EndianSwitchableOutputStream(OutputStream backingStream, ByteOrder endianness) {
        super(backingStream instanceof DataOutputStream ? (DataOutputStream) backingStream : new DataOutputStream(backingStream));
        this.endianness = endianness;
    }

    public ByteOrder getEndianness() {
        return endianness;
    }

    protected DataOutputStream getBackingStream() {
        return (DataOutputStream) super.out;
    }

    public void writeBoolean(boolean b) throws IOException {
        getBackingStream().writeBoolean(b);
    }

    public void writeByte(int i) throws IOException {
        getBackingStream().writeByte(i);
    }

    public void writeShort(int i) throws IOException {
        if (endianness == ByteOrder.LITTLE_ENDIAN) {
            i = Integer.reverseBytes(i) >> 16;
        }
        getBackingStream().writeShort(i);
    }

    public void writeChar(int i) throws IOException {
        if (endianness == ByteOrder.LITTLE_ENDIAN) {
            i = Character.reverseBytes((char) i);
        }
        getBackingStream().writeChar(i);
    }

    public void writeInt(int i) throws IOException {
        if (endianness == ByteOrder.LITTLE_ENDIAN) {
            i = Integer.reverseBytes(i);
        }
        getBackingStream().writeInt(i);
    }

    public void writeLong(long l) throws IOException {
        if (endianness == ByteOrder.LITTLE_ENDIAN) {
            l = Long.reverseBytes(l);
        }
        getBackingStream().writeLong(l);
    }

    public void writeFloat(float v) throws IOException {
        int intBits = Float.floatToIntBits(v);
        if (endianness == ByteOrder.LITTLE_ENDIAN) {
            intBits = Integer.reverseBytes(intBits);
        }
        getBackingStream().writeInt(intBits);
    }

    public void writeDouble(double v) throws IOException {
        long longBits = Double.doubleToLongBits(v);
        if (endianness == ByteOrder.LITTLE_ENDIAN) {
            longBits = Long.reverseBytes(longBits);
        }
        getBackingStream().writeLong(longBits);
    }

    public void writeBytes(String s) throws IOException {
        getBackingStream().writeBytes(s);
    }

    public void writeChars(String s) throws IOException {
        getBackingStream().writeChars(s);
    }

    public void writeUTF(String s) throws IOException {
        getBackingStream().writeUTF(s);
    }
}
