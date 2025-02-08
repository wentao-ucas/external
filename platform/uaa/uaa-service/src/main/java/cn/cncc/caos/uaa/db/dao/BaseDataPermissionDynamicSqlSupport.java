package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BaseDataPermissionDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseDataPermission baseDataPermission = new BaseDataPermission();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = baseDataPermission.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> depId = baseDataPermission.depId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> roleId = baseDataPermission.roleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = baseDataPermission.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = baseDataPermission.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = baseDataPermission.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> type = baseDataPermission.type;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> dataScope = baseDataPermission.dataScope;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseDataPermission extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<Integer> depId = column("dep_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> roleId = column("role_id", JDBCType.INTEGER);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public final SqlColumn<String> type = column("type", JDBCType.VARCHAR);

        public final SqlColumn<String> dataScope = column("data_scope", JDBCType.LONGVARCHAR);

        public BaseDataPermission() {
            super("base_data_permission");
        }
    }
}