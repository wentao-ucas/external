package cn.cncc.caos.external.provider.cloud;

import cn.cncc.caos.external.client.cloud.api.ICloudAuthService;
import cn.cncc.caos.external.client.cloud.dto.TokenCacheItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @className: CloudAuthServiceTest
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/20 10:24
 */
public class CloudAuthServiceImplTest extends BaseTest {
  @Autowired
  private ICloudAuthService cloudAuthService;

  private String opsAuthId = "huawei_beijing_ops_0";
  private String opmAuthId = "huawei_beijing_opm_0";

  @Test
  public void testIsAuth4Opm() {
    boolean isAuth = cloudAuthService.isValid(opmAuthId);
    assertEquals(true, isAuth);
  }

  @Test
  public void testIsAuth4Ops() {
    boolean isAuth = cloudAuthService.isValid(opsAuthId);
    assertEquals(true, isAuth);
  }

  @Test
  public void testGetCloudAuthList() {
    int size = cloudAuthService.getCloudAuthList().size();
    assertEquals(4, size);
  }

  @Test
  public void testGetToken4Opm() {
    TokenCacheItem opmToken = cloudAuthService.getToken(opmAuthId);
    assertNotNull("token 不为空", opmToken);
  }

  @Test
  public void testGetToken4Ops() {
    TokenCacheItem opsToken = cloudAuthService.getToken(opsAuthId);
    assertNotNull("token 不为空", opsToken);
  }
}
