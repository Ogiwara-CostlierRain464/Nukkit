package sample.utils;

/**
 * Created by ogiwara on 2016/09/22.
 */
public class HexToString {

    public static String encode(byte[] byteArray){

        StringBuffer sb = new StringBuffer();

        int i;
        for ( byte b : byteArray ){

            // バイトを自然数に変換... するときは注意が必要
            // もし b が負数の場合を考慮して、0xFFとの論理積をとり下位8bitの値だけをみるように
            i = 0xFF & (int)b;

            // これを、16進数で表現
            String str = Integer.toHexString( i );

            // バッファに追加
            sb.append(str);
        }

        return sb.toString();
    }

}
