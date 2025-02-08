import cn.cncc.caos.log.provider.DSLUtil;
import cn.cncc.caos.log.provider.QueryDate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DSLUtilTest {


    @Test
    public void testVelocityWithValidData() {
        String template = "Hello, $name! You have $count new messages.";
        Map<String, Object> data = new HashMap<>();
        data.put("name", "John");
        data.put("count", 5);

        String result = DSLUtil.velocity(template, data);
        assertEquals("Hello, John! You have 5 new messages.", result);
    }

    @Test
    public void testVelocityWithEmptyData() {
        String template = "Hello, $name! You have $count new messages.";
        Map<String, Object> data = new HashMap<>();

        String result = DSLUtil.velocity(template, data);
        assertEquals("Hello, $name! You have $count new messages.", result);
        // 注意：这里的断言取决于你的Velocity配置和是否启用了严格的引用模式。
        // 在某些配置下，Velocity可能会替换为空白字符串或保留原样。
    }

    @Test
    public void testVelocityWithNullData() {
        String template = "Hello, $name!";
        Map<String, Object> data = null;

        try {
            DSLUtil.velocity(template, data);
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException e) {
            // 预期会抛出空指针异常，因为data是null
        }
    }

    @Test
    public void testVelocityWithEmptyTemplate() {
        String template = "";
        Map<String, Object> data = new HashMap<>();
        data.put("name", "John");

        String result = DSLUtil.velocity(template, data);
        assertEquals("", result);
    }

    @Test
    public void testVelocityWithNullTemplate() {
        String template = null;
        Map<String, Object> data = new HashMap<>();
        data.put("name", "John");

        try {
            DSLUtil.velocity(template, data);
            fail("Expected NullPointerException or IllegalArgumentException to be thrown");
        } catch (NullPointerException | IllegalArgumentException e) {
            // 预期会抛出空指针异常或非法参数异常，因为template是null
        }
    }

    @Test
    public void testFullSearchDsl() throws IOException, URISyntaxException {

        URL url = DSLUtilTest.class.getClassLoader().getResource("dsl/fullsearch.tpl");
        Path path = Paths.get(url.toURI());

        String templateContent = new String(Files.readAllBytes(path),"UTF-8");

        QueryDate test_date = new QueryDate();
        String bdosodt1002Str = JSON.toJSONString(test_date);
        Map<String, Object> data =  JSONObject.parseObject(String.valueOf(bdosodt1002Str), Map.class);
        data.put("SYSCODE",null);
        data.put("APPCODE",null);
        data.put("STCODE",null);
        String dsl = DSLUtil.velocity(templateContent, data);
        System.out.println(dsl);
    }
}
