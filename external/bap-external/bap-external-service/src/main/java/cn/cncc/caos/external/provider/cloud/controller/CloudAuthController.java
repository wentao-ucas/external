package cn.cncc.caos.external.provider.cloud.controller;


import cn.cncc.caos.external.client.cloud.api.ICloudAuthService;
import cn.cncc.caos.external.client.cloud.dto.TokenCacheItem;
import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.response.SuccessResponse;
import cn.cncc.caos.external.client.cloud.vo.CloudAuthParamVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@Slf4j
@Tag(name = "云平台认证相关接口")
public class CloudAuthController {
	@Autowired
	private ICloudAuthService cloudAuthService;
	@RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/auth/list", method = RequestMethod.GET)
	@Operation(summary = "查询云平台认证参数列表")
	public BaseResponse<List<CloudAuthParamVO>> listCloudAuth() {
		List<CloudAuthParamVO> cloudAuthParamVOList = cloudAuthService.getCloudAuthList();
		return new SuccessResponse<>(cloudAuthParamVOList);
	}
//	@SneakyThrows
//	public ResponseData<String> method2(@RequestBody ApiDemo apiDemo){
//		return ResponseData.success("获取成功", "testb.controller.method1返回成功，值为"+ JacksonUtil.objToJson(apiDemo));
//	}
}
