package cn.cncc.caos.external.provider.cloud.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class CloudTaskLogDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final CloudTaskLog cloudTaskLog = new CloudTaskLog();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> taskinstid = cloudTaskLog.taskinstid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> taskid = cloudTaskLog.taskid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> status = cloudTaskLog.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> result = cloudTaskLog.result;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> parseresult = cloudTaskLog.parseresult;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> operator = cloudTaskLog.operator;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createtime = cloudTaskLog.createtime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updttime = cloudTaskLog.updttime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> taskinststatus = cloudTaskLog.taskinststatus;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> endtime = cloudTaskLog.endtime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> deptno = cloudTaskLog.deptno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class CloudTaskLog extends SqlTable {
        public final SqlColumn<String> taskinstid = column("TASKINSTID", JDBCType.VARCHAR);

        public final SqlColumn<String> taskid = column("TASKID", JDBCType.VARCHAR);

        public final SqlColumn<String> status = column("STATUS", JDBCType.CHAR);

        public final SqlColumn<String> result = column("RESULT", JDBCType.VARCHAR);

        public final SqlColumn<String> parseresult = column("PARSERESULT", JDBCType.VARCHAR);

        public final SqlColumn<String> operator = column("OPERATOR", JDBCType.VARCHAR);

        public final SqlColumn<Date> createtime = column("CREATETIME", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updttime = column("UPDTTIME", JDBCType.TIMESTAMP);

        public final SqlColumn<String> taskinststatus = column("TASKINSTSTATUS", JDBCType.CHAR);

        public final SqlColumn<Date> endtime = column("ENDTIME", JDBCType.TIMESTAMP);

        public final SqlColumn<String> deptno = column("DEPTNO", JDBCType.VARCHAR);

        public CloudTaskLog() {
            super("caos_cloud_tasklog");
        }
    }
}