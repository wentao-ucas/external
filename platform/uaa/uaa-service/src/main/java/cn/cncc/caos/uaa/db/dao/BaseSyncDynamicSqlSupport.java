package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BaseSyncDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseSync baseSync = new BaseSync();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = baseSync.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> syncTime = baseSync.syncTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> syncType = baseSync.syncType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> isValid = baseSync.isValid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseSync extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<Date> syncTime = column("sync_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Byte> syncType = column("sync_type", JDBCType.TINYINT);

        public final SqlColumn<Byte> isValid = column("is_valid", JDBCType.TINYINT);

        public BaseSync() {
            super("base_sync");
        }
    }
}