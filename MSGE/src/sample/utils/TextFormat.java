package sample.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ogiwara on 2016/09/02.
 */
public class TextFormat {

    public final static String ADD_PLAYER_TAG = "<AP>";

    public final static String DELETE_PLAYER_TAG = "<DP>";

    public final static String SERVER_STOP_TAG = "<SS>";

    public final static String NOTIFICATION_TAG = "<NO>";

    public static String getEncode(String platform){//TODO: Check Encode (case php, linux)
        String os = System.getProperty("os.name").toLowerCase();

        if(platform == DirectoryChecker.NUKKIT){
            if(os.startsWith("windows")) {
                return "UTF-8";//OK
            }else{
                return "UTF-8";//OK!
            }
        }else{
            if(os.startsWith("windows")) {
                return "UTF-8";//OK
            }else{
                return "UTF-8";
            }
        }
    }

    public static String getContents(String code){
        String regex = "(<GUI>)(?<carName>.+?)(</GUI>)";
        return extractMatchString(regex,code);
    }

    private static String extractMatchString(String regex, String target) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return matcher.group("carName");
        } else {
            throw new IllegalStateException("No match found.");
        }
    }

    public static String getTaggedContents(String code,String tag1, String tag2){
        String regex = "(" + tag1 + ")(?<carName>.+?)("+ tag2 +")";
        return extractMatchString(regex,code);
    }

    public static boolean hasGUITag(String log){
        if(log.contains("<GUI>") && log.contains("</GUI>")){
            return true;
        }else{
            return false;
        }
    }

    public static byte[] trim(byte[] bytes, int lenght){
        byte[] result = new byte[lenght];
        int i = 0;
        for(byte c : bytes){

            result[i] = c;
            i++;

            if(i == lenght){
                break;
            }
        }
        return result;
    }

    public static String makeLogforSmallScreen(String longlog){


        /*while (longlog.contains("[") && longlog.contains("]")){
            longlog = longlog.replace("[" +getTaggedContents(longlog,"[","]") + "]","");
        }*/

        return longlog;
    }

}
