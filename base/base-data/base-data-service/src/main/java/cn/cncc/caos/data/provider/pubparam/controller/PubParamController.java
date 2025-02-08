package cn.cncc.caos.data.provider.pubparam.controller;


import cn.cncc.caos.data.client.pubparam.api.IPubParamService;
import cn.cncc.caos.data.client.pubparam.dto.PubParamDto;
import cn.cncc.caos.common.core.enums.PubParamTypeEnum;
import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.response.FailResponse;
import cn.cncc.caos.common.core.response.IResponse;
import cn.cncc.caos.common.core.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "公共参数相关接口")
public class PubParamController {

	@Autowired
	private IPubParamService pubParamService;
	@ResponseBody
	@RequestMapping(value = "/inner_api/v1/caos/bap/data/pubparam/list/by/type", method = RequestMethod.GET)
	public BaseResponse<List<PubParamDto>> getParamlistByType(
			@Parameter(name = "type", in = ParameterIn.QUERY, description = "参数类型", required = true, example = "01")
			@RequestParam(value = "type") String type) {
		log.info("调用方法:getParamlistByType,查询入参:type:{}", type);
		List<PubParamDto> paramList = pubParamService.getParamlistByType(type);
		if(CollectionUtils.isEmpty(paramList)){
			log.warn("cann't get pub param with " + type);
			return new FailResponse<>(IResponse.ERR_PARAMS,"cann't get pub param with " + type);
		}
		return new SuccessResponse<>(paramList);
	}

	@ResponseBody
	@RequestMapping(value = "/inner_api/v1/caos/bap/data/pubparam/query/by/id", method = RequestMethod.GET)
	@Operation(summary = "根据url参数paramId获取公共参数表参数详情")
	public BaseResponse<PubParamDto> getParamDetailById(
			@Parameter(name = "paramId", in = ParameterIn.QUERY, description = "参数id", required = true, example = "huawei_beijing_ops_0")
			@RequestParam(value = "paramId") String paramId) {
		log.info("调用方法:getParamlistById,查询入参:paramId:{}", paramId);
		PubParamDto param = pubParamService.getParamDetailById(paramId);
		return new SuccessResponse<>(param);
	}
}
