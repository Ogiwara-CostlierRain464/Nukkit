package sample.Network;

/**
 * Created by ogiwara on 2016/09/18.
 */
public class UpdateConsolePacket extends Packet{

    public UpdateConsolePacket(String log){
        try {
            data = (UpdateConsole + log).getBytes("UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
