package sample.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by ogiwara on 2016/09/18.
 */
public class Security {

    static Key makeKey(String Password){
        byte[] key = new byte[ 128/8 ];
        byte[] pass = Password.getBytes();

        for(int i = 0; i < key.length; i++){
            if(i < pass.length){
                key[i] = pass[i];
            }else{
                key[i] = 0;
            }
        }

        return new SecretKeySpec(key, "AES");
    }

    static IvParameterSpec makeIV(String Password){

        byte[] key = new byte[ 128/8 ];
        byte[] pass = Password.getBytes();

        for(int i = 0; i < key.length; i++){
            if(i < pass.length){
                key[i] = pass[i];
            }else{
                key[i] = 0;
            }
        }

        return new IvParameterSpec(key);
    }

    //暗号化
    public static byte[] encode(byte[] src,String Password){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, makeKey(Password),makeIV(Password));
            return cipher.doFinal(src);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //複合化
    public static byte[] decode(byte[] src,String Password){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, makeKey(Password),makeIV(Password));
            return cipher.doFinal(src);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
