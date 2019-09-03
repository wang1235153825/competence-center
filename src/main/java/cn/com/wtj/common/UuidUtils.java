package cn.com.wtj.common;

import java.util.UUID;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public class UuidUtils {

    private static final String DEFAULT_SEP_CHAR = "-";

    private static final String DEFAULT_REPLACE_CHAR = "";

    public static String createUUID(){
        return createUUID(false);
    }

    public static String createUUID(boolean boo){
        String uuid = UUID.randomUUID().toString();
        if (boo){
            return uuid.replace(DEFAULT_SEP_CHAR,DEFAULT_REPLACE_CHAR);
        }
        return uuid;
    }


}
