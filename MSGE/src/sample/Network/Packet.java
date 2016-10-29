package sample.Network;

import sample.ConsoleWindowControll;
import sample.RemoteAccessThread;
import sample.utils.Security;

import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.Key;

/**
 * Created by ogiwara on 2016/09/18.
 */
public class Packet {

    public static final String CheckUpdate = "CU";

    public static final String CommandExecute = "CE";

    public static final String UpdateConsole = "UC";

    public static final String Notification = "NO";

    protected byte[] data;

    public byte[] getData(){
        return data;
    }
}

