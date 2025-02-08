package cn.cncc.caos.external.provider.cloud.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class CloudTaskCfgDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final CloudTaskCfg cloudTaskCfg = new CloudTaskCfg();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> taskid = cloudTaskCfg.taskid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> taskdesc = cloudTaskCfg.taskdesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> url = cloudTaskCfg.url;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> reqtype = cloudTaskCfg.reqtype;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> planetype = cloudTaskCfg.planetype;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> param = cloudTaskCfg.param;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> authid = cloudTaskCfg.authid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> croncfg = cloudTaskCfg.croncfg;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> parserule = cloudTaskCfg.parserule;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> status = cloudTaskCfg.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> operator = cloudTaskCfg.operator;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> deptno = cloudTaskCfg.deptno;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createtime = cloudTaskCfg.createtime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> remark = cloudTaskCfg.remark;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updttime = cloudTaskCfg.updttime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> taskstatus = cloudTaskCfg.taskstatus;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class CloudTaskCfg extends SqlTable {
        public final SqlColumn<String> taskid = column("TASKID", JDBCType.VARCHAR);

        public final SqlColumn<String> taskdesc = column("TASKDESC", JDBCType.VARCHAR);

        public final SqlColumn<String> url = column("URL", JDBCType.VARCHAR);

        public final SqlColumn<String> reqtype = column("REQTYPE", JDBCType.VARCHAR);

        public final SqlColumn<String> planetype = column("PLANETYPE", JDBCType.CHAR);

        public final SqlColumn<String> param = column("PARAM", JDBCType.VARCHAR);

        public final SqlColumn<String> authid = column("AUTHID", JDBCType.VARCHAR);

        public final SqlColumn<String> croncfg = column("CRONCFG", JDBCType.VARCHAR);

        public final SqlColumn<String> parserule = column("PARSERULE", JDBCType.VARCHAR);

        public final SqlColumn<String> status = column("STATUS", JDBCType.CHAR);

        public final SqlColumn<String> operator = column("OPERATOR", JDBCType.VARCHAR);

        public final SqlColumn<String> deptno = column("DEPTNO", JDBCType.VARCHAR);

        public final SqlColumn<Date> createtime = column("CREATETIME", JDBCType.TIMESTAMP);

        public final SqlColumn<String> remark = column("REMARK", JDBCType.VARCHAR);

        public final SqlColumn<Date> updttime = column("UPDTTIME", JDBCType.TIMESTAMP);

        public final SqlColumn<String> taskstatus = column("TASKSTATUS", JDBCType.CHAR);

        public CloudTaskCfg() {
            super("caos_cloud_taskcfg");
        }
    }
}