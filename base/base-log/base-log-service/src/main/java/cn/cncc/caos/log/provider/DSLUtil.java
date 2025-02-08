package cn.cncc.caos.log.provider;


import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.*;
public class DSLUtil {
    public static String velocity(String template, Map<String, Object> data) {

        // VelocityEngine处理
        // 创建对象
        VelocityEngine ve = new VelocityEngine();
        // 设置变量
        VelocityContext ctx = new VelocityContext();
        Set<Map.Entry<String, Object>> entrySet = data.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(StringUtils.isNotEmpty(String.valueOf(value))){
                ctx.put(key, value);
            }

        }
        StringWriter sw = new StringWriter();
        String logTag = new String();
        boolean eval = ve.evaluate(ctx, sw, logTag, template);
        String dsl = sw.toString();
        return dsl;

    }
    public static Long gethitsToTatal(Map<String, Object> hits) {
        Object num = hits.get("total");
        Long total = Long.valueOf(String.valueOf(num));
        return total;
    }
    public static List<Map<String, String>> gethitsToList(Map<String, Object> hits) {

        List<Map<String, String>> res = new ArrayList<>();

        List<Map<String,Object>> list = (List<Map<String, Object>>) hits.get("hits");
        for (Map<String, Object> stringObjectMap : list) {
            String index = String.valueOf(stringObjectMap.get("_id"));
            Map<String, String> source = (Map<String, String>) stringObjectMap.get("_source");
            source.put("id",index);
            //有高亮色时重新定义logdata值
            if(stringObjectMap.get("highlight") != null){
                Map<String,List<String>> highlight = (Map<String, List<String>>) stringObjectMap.get("highlight");
                List<String> logdata = highlight.get("logdata");
                String em = logdata.get(0);
                String replace = em.replace("<em>", "<em style=\"color:red\">");
//                source.put("logdata",logdata.get(0));
                source.put("logdata",replace);
            }
            res.add(source);
        }

        return res;
    }
}
