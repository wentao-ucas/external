package cn.cncc.caos.log.provider;

import java.util.List;
import java.util.Map;

public interface BapLogService {
    BdosResData queryLog(QueryDate bdosodt1002, Integer pageSize, Integer pageNum);

    BdosResData queryEsById(String id);

    List<Map<String, Object>> getInfo(QueryDate bdosodt1002);

    Map<String, Object> loadAll();

    BdosResData getLogScroll(String id, String direction);
}
