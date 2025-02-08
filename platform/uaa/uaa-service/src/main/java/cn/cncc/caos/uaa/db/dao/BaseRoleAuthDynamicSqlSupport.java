package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BaseRoleAuthDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseRoleAuth baseRoleAuth = new BaseRoleAuth();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = baseRoleAuth.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> roleName = baseRoleAuth.roleName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> roleDesc = baseRoleAuth.roleDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> sysId = baseRoleAuth.sysId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> isValid = baseRoleAuth.isValid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = baseRoleAuth.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = baseRoleAuth.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> roleKey = baseRoleAuth.roleKey;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseRoleAuth extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<String> roleName = column("role_name", JDBCType.VARCHAR);

        public final SqlColumn<String> roleDesc = column("role_desc", JDBCType.VARCHAR);

        public final SqlColumn<Integer> sysId = column("sys_id", JDBCType.INTEGER);

        public final SqlColumn<Byte> isValid = column("is_valid", JDBCType.TINYINT);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> roleKey = column("role_key", JDBCType.VARCHAR);

        public BaseRoleAuth() {
            super("base_role_auth");
        }
    }
}