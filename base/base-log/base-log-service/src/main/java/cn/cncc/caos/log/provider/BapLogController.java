package cn.cncc.caos.log.provider;


import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.response.FailResponse;
import cn.cncc.caos.common.core.response.SuccessResponse;
import cn.cncc.caos.log.provider.domain.LogTableDataInfo;
import cn.cncc.cjdp5.common.core.controller.BaseController;
import cn.cncc.cjdp5.common.core.page.TableDataInfo;
import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/search")
@Tag(name = "日志全文检索接口")
public class BapLogController extends BaseController {

//    private final static Logger log = LogManager.getLogger(BapLogController.class);

  @Autowired
  private BapLogService fullsearchService;

  //@PreAuthorize("@ss.hasPermi('system:journal:list')")
  @GetMapping("/load/all")
  @Operation(summary = "测试接口")
  public BaseResponse<Map<String, Object>> loadAll() {
    return new SuccessResponse<>(fullsearchService.loadAll());
  }


  //获取echarts数据
//  @PreAuthorize("@ss.hasPermi('system:journal:list')")
  @Operation(summary = "统计数据（用于ECharts）接口")
  @PostMapping(value = "log/charts")
  public BaseResponse<List<Map<String, Object>>> getInfo(@RequestBody QueryDate bdosodt1002) {
    try {
      List<Map<String, Object>> list = fullsearchService.getInfo(bdosodt1002);
      return new SuccessResponse<>(list);
    } catch (Exception e) {
//            return AjaxResult.error(500,e.getMessage());
      return FailResponse.getResponse(e);
    }
  }


  //获取日志
//  @PreAuthorize("@ss.hasPermi('system:journal:list')")
  @Operation(summary = "日志列表查询接口")
  @PostMapping("/log/list")
  public BaseResponse<LogTableDataInfo> queryLog(@RequestBody QueryDate bdosodt1002,
                                                 @Parameter(name = "pageSize", in = ParameterIn.QUERY, description = "每页数据量", required = true, example = "20")
                                                 @RequestParam Integer pageSize,
                                                 @Parameter(name = "pageNum", in = ParameterIn.QUERY, description = "起始页号", required = true, example = "1")
                                                 @RequestParam Integer pageNum) {
    //分页
    startPage();
    BdosResData bdosResData;
    try {
      bdosResData = fullsearchService.queryLog(bdosodt1002, pageSize, pageNum);
    } catch (Exception e) {
      log.error(e.getMessage());
//            TableDataInfo tableDataInfo = new TableDataInfo();
//            tableDataInfo.setTotal(0);
//            tableDataInfo.setRows(new ArrayList<>());
//            tableDataInfo.setMsgCode(500);
//            tableDataInfo.setMessage(e.getMessage());
//            return tableDataInfo;
      return FailResponse.getResponse(e);
    }
    Object dataStr = bdosResData.getData();
    Map<String, Object> data = JSONObject.parseObject(String.valueOf(dataStr), Map.class);
//    Map<String, Object> data = JacksonUtil.jsonToObj()
    Map<String, Object> hits = (Map<String, Object>) data.get("hits");

    List<Map<String, String>> list = DSLUtil.gethitsToList(hits);
    TableDataInfo dataTable = getDataTable(list);
    Long total = DSLUtil.gethitsToTatal(hits);
    dataTable.setTotal(total);
    LogTableDataInfo info = getLogTableDataInfo(dataTable);
    return new SuccessResponse<>(info);
  }

  private LogTableDataInfo getLogTableDataInfo(TableDataInfo dataTable) {
    LogTableDataInfo info = new LogTableDataInfo();
    BeanUtils.copyProperties(dataTable, info);
    return info;
  }

  //查询ID
//  @PreAuthorize("@ss.hasPermi('system:journal:detail')")
  @Operation(summary = "日志列表查询（根据ID）接口")
  @GetMapping(value = "/log/list/{id}")
  public BaseResponse<LogTableDataInfo> queryEsById(
      @Parameter(name = "id", in = ParameterIn.PATH, description = "日志ID", required = true, example = "123")
      @PathVariable String id) {
    try {
      BdosResData bdosResData = fullsearchService.queryEsById(id);
      Object dataStr = bdosResData.getData();
      Map<String, Object> data = JSONObject.parseObject(String.valueOf(dataStr), Map.class);
      Map<String, Object> hits = (Map<String, Object>) data.get("hits");

      List<Map<String, String>> list = DSLUtil.gethitsToList(hits);
      TableDataInfo dataTable = getDataTable(list);
      Long total = DSLUtil.gethitsToTatal(hits);
      dataTable.setTotal(total);
      LogTableDataInfo info = getLogTableDataInfo(dataTable);
      return new SuccessResponse<>(info);
    } catch (Exception e) {
      log.error(e.getMessage());
//            TableDataInfo tableDataInfo = new TableDataInfo();
//            tableDataInfo.setTotal(0);
//            tableDataInfo.setRows(new ArrayList<>());
//            tableDataInfo.setMsgCode(500);
//            tableDataInfo.setMessage(e.getMessage());
//            return tableDataInfo;
      return FailResponse.getResponse(e);
    }
  }


  //  @PreAuthorize("@ss.hasPermi('system:journal:detail')")
  @Operation(summary = "日志列表查询（相近）接口")
  @PostMapping(value = "log/nearby")
  public BaseResponse<LogTableDataInfo> getLogScroll(NearbyLogParam nearbyLogParam) {
    try {
      String id = nearbyLogParam.getId();
      String direction = nearbyLogParam.getDirection();
      BdosResData res = fullsearchService.getLogScroll(id, direction);

      List rowsdata = new ArrayList();
      if (res.getData() != null) {
        Map<String, Object> data = (Map<String, Object>) res.getData();
        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");
        for (Map<String, Object> row : rows) {
          Object ids = row.get("_id");
          Map<String, String> source = (Map<String, String>) row.get("_source");
          source.put("id", String.valueOf(ids));
          rowsdata.add(source);
        }

        TableDataInfo dataTable = getDataTable(rowsdata);
        Object total = data.get("total");
        dataTable.setTotal(Long.valueOf(String.valueOf(total)));
        LogTableDataInfo info = getLogTableDataInfo(dataTable);
        return new SuccessResponse<>(info);
      }
      return null;
    } catch (Exception e) {
      log.error(e.getMessage());
//            TableDataInfo tableDataInfo = new TableDataInfo();
//            tableDataInfo.setTotal(0);
//            tableDataInfo.setRows(new ArrayList<>());
//            tableDataInfo.setMsgCode(500);
//            tableDataInfo.setMessage(e.getMessage());
//            return tableDataInfo;
      return FailResponse.getResponse(e);
    }

  }
}
