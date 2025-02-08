package cn.cncc.caos.common.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @className: IDUtil
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/13 12:39
 */
public class IDUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    public static  String getUUIdFormatAllNumber(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    public static String getStringNextId(String prefix){
        return prefix + StringUtil.getNextSequence();
    }

    public static String buildUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        return String.format("%s-%s-%s-%s-%s",
                uuidString.substring(0, 8),
                uuidString.substring(8, 12),
                uuidString.substring(12, 16),
                uuidString.substring(16, 20),
                uuidString.substring(20));
    }

    private static String getYmdHmsS() {
        return dateFormat.format(new Date());
    }

    public static String getSimpleID() {
        return String.format("%14s",getYmdHmsS());
    }
}
