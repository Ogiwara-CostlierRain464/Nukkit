package sample.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ogiwara on 2016/08/30.
 */
public class DirectoryChecker {

    public final static String NUKKIT = "NUKKIT";

    public final static String POCKETMINE = "POCKETMINE";

    public static boolean CheckDirectory(){
        if(getsrc() != null && getruntime() != null){
            return true;
        }else{
            return false;
        }
    }

    public static ArrayList<String> getArgAndPlatform(){
        ArrayList<String> result = new ArrayList<String>();
        String runtime = getruntime();
        String platform;
        if(runtime == "java"){
            platform = NUKKIT;
        }else{
            platform = POCKETMINE;
    }
        String args = MakeCommand(runtime,getsrc(),platform);
        result.add(args);
        result.add(platform);
        return result;
    }

    private static String MakeCommand(String runtime,String startfile,String platform){
        String command;
        if(platform == NUKKIT){
            command = runtime + " " + "-Djline.terminal=jline.UnsupportedTerminal -jar " + startfile;
            return command;
        }else{//TODO: Support Linux
            String os = System.getProperty("os.name").toLowerCase();
            if(os.startsWith("windows")) {
                command = runtime + " -c bin" + File.separator + "php " + startfile + " %*";
                return command;
            }else{
                command = runtime + " " + startfile;
                return command;
            }
        }
    }

    public static void copyFile(File in, File out) throws Exception{
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        try{
            byte[] buf = new byte[1024];
            int i = 0;
            while((i = fis.read(buf)) != -1){
                fos.write(buf,0,i);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(fis != null) fis.close();
            if(fos != null) fos.close();
        }
    }

    public static String getsrc(){
        File currentdir = new File(System.getProperty("user.dir"));
        for(File src : currentdir.listFiles()){
            if(src.getName().endsWith("jar") && src.getName().toLowerCase().startsWith("nukkit")){
                return src.getName();
            }

            if(src.getName().endsWith("phar")){
                return src.getName();
            }

            if(src.getName().contains("src")){
                return getSrcStartFile(src);
            }
        }

        return null;
    }

    public static String getruntime(){
        File currentdir = new File(System.getProperty("user.dir"));
        for (File bin : currentdir.listFiles()){
            if(bin.getName().startsWith("nukkit") && bin.getName().endsWith("jar")){
                return "java";
            }

            if(bin.getName().contains("bin")){
                return getphpbinary(bin);
            }
        }
        return null;
    }

    private static String getSrcStartFile(File src){
        return src.getPath() + File.separator + "pocketmine"+ File.separator +"PocketMine.php";
    }

    private static String getphpbinary(File bin){

        String os = System.getProperty("os.name").toLowerCase();

        if(os.startsWith("windows")){//win I suppose it is only support php7?
            File winphp = new File(bin.getPath() + File.separator + "php" + File.separator + "php.exe");
            if(winphp.exists()){
                return winphp.getPath();
            }else{
                return null;
            }
        }else{//linux, mac
            File linuxphp = new File("./bin" + File.separator + "php5" + File.separator + "bin" + File.separator + "php");//php5
            if(linuxphp.exists()){
                return linuxphp.getPath();
            }else{
                File linuxphp7 = new File("./bin" + File.separator + "php7" + File.separator + "bin" + File.separator + "php");
                if(linuxphp7.exists()){
                    return linuxphp7.getPath();
                }else{
                    return null;
                }
            }
        }
    }
}
