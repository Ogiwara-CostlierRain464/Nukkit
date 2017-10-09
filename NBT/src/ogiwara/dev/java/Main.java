package ogiwara.dev.java;

import ogiwara.dev.java.stream.NBTInputStream;
import ogiwara.dev.java.tag.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

public class Main {
    public static void main(String[] args) {
	    System.out.println("Welcome to NBTViewer");
        if (args.length < 1) {
            System.err.println("No files provided! Usage: <nbt file> [compressed] [byteorder]");
            System.exit(1);
        }

        File argFile = new File(args[0]);
        boolean compressed = args.length >= 2 ? Boolean.valueOf(args[1]): true;
        ByteOrder order = args.length >= 3 ? getByteOrder(args[2]) : ByteOrder.BIG_ENDIAN;

        NBTInputStream input;
        try{
            input = new NBTInputStream(new FileInputStream(argFile),compressed,order);
        }catch (IOException e){
            System.err.println("Error opening NBT file: " + e);
            e.printStackTrace();
            System.exit(1);
            return;
        }

        try {
            Tag tag = input.readTag();
            System.out.println("NBT data from file: " + argFile.getCanonicalPath());
            System.out.println(tag);
        } catch (IOException e) {
            System.err.println("Error reading tag from file: " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static ByteOrder getByteOrder(String name) {
        if (name.equalsIgnoreCase("big_endian") || name.equalsIgnoreCase("bigendian") || name.equalsIgnoreCase("be")) {
            return ByteOrder.BIG_ENDIAN;
        } else if (name.equalsIgnoreCase("little_endian") || name.equalsIgnoreCase("littleendian") || name.equalsIgnoreCase("le")) {
            return ByteOrder.LITTLE_ENDIAN;
        } else {
            throw new IllegalArgumentException("Unknown ByteOrder: " + name);
        }
    }
}
