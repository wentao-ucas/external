package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseUserIndividuationConfigDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseUserIndividuationConfig baseUserIndividuationConfig = new BaseUserIndividuationConfig();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userId = baseUserIndividuationConfig.userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> moduleId = baseUserIndividuationConfig.moduleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> moduleName = baseUserIndividuationConfig.moduleName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> systemId = baseUserIndividuationConfig.systemId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> mailStatus = baseUserIndividuationConfig.mailStatus;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> smsStatus = baseUserIndividuationConfig.smsStatus;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = baseUserIndividuationConfig.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = baseUserIndividuationConfig.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseUserIndividuationConfig extends SqlTable {
        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<String> moduleId = column("module_id", JDBCType.VARCHAR);

        public final SqlColumn<String> moduleName = column("module_name", JDBCType.VARCHAR);

        public final SqlColumn<String> systemId = column("system_id", JDBCType.VARCHAR);

        public final SqlColumn<Byte> mailStatus = column("mail_status", JDBCType.TINYINT);

        public final SqlColumn<Byte> smsStatus = column("sms_status", JDBCType.TINYINT);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseUserIndividuationConfig() {
            super("base_user_individuation_config");
        }
    }
}