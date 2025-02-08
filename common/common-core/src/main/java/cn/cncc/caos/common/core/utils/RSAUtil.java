package cn.cncc.caos.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public class RSAUtil {
  private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqdX9NQ9vBLMDYMO6mDRMbt1T80jARjkq2dTaTcGNNVsu0cRZoLsu6FEinGbEJiZKeaWRDYuoGMsBqgJTmAMz7P/PkeSWoYp6Bwqej44UCnnFaluGHQdgWorA/EUgjQ+oIx0t/NBT7q378RiNm2aFSQPlkZae/dm6Y9WV2s4024b8QA2TRmAEOW5ZuZed+SrQYvQ42xNLZ7MrJgQ9RyvsHP6HoyTEcdPV4nLjwnkAwwFWC/iPgyAExp7EDfyb/N+wgCsqgCCxjxFBhfesi24H3tP1gR9aKkb1EHLbNagRTkRDuLorB9oHaKOLHd0T9uctRism5T+biYmGUNb3kmSRRwIDAQAB";

  private static String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC5fOSsocLXzgBVS/7c4DZo3ROn9XFwA9isn/YCNR7Lr0sKENjUPIeNzmGO5V4i3LzOp3iO89gfqoXWxY6ydasvwhYDNTfHvDJFOS+6w/yd/YR6GwzMexszhUDCNnpJ9EKbHxam3xZBlaWDJfud4L1uF8rdRXY3/qjxd7csGnmCST8Qqr2OqCsdsarElZQtgckQHonBvYJesWAJeriGK+tGt0H/p93iWuM8WQYcmusOGcEMSVTzREcDfC8jNZ61YfMohqd7TJEYesBVc418Je/EZfIB3FhvausaipwaZcKpeX3lsq9NOXDsSH9qKwHTRSyJinXl3JZ98Ib4pZpCPEGfAgMBAAECggEAJqCrvoexW9+6kgjReo32xr/DaCblCL7EUK6/PJZzY1eN+PqpLQmt8QoZpvh9MiVl7GNT5/FAANgyJxfdL6zxiXCRxPlANIgsr93I4swNv5kcDZG0iKDaeVA2vDs8HEqR1FwVC4tJxz6yyS0PyUEUMekihhIBHIgcBq03qCebRlY/2KtMTpFdRdW579etelmv8gW6ZS9pS958YGwojyOG1GnVp/SN4bSVzpEwPLV12wTtmW30Sa/XGFC+lmVVbdkCIvoF+dLs1nVZj5G/7eoiygp/9sUAo4r9HHKjxUOrJBMYppzkCKNcCxOoEIAJooZkqFrkHQGAD02Xq/uZg05LgQKBgQDbbCCxX9KxrzJraehXRbPWmIlzhc/CaOxX56FLIzrxntZiwDCb1Fa2x9JsIOokxmFzr7nx0mHumbp69VDbJw/NREoXFL960bTSDonO8uwRyCmT7RSaAbyfhkzauZrbSKgsF5FtYKgvet7CcKpQvFaXGj3Z9wp4XlXVdAGaTcVjfQKBgQDYaJtcbeb1i41bdfUEJTGxbBJNjROX1TYFc58JPEyy+rLD51cJNxYKLsRytQ5c7hUtR6KjNdCfsCMcyUfhggue6gLGDKYsAk4vApMFEeQFOOkMIBdvoy8Ig3B4mfm26gEZgEusqYCpUBsMbX8ARb/uSv5YQiXnyYPA8dJqN4tMSwKBgQCa7NiUhITeQkZGQyvyWtuknGUIdSWBLdYaJwjiZYibyzux0+M+U4m6WpI/GGpZ9twd86hFjvboohBh+s3RPt5QX3Q0ocik7mZOnkc9IJX/5CU/NfkC2FEXDZs8ICqvw7hCEY3z/Xgitg2MmkIW6TP2UmfzexMgc/ME2Hi9O0YwfQKBgQCzkGDRGdQZDBlXuZF7Rg1spTqupI+MXXp8nYpplGIj5EburIcYe1/LRTD16OupClgCAT2e5SldRz5rRUxifpB75Lo2cQp029jZwu4Qt96WwrkEuaAYnrOJAd8SAtkkABi3ey/Pbbn3QyOedUB7DmUKKanPu9tN73RmZYDtZ3cq7wKBgQCxLApHCmEeQVidox26CGhZetyNXfE93zfdSx/bFrfMnlEe74vXklensXwvjJUEWjsad7ZEg9omeJnEDNXyThOlI4HgIlTjKF/AgJb/fkqNRiUapQ4eaf/3+Jde5Hv5+mkOyXnnRDu4AHB1fZ/rJ015aQR1lAPqs5JHCQByf8b9Mg==";


  /**
   * 该方法 可生成公钥与私钥
   * 如果不想用在线网站生成的公钥私钥,用这个生成的也可以
   *
   * @throws NoSuchAlgorithmException
   */
  public static void genKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
    // 初始化密钥对生成器，密钥大小为96-1024位
    keyPairGen.initialize(1024, new SecureRandom());
    // 生成一个密钥对，保存在keyPair中
    KeyPair keyPair = keyPairGen.generateKeyPair();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // 得到私钥
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    // 得到公钥
    String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
    // 得到私钥字符串
    String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
    // 将公钥和私钥打印出来直接粘贴出来用即可
    log.info("公钥：" + publicKeyString);
    log.info("----------------------------------------------------------------");
    log.info("私钥： " + privateKeyString);
  }

  /**
   * 加密
   *
   * @param str
   * @return
   * @throws Exception
   */
  public static String encrypt(String str) throws Exception {
    // base64编码的公钥
    byte[] decoded = Base64.decodeBase64(publicKey);
    RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
    // RSA加密
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, pubKey);
    return Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
  }

  /**
   * 解密
   *
   * @param str
   * @return
   * @throws Exception
   */
  public static String decrypt(String str) throws Exception {
    // 64位解码加密后的字符串
    byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
    // base64编码的私钥
    byte[] decoded = Base64.decodeBase64(privateKey);
    RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
    // RSA解密
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, priKey);
    return new String(cipher.doFinal(inputByte));
  }
}
