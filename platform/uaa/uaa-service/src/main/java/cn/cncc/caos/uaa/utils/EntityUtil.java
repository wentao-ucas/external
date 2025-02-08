package cn.cncc.caos.uaa.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EntityUtil {

    public static <T> List<Map<String, Object>> listEntityToListMap(List<T> list) {
        List<Map<String, Object>> res = new ArrayList<>();
        list.forEach(object-> res.add(entityToMap(object)));
        return res;
    }

    public static HashMap<String, Object> entityToMap(Object object) {
        HashMap<String, Object> map = new HashMap();
        for (Field field : object.getClass().getDeclaredFields()){
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return map;
    }

    /**
     * 不考虑从祖先类继承的属性，只获取当前类属性，包括四类访问权限，private，protect，default，public
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private static String getFieldValueByFieldName(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return  field.get(object).toString();
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    private static Integer getFieldValueIntegerByFieldName(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return  Integer.parseInt(field.get(object).toString());
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public static Map<String,List<Map<String,Object>>> getHashMapOfListUseKeyFieldNameByHashMapList(List<Map<String,Object>> list, String fieldName) {
        Map<String,List<Map<String,Object>>> result = new HashMap<>();
        for(Map<String,Object> object: list) {
            String key = object.get(fieldName).toString();
            if(!result.containsKey(key))
                result.put(key, new ArrayList<>());
            result.get(key).add(object);
        }
        return result;
    }

    public static Map<String,Map<String,Object>> getHashMapUseKeyFieldNameByHashMapList(List<Map<String,Object>> list, String fieldName) {
        Map<String,Map<String,Object>> result = new HashMap<>();
        for(Map<String,Object> object: list) {
            String key = object.get(fieldName).toString();
            result.put(key, object);
        }
        return result;
    }

    public static <T> HashMap<String,List<T>> getHashMapOfListUseKeyFieldNameByObjectList(List<T> list, String fieldName) {
        HashMap<String,List<T>> result = new HashMap<>();
        for(T object: list) {
            String key = getFieldValueByFieldName(object,fieldName);
            if(!result.containsKey(key))
                result.put(key, new ArrayList<>());
            result.get(key).add(object);
        }
        return result;
    }

    public static <T> HashMap<Integer,List<T>> getHashMapOfListUseKeyFieldNameIntegerByObjectList(List<T> list, String fieldName) {
        HashMap<Integer,List<T>> result = new HashMap<>();
        for(T object: list) {
            Integer key = getFieldValueIntegerByFieldName(object,fieldName);
            if(!result.containsKey(key))
                result.put(key, new ArrayList<>());
            result.get(key).add(object);
        }
        return result;
    }

    public static <T> HashMap<String,List<T>> getHashMapOfListUseKeyFieldsNameByObjectList(List<T> list, String fieldName,String fieldName2) {
        HashMap<String,List<T>> result = new HashMap<>();
        for(T object: list) {
            String key1 = getFieldValueByFieldName(object,fieldName);
            String fieldValueByFieldName = getFieldValueByFieldName(object, fieldName2);
            String key = key1+fieldValueByFieldName;
            if(!result.containsKey(key))
                result.put(key, new ArrayList<>());
            result.get(key).add(object);
        }
        return result;
    }

    public static <T> HashMap<String,T> getHashMapUseKeyFieldNameObjByObjectList(List<T> list, String ... filedNames) {
        HashMap<String,T> result = new HashMap<>();
        for(T object: list) {
            String key = "";
            for (String filedName : filedNames) {
                String fieldValueByFieldName = getFieldValueByFieldName(object, filedName);
                key += fieldValueByFieldName;
            }
            result.put(key, object);
        }
        return result;
    }


    public static <T> HashMap<String,T> getHashMapUseKeyFieldNameByObjectList(List<T> list, String fieldName) {
        HashMap<String,T> result = new HashMap<>();
        for(T object: list) {
            String key = getFieldValueByFieldName(object,fieldName);
            result.put(key, object);
        }
        return result;
    }

    public static <T> HashMap<String,String> getHashMapUseKeyFieldNameByObjectList(List<T> list, String fieldName, String fieldName2) {
        HashMap<String,String> result = new HashMap<>();
        for(T object: list) {
            String key = getFieldValueByFieldName(object,fieldName);
            String value = getFieldValueByFieldName(object,fieldName2);
            result.put(key, value);
        }
        return result;
    }

    public static void setEntityNullValueToDefault(Object model){
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString(); // 获取属性的类型
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(model); // 调用getter方法获取属性值
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,String.class);
                        m.invoke(model, "");
                    }
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,Integer.class);
                        m.invoke(model, 0);
                    }
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,Boolean.class);
                        m.invoke(model, false);
                    }
                }
//                if (type.equals("class java.util.Date")) {
//                    Method m = model.getClass().getMethod("get" + name);
//                    Date value = (Date) m.invoke(model);
//                    if (value == null) {
//                        m = model.getClass().getMethod("set"+name,Date.class);
//                        m.invoke(model, new Date());
//                    }
//                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (NoSuchMethodException e) {
            log.error("", e);
        } catch (SecurityException e) {
            log.error("", e);
        } catch (IllegalAccessException e) {
            log.error("", e);
        } catch (IllegalArgumentException e) {
            log.error("", e);
        } catch (InvocationTargetException e) {
            log.error("", e);
        }
    }
}
