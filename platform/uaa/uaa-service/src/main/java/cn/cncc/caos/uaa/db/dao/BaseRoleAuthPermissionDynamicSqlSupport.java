package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BaseRoleAuthPermissionDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseRoleAuthPermission baseRoleAuthPermission = new BaseRoleAuthPermission();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = baseRoleAuthPermission.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> roleId = baseRoleAuthPermission.roleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> permissionId = baseRoleAuthPermission.permissionId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = baseRoleAuthPermission.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = baseRoleAuthPermission.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = baseRoleAuthPermission.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseRoleAuthPermission extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<String> roleId = column("role_id", JDBCType.VARCHAR);

        public final SqlColumn<String> permissionId = column("permission_id", JDBCType.VARCHAR);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public BaseRoleAuthPermission() {
            super("base_role_auth_permission");
        }
    }
}