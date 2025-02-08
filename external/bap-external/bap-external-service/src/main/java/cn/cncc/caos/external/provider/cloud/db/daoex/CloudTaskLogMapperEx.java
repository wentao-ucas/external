package cn.cncc.caos.external.provider.cloud.db.daoex;

import cn.cncc.caos.external.client.cloud.vo.CloudTaskLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @className: CloudTaskLogMapperEx
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/13 8:37
 */
@Mapper
public interface CloudTaskLogMapperEx {
    @Select({"select l.TASKINSTID,c.TASKID,c.TASKDESC,c.CRONCFG,l.PARSERESULT,l.TASKINSTSTATUS,l.CREATETIME,l.ENDTIME,l.DEPTNO,l.OPERATOR",
            "from caos_cloud_taskcfg c left join caos_cloud_tasklog l on c.TASKID=l.TASKID ",
            "where c.`STATUS`='00' and l.`STATUS`='00' and l.TASKINSTSTATUS = #{instStatus,jdbcType=VARCHAR} and l.DEPTNO = #{deptNo,jdbcType=VARCHAR}"})
    @Results(
            id="cloud_task_log_vo",
            value={
                    @Result(column="TASKINSTID", property="taskinstid", jdbcType= JdbcType.VARCHAR, id=true),
                    @Result(column="TASKID", property="taskid", jdbcType=JdbcType.VARCHAR),
                    @Result(column="TASKDESC", property="taskdesc", jdbcType=JdbcType.VARCHAR),
                    @Result(column="CRONCFG", property="croncfg", jdbcType=JdbcType.VARCHAR),
                    @Result(column="PARSERESULT", property="parseresult", jdbcType=JdbcType.VARCHAR),
                    @Result(column="TASKINSTSTATUS", property="taskinststatus", jdbcType=JdbcType.VARCHAR),
                    @Result(column="CREATETIME", property="createtime", jdbcType=JdbcType.TIMESTAMP),
                    @Result(column="ENDTIME", property="endtime", jdbcType=JdbcType.TIMESTAMP),
                    @Result(column="DEPTNO", property="deptno", jdbcType=JdbcType.VARCHAR),
                    @Result(column="OPERATOR", property="operator", jdbcType=JdbcType.VARCHAR)
            })
    List<CloudTaskLogVO> getCloudTaskLogByDeptNoAndInstStatus(@Param("deptNo") String deptNo,@Param("instStatus") String instStatus);


    @Select({"select l.TASKINSTID,c.TASKID,c.TASKDESC,c.CRONCFG,l.PARSERESULT,l.TASKINSTSTATUS,l.CREATETIME,l.ENDTIME,l.DEPTNO,l.OPERATOR",
            "from caos_cloud_tasklog l left join caos_cloud_taskcfg c on c.TASKID=l.TASKID ",
            "where c.`STATUS`='00' and l.`STATUS`='00'  and l.DEPTNO = #{deptNo,jdbcType=VARCHAR} ORDER BY l.CREATETIME desc"})
    @ResultMap("cloud_task_log_vo")
    List<CloudTaskLogVO> getAllCloudTaskLogByDeptNo(@Param("deptNo") String deptNo);

    @Select({"SELECT l.TASKINSTID,l.TASKID,c.TASKDESC,c.CRONCFG,l.PARSERESULT,l.TASKINSTSTATUS,l.CREATETIME,l.ENDTIME,l.DEPTNO,l.OPERATOR " +
            "FROM caos_cloud_tasklog l left join caos_cloud_taskcfg c on c.TASKID=l.TASKID " +
            "where c.`STATUS`='00' and l.`STATUS`='00' and l.DEPTNO=#{deptNo,jdbcType=VARCHAR} and l.TASKID=#{taskId,jdbcType=VARCHAR} ORDER BY l.CREATETIME desc limit 1"})
    @ResultMap("cloud_task_log_vo")
    CloudTaskLogVO getLastCloudTaskLogByTaskIdDeptNo(@Param("deptNo") String deptNo,@Param("taskId") String taskId);

    @Select({"SELECT l.TASKINSTID,l.TASKID,c.TASKDESC,c.CRONCFG,l.PARSERESULT,l.TASKINSTSTATUS,l.CREATETIME,l.ENDTIME,l.DEPTNO,l.OPERATOR " +
            "FROM caos_cloud_tasklog l left join caos_cloud_taskcfg c on c.TASKID=l.TASKID " +
            "where c.`STATUS`='00' and l.`STATUS`='00' and l.DEPTNO=#{deptNo,jdbcType=VARCHAR} and l.TASKID=#{taskId,jdbcType=VARCHAR} ORDER BY l.CREATETIME desc"})
    @ResultMap("cloud_task_log_vo")
    List<CloudTaskLogVO> getAllCloudTaskLogByTaskIdDeptNo(@Param("deptNo") String deptNo,@Param("taskId") String taskId);
}
