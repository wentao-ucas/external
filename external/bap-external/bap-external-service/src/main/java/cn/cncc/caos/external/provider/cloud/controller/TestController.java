package cn.cncc.caos.external.provider.cloud.controller;


import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.response.SuccessResponse;
import cn.cncc.caos.common.core.utils.*;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "测试相关接口")
@MojitoSchema(schemaId = "testController")
public class TestController {
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public BaseResponse<String> method1(@RequestParam(value = "params") String params) {
		return new SuccessResponse<>("testa.controller1.method1返回成功，值为"+params);
	}
//	@SneakyThrows
//	public ResponseData<String> method2(@RequestBody ApiDemo apiDemo){
//		return ResponseData.success("获取成功", "testb.controller.method1返回成功，值为"+ JacksonUtil.objToJson(apiDemo));
//	}
}
