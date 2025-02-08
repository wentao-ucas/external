package cn.cncc.caos.uaa.utils;

public class EnvironmentTypeUtil {


  public static String getIdPre(String jwEnv){
    if (jwEnv.contains("office")){
      return "Y";
    }else {
      return "X";
    }
  }

}
