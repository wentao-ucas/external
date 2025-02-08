package cn.cncc.caos.log.provider;

import cn.cncc.caos.common.core.utils.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class BapLogServiceImpl implements BapLogService {

  @Autowired
  private DataShareRequest rpcUtil;
  private String fullSearchTmp = null;
  private String fullSearchEChartsTmp = null;

  public BapLogServiceImpl() throws URISyntaxException, IOException {
    initialize();
  }

  private void initialize() throws URISyntaxException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    try (InputStream is = classLoader.getResourceAsStream("dsl/fullsearch.tpl");
         InputStream chartIS = classLoader.getResourceAsStream("dsl/fullsearch_echart.tpl")) {
      this.fullSearchTmp = FileUtil.convertInpustreamToString(is);
      this.fullSearchEChartsTmp = FileUtil.convertInpustreamToString(chartIS);
      log.info(fullSearchTmp);
      log.info(fullSearchEChartsTmp);
    }
    /*
    URL url = BapLogServiceImpl.class.getClassLoader().getResource("dsl/fullsearch.tpl");
    Path path = Paths.get(url.toURI());

    this.fullSearchTmp = new String(Files.readAllBytes(path), "UTF-8");


    url = BapLogServiceImpl.class.getClassLoader().getResource("dsl/fullsearch_echart.tpl");
    path = Paths.get(url.toURI());

    this.fullSearchEChartsTmp = new String(Files.readAllBytes(path), "UTF-8");
*/
  }

  @Override
  public BdosResData queryLog(QueryDate bdosodt1002,Integer pageSize, Integer pageNum) {
    //返回值
    List<Map<String, String>> res = new ArrayList<>();
    //获取pageNum、pageSize

    //PageDomain pageDomain = TableSupport.buildPageRequest();
    //Integer pageNum = pageDomain.getPageNum();
    //Integer pageSize = pageDomain.getPageSize();

    Integer from = (pageNum - 1) * pageSize;

    bdosodt1002.setFrom(String.valueOf(from));
    bdosodt1002.setSize(String.valueOf(pageSize));

    String bdosodt1002Str = JSON.toJSONString(bdosodt1002);
    Map<String, Object> data = JSONObject.parseObject(String.valueOf(bdosodt1002Str), Map.class);


    String template = this.fullSearchTmp;
    // 在data里面put 模板参数
    data.put("SYSCODE", null);
    data.put("APPCODE", null);
    data.put("STCODE", null);
    //解析
    String dsl = DSLUtil.velocity(template, data);
    //访问数据共享
    Map param = new HashMap();
    param.put("index", "bdosodt1002");
    BdosResData bdosResData = rpcUtil.query(dsl, param);
    if (!"BDOS0000".equals(bdosResData.getCode())) {
      throw new RuntimeException("[ RPC -- ERROR ] response code : " + bdosResData.getCode() + " msg : " + bdosResData.getMessage());
    }

    return bdosResData;
  }

  @Override
  public BdosResData queryEsById(String id) {
    String template = "{\n" +
        "  \"query\": {\n" +
        "    \"ids\": {\n" +
        "      \"values\": [\"${id}\"]\n" +
        "    }\n" +
        "  }\n" +
        "}";

    Map<String, Object> esparam = new HashMap<>();
    esparam.put("id", id);
    //解析
    String dsl = DSLUtil.velocity(template, esparam);
    //访问数据共享
    Map param = new HashMap();
    param.put("index", "bdosodt1002");
    BdosResData bdosResData = rpcUtil.query(dsl, param);
    if (!"BDOS0000".equals(bdosResData.getCode())) {
      throw new RuntimeException("[ RPC -- ERROR ] response code : " + bdosResData.getCode() + " msg : " + bdosResData.getMessage());
    }
    return bdosResData;
  }

  @Override
  public List<Map<String, Object>> getInfo(QueryDate bdosodt1002) {
    List<Map<String, Object>> res = new ArrayList<>();

    String template = this.fullSearchEChartsTmp;
    String bdosodt1002Str = JSON.toJSONString(bdosodt1002);
    Map<String, Object> data = JSONObject.parseObject(String.valueOf(bdosodt1002Str), Map.class);


    //时间维度处理
    try {
      data = initesTime(data);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    //解析
    String dsl = DSLUtil.velocity(template, data);

    //访问数据共享
    Map param = new HashMap();
    param.put("index", "bdosodt1002");
    BdosResData bdosResData = rpcUtil.query(dsl, param);
    if (!"BDOS0000".equals(bdosResData.getCode())) {
      throw new RuntimeException("[ RPC -- ERROR ] response code : " + bdosResData.getCode() + " msg : " + bdosResData.getMessage());
    }

    Object dataStr = bdosResData.getData();
    Map<String, Object> datashare = JSONObject.parseObject(String.valueOf(dataStr), Map.class);
    Map<String, Object> aggr = (Map<String, Object>) datashare.get("aggregations");
    Map<String, Object> numcont = (Map<String, Object>) aggr.get("numcont");
    List<Map<String, Object>> buckets = (List<Map<String, Object>>) numcont.get("buckets");

    for (Map<String, Object> bucket : buckets) {
      Map<String, Object> cont = new LinkedHashMap<>();
      cont.put("time", bucket.get("key_as_string"));
      cont.put("value", bucket.get("doc_count"));
      res.add(cont);
    }
    return res;
  }

  @Override
  public Map<String, Object> loadAll() {
    Map<String, Object> res = new HashMap<>();

    //List<Map<String, String>> list0901 = bdosodt0913Mapper.loadAll("bdosodt0901");
    //List<Map<String, String>> list0904 = bdosodt0913Mapper.loadAll("bdosodt0904");
    //List<Map<String, String>> list0903 = bdosodt0913Mapper.loadAll("bdosodt0903");
    //ist<Map<String, String>> list0902 = bdosodt0913Mapper.loadAll("bdosodt0902");

    //res.put("machine",list0901);
    //res.put("app",list0904);
    //res.put("sys",list0903);
    //res.put("mac",list0902);

    return res;
  }

  private Map<String, Object> initesTime(Map<String, Object> data) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //默认年
    data.put("interval", "year");
    String mintime1 = String.valueOf(data.get("mintime"));
    String maxtime1 = String.valueOf(data.get("maxtime"));
    Date mintime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mintime1);
    Date maxtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(maxtime1);
    long time = maxtime.getTime() - mintime.getTime();
    long timescale = 24 * 3 * 60 * 1000;
    //minute：分
    if (timescale <= time) {
      data.put("interval", "3m");
    }
    //quarter：刻
    timescale = 24 * 15 * 60 * 1000;
    if (timescale <= time) {
      data.put("interval", "15m");
    }
    //hour：时
    timescale = 24 * 1 * 60 * 60 * 1000;
    if (timescale <= time) {
      data.put("interval", "hour");
    }
    //day：日
    timescale = 24 * 1 * 24 * 60 * 60 * 1000L;
    if (timescale <= time) {
      data.put("interval", "day");
    }
    //week:周
    timescale = 24 * 7 * 24 * 60 * 60 * 1000L;
    if (timescale <= time) {
      data.put("interval", "week");
    }


    return data;
  }

  @Override
  public BdosResData getLogScroll(String id, String direction) {
    //访问数据共享
    Map param = new HashMap();
    param.put("apikey", "API_DIEPEM_LOG_SCROLL");
    Map<String, String> map = new HashMap();
    map.put("id", id);
    map.put("direction", direction);
    param.put("param", map);
    BdosResData bdosResData = rpcUtil.getLogScroll(param);
    if (!"BDOS0000".equals(bdosResData.getCode())) {
      throw new RuntimeException("[ RPC -- ERROR ] response code : " + bdosResData.getCode() + " msg : " + bdosResData.getMessage());
    }

    return bdosResData;
  }
}
