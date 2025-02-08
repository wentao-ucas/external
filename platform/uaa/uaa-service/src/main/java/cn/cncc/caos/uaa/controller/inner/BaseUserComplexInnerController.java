package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.controller.base.BaseBaseUserComplexController;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// @RestController
@Slf4j
@MojitoSchema(schemaId = "inner_api/baseUserComplexInnerController")
public class BaseUserComplexInnerController extends BaseBaseUserComplexController {

    @ResponseBody
    // @RequestMapping(value = "/inner_api/iops_login", method = RequestMethod.GET)
    public JwResponseData<Map<String, Object>> iopsLogin(@RequestParam(value = "type", required = false, defaultValue = "code") String type,
                                                       @RequestParam(value = "value", required = false, defaultValue = "074806ed0bcd459fb126571bd362a147%7") String value) {
        return super.iopsLogin(type, value);
    }

}

