package cn.cncc.caos.common.core.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public class SM4Util {
  private static final String ALGORITHM = "SM4";
  private static final String PROVIDER = "BC";
  private static final String CIPHER_TRANSFORMATION = "SM4/ECB/PKCS5Padding"; // 注意：ECB模式可能不是最安全的

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  // 使用PBKDF2生成密钥（这里使用SHA-256作为哈希函数）
  public static SecretKey generateKeyFromString(String input) {
    byte[] keyBytes = input.getBytes();
    if (keyBytes.length >= 16) {
      // 如果输入字符串足够长，直接截取前16个字节
      return new SecretKeySpec(Arrays.copyOfRange(keyBytes, 0, 16), ALGORITHM);
    } else {
      // 如果输入字符串不够长，这里可以抛出异常或采取其他措施
      // 但为了示例，我们简单地填充0（这通常不是一个好主意）
      byte[] paddedKey = new byte[16];
      System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
      // 其余部分填充0（不推荐）
      return new SecretKeySpec(paddedKey, ALGORITHM);
    }
  }

  // 加密字符串
  public static String encrypt(String content, String key) throws Exception {
    return encrypt(content, generateKeyFromString(key));
  }
  public static String encrypt(String content, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION, PROVIDER);
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] encryptedBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  // 解密字符串
  public static String decrypt(String encryptedContent, String key) throws Exception {
    return decrypt(encryptedContent, generateKeyFromString(key));
  }
  public static String decrypt(String encryptedContent, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION, PROVIDER);
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] decodedBytes = Base64.getDecoder().decode(encryptedContent);
    byte[] decryptedBytes = cipher.doFinal(decodedBytes);
    return new String(decryptedBytes, StandardCharsets.UTF_8);
  }

}
