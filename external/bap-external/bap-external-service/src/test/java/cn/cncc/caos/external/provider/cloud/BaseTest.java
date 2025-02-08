package cn.cncc.caos.external.provider.cloud;

import cn.cncc.caos.external.provider.BapExternalProviderApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wb_zyang
 * @Description {description}
 * @date 2024/11/28
 * @since v1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BapExternalProviderApplication.class)
@TestPropertySource(properties = {"spring.profiles.active=dev"})
@ActiveProfiles("dev")
public class BaseTest {
}
