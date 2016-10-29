package sample.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

/**
 * Created by ogiwara on 2016/08/31.
 */
public class SettingFileManager {

    public Properties settingfile;

    public static void CreateDefaultSettingFile(){
        try{
            File file = new File("MSGE.properties");
            if(!file.exists()){
                file.createNewFile();//なかったら作成
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

                pw.println("ban=ban");
                pw.println("playerlock=playerlock");
                pw.println("remoteview-port=34567");
                String rand = getRandomString(16);
                pw.println("remoteview-password=" + rand);
                pw.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getRandomString(int cnt) {
        final String chars ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd=new Random();
        StringBuffer buf = new StringBuffer();
        for(int i=0;i<cnt;i++){
            int val=rnd.nextInt(chars.length());
            buf.append(chars.charAt(val));
        }
        return buf.toString();
    }


    public SettingFileManager(){

        try{
            settingfile = new Properties();
            InputStream inputstream = new FileInputStream("MSGE.properties");
            settingfile.load(inputstream);
            inputstream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String get(String key){
        return settingfile.getProperty(key);
    }

    public void set(String key, String value){
        settingfile.setProperty(key,value);
    }

    public boolean save(String comment){
        try {
            settingfile.store(new FileOutputStream("MSGE.properties"), comment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
