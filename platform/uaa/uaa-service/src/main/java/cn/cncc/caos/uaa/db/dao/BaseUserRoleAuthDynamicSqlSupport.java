package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BaseUserRoleAuthDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseUserRoleAuth baseUserRoleAuth = new BaseUserRoleAuth();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = baseUserRoleAuth.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> roleId = baseUserRoleAuth.roleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userId = baseUserRoleAuth.userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = baseUserRoleAuth.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = baseUserRoleAuth.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = baseUserRoleAuth.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseUserRoleAuth extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<String> roleId = column("role_id", JDBCType.VARCHAR);

        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public BaseUserRoleAuth() {
            super("base_user_role_auth");
        }
    }
}