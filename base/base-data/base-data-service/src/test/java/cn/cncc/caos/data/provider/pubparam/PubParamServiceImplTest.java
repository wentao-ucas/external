package cn.cncc.caos.data.provider.pubparam;

import cn.cncc.caos.common.core.enums.PubParamTypeEnum;
import cn.cncc.caos.data.client.pubparam.api.IPubParamService;
import cn.cncc.caos.data.client.pubparam.dto.PubParamDto;
import cn.cncc.caos.data.provider.BapDataProviderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @className: PubParamServiceImplTest
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/29 14:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BapDataProviderApplication.class)
@TestPropertySource(properties = {"spring.profiles.active=dev"})
@ActiveProfiles("dev")
public class PubParamServiceImplTest {
    @Autowired
    private IPubParamService pubParamService;

    private String opsAuthId="huawei_beijing_ops_0";

    @Test
    public void testGetParamDetailById(){
        PubParamDto pubParamDto = pubParamService.getParamDetailById(opsAuthId);
        assertNotNull(pubParamDto);
    }

    @Test
    public void testGetParamlistByType(){
        List<PubParamDto> pubParamDtoList = pubParamService.getParamlistByType(PubParamTypeEnum.HWCLOUD_AUTH.getType());
        assertEquals("期望记录有四条记录",4,pubParamDtoList.size());
    }
}
