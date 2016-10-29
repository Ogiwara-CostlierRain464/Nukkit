package sample.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ogiwara on 2016/08/30.
 */
public class PluginListLoader {

    public static ArrayList<String> getPluginList(String StartDirectoryPath){

        ArrayList<String> pluginlist = new ArrayList<String>();

        File plugindir = new File(StartDirectoryPath + File.separator +"plugins");

        for(File plugin : plugindir.listFiles()){

            if(plugin.isFile()){
                //CheckName
                if(isPlugin(plugin)) pluginlist.add(plugin.getName().replace(".phar","").replace(".jar",""));
            }

            if(plugin.isDirectory()){
                //CheckSrc
                if(isContainsrc(plugin)) pluginlist.add(plugin.getName());
            }
        }

        return pluginlist;
    }

    public static boolean isPlugin(File file){

        boolean result = false;

        if(file.getName().endsWith("jar")){
            result = true;
        }

        if(file.getName().endsWith("phar")){
            result = true;
        }

        return result;
    }

    public static boolean isContainsrc(File dir){

        boolean result = false;

        for (String name : dir.list()) {
            if (name.contains("src")) {
                result = true;
            }
        }

        return result;
    }

}
