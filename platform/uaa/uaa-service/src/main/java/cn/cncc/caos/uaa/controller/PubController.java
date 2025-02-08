package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.service.BaseDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/pub")
@Tag(name = "公共接口")
public class PubController {
  @Resource
  private ApplicationContext applicationContext;

  @Operation(summary = "内存数据重载接口")
  @RequestMapping(value = "/reload", method = RequestMethod.GET)
  public JwResponseData<Object> reloadAll() {
    String[] dataServiceNames = applicationContext.getBeanNamesForType(BaseDataService.class);
    for (String name : dataServiceNames) {
      BaseDataService service = applicationContext.getBean(name, BaseDataService.class);
      try {
        service.reload();
      } catch (Exception e) {
        return JwResponseData.error(JwResponseCode.SERVER_ERROR);
      }
    }
    return JwResponseData.success("内存数据重载成功");
  }
}
