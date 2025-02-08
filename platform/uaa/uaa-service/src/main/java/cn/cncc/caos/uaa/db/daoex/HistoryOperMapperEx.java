package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.pojo.HistoryOper;
import cn.cncc.caos.uaa.vo.BaseOperationLogSearchParameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HistoryOperMapperEx {

    @Select({"<script>",
            "SELECT * FROM history_oper WHERE 1 = 1",
            "<when test = 'searchParameter.userRealName != null'>",
            "AND user_name LIKE CONCAT('%',#{ searchParameter.userRealName },'%') ",
            "</when>",
            "<when test = 'searchParameter.info != null'>",
            "AND oper_data LIKE CONCAT('%',#{ searchParameter.info },'%') ",
            "</when>",
            "<when test='searchParameter.createTimeStart != null'>",
            "AND (oper_time >= #{searchParameter.createTimeStart} AND oper_time &lt;= #{searchParameter.createTimeEnd})",
            "</when>",
            "${orderString}",
            "</script>"})
    List<HistoryOper> selectPage(@Param("searchParameter") BaseOperationLogSearchParameter searchParameter, @Param("orderString") String orderString);


}
