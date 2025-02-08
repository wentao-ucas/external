import cn.cncc.caos.log.provider.BapLogService;
import cn.cncc.caos.log.provider.BapLogServiceImpl;
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

public class BapLogServiceTest {
    @Test
    public void testGetInfo() throws URISyntaxException, IOException {
        BapLogService test_inst = new BapLogServiceImpl();
        QueryDate test_date = new QueryDate();

        test_inst.getInfo(test_date);
    }
    @Test
    public void testQueryLog() throws URISyntaxException, IOException {
        BapLogService test_inst = new BapLogServiceImpl();
        QueryDate test_date = new QueryDate();
        //test_inst.queryLog(test_date,20,1);
    }
}
