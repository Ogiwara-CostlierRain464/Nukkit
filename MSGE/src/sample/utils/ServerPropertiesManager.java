package sample.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by ogiwara on 2016/09/01.
 */
public class ServerPropertiesManager {

    public Properties properties;

    public ServerPropertiesManager(){

        try{
            properties = new Properties();
            InputStream inputstream = new FileInputStream("server.properties");
            properties.load(inputstream);
            inputstream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String get(String key){
        return properties.getProperty(key);
    }

    public void set(String key, String value){
        properties.setProperty(key,value);
    }

    public ArrayList<String> getKeys(){
       ArrayList<String> list = new ArrayList<String>();
        for(String key : properties.stringPropertyNames()){
            list.add(key);
        }

        return list;
    }

    public boolean save(String comment){
        try {
            properties.store(new FileOutputStream("server.properties"), comment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
