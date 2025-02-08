package cn.cncc.caos.external.client.cloud.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author wb_zyang
 * @Description 指标数据查询条件VO
 * @date 2024/12/04
 * @since v1.0.0
 */
@Getter
@Setter
public class SeriesDataParamVO {
    private List<Map> samples;
    private int period;
    @JsonProperty(value ="time_range")
    private String timeRange;
    private List<String> statistics;
}
