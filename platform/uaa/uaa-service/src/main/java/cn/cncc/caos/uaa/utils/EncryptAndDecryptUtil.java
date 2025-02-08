package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.utils.AESUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import org.apache.commons.lang.StringUtils;

public class EncryptAndDecryptUtil {

  private static final String KEY = "base_user_key";

  public static void encryptBaseUser(BaseUser baseUser){
    String phone = baseUser.getPhone();
    if (StringUtils.isNotEmpty(phone)) {
      phone = AESUtil.encrypt(phone, KEY);
      baseUser.setPhone(phone);
    }
  }

  public static void decryptBaseUser(BaseUser baseUser){
    String phone = baseUser.getPhone();
    if (StringUtils.isNotEmpty(phone)) {
      phone = AESUtil.decrypt(phone, KEY);
      baseUser.setPhone(phone);
    }
  }

  public static String decryptString(String str){
    String retStr = "";
    if (StringUtils.isNotEmpty(str)) {
      retStr = AESUtil.decrypt(str, KEY);
    }
    return retStr;
  }

}
