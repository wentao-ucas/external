package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.utils.StringUtil;

import java.util.UUID;

public class IDUtil {
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
}
