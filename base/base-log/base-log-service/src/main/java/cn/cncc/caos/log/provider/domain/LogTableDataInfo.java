package cn.cncc.caos.log.provider.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LogTableDataInfo {
  @Schema(description = "数量", type = "Long", defaultValue = "1", example = "1")
  private long total;
  @Schema(description = "数据", type = "List", example = "[{\"puttime\": \"2024-06-13 01:20:20.79\",\"filenode\": \"072507c9718e2de65f02a1bc0a5f195c82256\",\"logdata\": \"testtesttest\",\"syscode\": \"IBPS\",\"logtype\": \"log\",\"collno\": \"53515876733911535\",\"filename\": \"middleware_api_2024-06-13.log\",\"machine\": \"SCBSP001\",\"gettime\": \"2024-06-13 01:20:03.567\",\"nodecode\": \"SHNPC\",\"filedir\": \"/tmp/Easymonitor/log/middleware_api\",\"appcode\": \"KPI\",\"id\": \"YvqkFZABis1tOKkNazjk\",\"logtime\": \"2024-06-13 01:20:03.567\",\"msgtype\": \"\"}]")
  private List<?> rows;
}
