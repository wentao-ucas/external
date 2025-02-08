package cn.cncc.caos.uaa.vo;

import cn.cncc.caos.common.core.utils.TimeUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Data
public class BaseOperationLogSearchParameter {

  private Date createTimeStart;
  private Date createTimeEnd;

  private String userRealName;
  private String depName;
  private String info;

  private String typeCn;

  private String sortValue;
  private String sortOrder;

  public void setDataNullToDefaultValue() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String str1 = "1971-01-01 00:00:00";
    String str2 = "2037-12-31 00:00:00";


    try {
      if (ObjectUtils.isEmpty(createTimeStart)) {
        createTimeStart = sdf.parse(str1);
        createTimeEnd = sdf.parse(str2);
      } else {
        createTimeStart = TimeUtil.getStartOfTodayDate(createTimeStart);
        createTimeEnd = TimeUtil.getEndOfTodayDate(createTimeEnd);
      }

      if (StringUtils.isEmpty(sortValue)) {
        sortValue = "createTime";
      }
      if (StringUtils.isEmpty(sortOrder)) {
        sortOrder = "desc";
      }

    } catch (ParseException e) {
      log.error("", e);
    }
  }

}
