import static org.mockito.Mockito.*;


import cn.cncc.caos.log.provider.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataShareRequestTest {

    @InjectMocks
    private DataShareRequest dataShareRequest;

    @Mock
    private HttpUtil httpUtil; // 假设 HttpUtil 是一个可以静态导入的工具类，这里我们需要 mock 它

    @Before
    public void setUp() {
        // 初始化 mock 对象
        mock(HttpUtil.class);
    }

    @Test
    public void testQuery() throws URISyntaxException, IOException {
        // 准备测试数据
        URL url = DSLUtilTest.class.getClassLoader().getResource("dsl/fullsearch.tpl");
        Path path = Paths.get(url.toURI());

        String template = new String(Files.readAllBytes(path),"UTF-8");
        QueryDate test_date = new QueryDate();
        String bdosodt1002Str = JSON.toJSONString(test_date);
        Map<String, Object> data =  JSONObject.parseObject(String.valueOf(bdosodt1002Str), Map.class);
        data.put("SYSCODE",null);
        data.put("APPCODE",null);
        data.put("STCODE",null);
        data.put("size", "1");
        data.put("from","0");
        String dsl = DSLUtil.velocity(template, data);

        data.put("index","bdosodt1002");
        // 模拟 HttpUtil.post 方法的返回值
        String mockResponse = "{\"status\":\"success\",\"data\":\"some shared data\"}";
        //when(HttpUtil.post(anyString(), anyString(), anyString(), anyInt(), anyString())).thenReturn(mockResponse);

        // 调用 query 方法
        BdosResData result = dataShareRequest.query(dsl, data);

        // 验证结果
        assertNotNull(result);
        System.out.println("返回结果: " + result);
    }

    // 你可以为其他方法编写类似的测试用例
}