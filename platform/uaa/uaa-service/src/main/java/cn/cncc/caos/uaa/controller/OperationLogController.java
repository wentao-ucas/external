package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.response.BasePageRes;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.uaa.db.pojo.HistoryOper;
import cn.cncc.caos.uaa.helper.OperationLogHelper;
import cn.cncc.caos.uaa.service.HistoryOperService;
import cn.cncc.caos.uaa.utils.SearchListUtil;
import cn.cncc.caos.uaa.vo.BaseOperationLogRes;
import cn.cncc.caos.uaa.vo.BaseOperationLogSearchParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "操作日志")
public class OperationLogController {

  @Resource
  private HistoryOperService historyOperService;

  @PreAuthorize("hasAuthority('log-aduit')")
  @ResponseBody
  @Operation(summary = "获取操作日志接口")
  @RequestMapping(value = "/open_api/operation/log", method = RequestMethod.GET)
  JwResponseData<BasePageRes<BaseOperationLogRes>> getOperationLogPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                                       @RequestParam(value = "searchString", required = false, defaultValue = "{}") String searchString) {
    try {
      BaseOperationLogSearchParameter searchParameter = JacksonUtil.jsonToObj(searchString, BaseOperationLogSearchParameter.class);
      searchParameter.setDataNullToDefaultValue();
      searchParameter.setSortValue("operTime");
      String sortValue = searchParameter.getSortValue();
      String sortOrder = searchParameter.getSortOrder();
      SearchListUtil.judgeSortIsValid(HistoryOper.class, sortOrder, sortValue);
      String sortString = String.format("order by %s %s", cn.cncc.caos.uaa.utils.StringUtil.convertHumpStringToUnderLineString(sortValue), sortOrder);

      BasePageRes<HistoryOper> operationLogPage = historyOperService.getOperationLogPage(pageNum, pageSize, searchParameter, sortString);
      List<HistoryOper> operationLogList = operationLogPage.getList();
      List<BaseOperationLogRes> baseOperationLogResList = new ArrayList<>();
      for (HistoryOper operationLog : operationLogList) {
        BaseOperationLogRes baseOperationLogRes = new BaseOperationLogRes();
        baseOperationLogRes.setCreateTime(operationLog.getOperTime());
        baseOperationLogRes.setUserRealName(operationLog.getRealName());
        baseOperationLogRes.setDepName("");
        baseOperationLogRes.setTypeCn(operationLog.getOperType());
        baseOperationLogRes.setInfo(OperationLogHelper.jsonFormatting(operationLog.getOperData()));
        baseOperationLogResList.add(baseOperationLogRes);
      }

      BasePageRes<BaseOperationLogRes> baseOperationLogResBasePageRes = new BasePageRes<>();
      baseOperationLogResBasePageRes.setList(baseOperationLogResList);
      baseOperationLogResBasePageRes.setTotal(operationLogPage.getTotal());
      return JwResponseData.success("获取成功", baseOperationLogResBasePageRes);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


}
