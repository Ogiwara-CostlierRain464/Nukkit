package sample;

import sample.Network.NotificationPacket;
import sample.Network.Packet;
import sample.Network.UpdateConsolePacket;
import sample.utils.HexToString;
import sample.utils.Security;
import sample.utils.SettingFileManager;
import sample.utils.TextFormat;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by ogiwara on 2016/09/18.
 */
public class RemoteAccessThread extends Thread {

    public static Integer port;
    public static String password;

    //Flags
    static ArrayList<String> Logs = new ArrayList<String>();
    static ArrayList<String> notifications = new ArrayList<String>();

    static boolean running = true;

    DatagramSocket sendsocket;
    DatagramSocket recsocket;

    static RemoteAccessThread instance;

    public RemoteAccessThread(){
        SettingFileManager settingFileManager = new SettingFileManager();
        port = Integer.parseInt(settingFileManager.get("remoteview-port"));
        password = settingFileManager.get("remoteview-password");
        instance = this;
        try {
            sendsocket = new DatagramSocket();
            recsocket = new DatagramSocket(port);
        }catch (SocketException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        super.run();
        while (running){
            try{
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf,buf.length);
                recsocket.receive(packet);//ここでpacket.getDataでデータを取り出せる
                try{
                    byte[] decodedpacket = Security.decode(TextFormat.trim(packet.getData(),packet.getLength()),password);

                    AnalyzePacket(new String(decodedpacket,"UTF-8"),packet.getAddress().getHostAddress());
                }catch (Exception e){
                    e.printStackTrace();
                    //Password is wrong.
                    ConsoleWindowControll.getInstance().AddLogFromAnotherThread(packet.getAddress().toString() +"が遠隔操作を試みましたが、失敗しました");
                }
            }catch (Exception e){

            }
        }
    }

    void AnalyzePacket(String text,String ip){


        switch (text.substring(0,2)){
            case Packet.CheckUpdate:

                for(String log : getLogs()){
                    log = TextFormat.makeLogforSmallScreen(log);
                    UpdateConsolePacket uc = new UpdateConsolePacket(log);
                    SendPacket(uc,ip);
                }

                Logs.clear();

                for(String notifi : getNotifications()){
                    NotificationPacket np = new NotificationPacket(notifi);
                    SendPacket(np,ip);
                }

                notifications.clear();

                break;

            case Packet.CommandExecute:
                //Not supporting now.
                break;

            default:
                ConsoleWindowControll.getInstance().AddLogFromAnotherThread("サポートしてないパケット:" + text.substring(0,2));
                break;
        }
    }

    static ArrayList<String> getLogs(){
        ArrayList<String> result = Logs;
       // Logs.clear();
        return result;
    }

    static ArrayList<String> getNotifications(){
        ArrayList<String> result = notifications;
        //notifications.clear();
        return result;
    }

    public static void AddLogStack(String log){
        if(Logs.size() == 20){
           // Logs.clear();
        }

        Logs.add(log);
    }

    public static void AddNotificationStack(String notification){

        if(notifications.size() > 5){
            //notifications.clear();
        }

        notifications.add(notification);
    }

    public static RemoteAccessThread getInstance(){
        return instance;
    }

    public void kill(){
        running = false;
        recsocket.close();
        sendsocket.close();
    }

    public void SendPacket(Packet packet,String ip){
        try{
            InetAddress inet = InetAddress.getByName(ip);
            byte[] encoded = Security.encode(packet.getData(), RemoteAccessThread.password);

            DatagramPacket dp = new DatagramPacket(encoded, encoded.length, inet, 55555);//TODO Change port of smartphone.

            sendsocket.send(dp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
