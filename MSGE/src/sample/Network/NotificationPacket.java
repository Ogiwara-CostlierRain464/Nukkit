package sample.Network;

/**
 * Created by ogiwara on 2016/09/18.
 */
public class NotificationPacket extends Packet{

    public NotificationPacket(String notification){
        try {
            data = (Notification + notification).getBytes("UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
