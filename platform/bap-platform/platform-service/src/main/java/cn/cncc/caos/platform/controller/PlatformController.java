package cn.cncc.caos.platform.controller;

import cn.cncc.caos.common.core.request.BapRequest;
import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.response.FailResponse;
import cn.cncc.caos.common.core.response.SuccessResponse;
import cn.cncc.caos.platform.domain.TestReq;
import cn.cncc.caos.platform.domain.TestRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "系统管理接口")
public class PlatformController {
//  private PlatformService platformService;

//  @Autowired
//  public PlatformController(PlatformService platformService) {
//    this.platformService = platformService;
//  }

  @Operation(summary = "测试GET接口")
//  @RequestMapping(value = "/test1", method = RequestMethod.GET)
  @GetMapping("/test1/{userId}")
  public BaseResponse<TestRes> test1(
      @Parameter(name = "userId", in = ParameterIn.PATH, description = "用户id", required = true, example = "605")
      @PathVariable("userId") Integer userId
  ) {
    return new SuccessResponse<>();
  }

  @Operation(summary = "测试POST接口")
//  @RequestMapping(value = "/test2", method = RequestMethod.POST)
  @PostMapping("/test2")
  public BaseResponse<TestRes> test2(@RequestBody BapRequest<TestReq> req) {
    return new SuccessResponse<>(new TestRes());
  }

  @Operation(summary = "测试PUT接口")
  @PutMapping("/test3")
  public BaseResponse<TestRes> test3(@RequestBody BapRequest<TestReq> req) {
    try {
      return new SuccessResponse<>(new TestRes());
    } catch (Exception e) {
      log.error("", e);
      return FailResponse.getResponse(e);
    }
  }

  @Operation(summary = "测试DELETE接口")
  @DeleteMapping("/test1")
  public BaseResponse<TestRes> test4(
      @Parameter(name = "userId", in = ParameterIn.QUERY, description = "用户id", required = true, example = "605")
      @RequestParam("userId") Integer userId
  ) {
    return new SuccessResponse<>();
  }
//
//  @RequestMapping(value = "/test3", method = RequestMethod.GET)
//  public BaseResponse<Object> test3() {
//    try {
//      JwResponseData<List<DepartmentDTO>> depRes = platformService.test3();
//      return new SuccessResponse<>(depRes.getData());
//    } catch (Exception e) {
//      log.error("", e);
//      return FailResponse.getResponse(e);
//    }
//  }
}
