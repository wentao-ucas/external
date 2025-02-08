package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class AllServerConfigDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final AllServerConfig allServerConfig = new AllServerConfig();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = allServerConfig.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> module = allServerConfig.module;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> function = allServerConfig.function;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> configKey = allServerConfig.configKey;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> configValue = allServerConfig.configValue;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> configDesc = allServerConfig.configDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> serverEnv = allServerConfig.serverEnv;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> location = allServerConfig.location;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> cTime = allServerConfig.cTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> uTime = allServerConfig.uTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = allServerConfig.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class AllServerConfig extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<String> module = column("module", JDBCType.VARCHAR);

        public final SqlColumn<String> function = column("function", JDBCType.VARCHAR);

        public final SqlColumn<String> configKey = column("config_key", JDBCType.VARCHAR);

        public final SqlColumn<String> configValue = column("config_value", JDBCType.VARCHAR);

        public final SqlColumn<String> configDesc = column("config_desc", JDBCType.VARCHAR);

        public final SqlColumn<String> serverEnv = column("server_env", JDBCType.VARCHAR);

        public final SqlColumn<String> location = column("location", JDBCType.VARCHAR);

        public final SqlColumn<Date> cTime = column("c_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> uTime = column("u_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public AllServerConfig() {
            super("all_server_config");
        }
    }
}