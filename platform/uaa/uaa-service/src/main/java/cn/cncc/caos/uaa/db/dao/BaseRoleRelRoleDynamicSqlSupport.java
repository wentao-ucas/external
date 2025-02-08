package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseRoleRelRoleDynamicSqlSupport {

    public static final BaseRoleRelRole baseRoleRelRole = new BaseRoleRelRole();


    public static final SqlColumn<Integer> id = baseRoleRelRole.id;


    public static final SqlColumn<Integer> roleIdKey = baseRoleRelRole.roleIdKey;


    public static final SqlColumn<Integer> roleIdValue = baseRoleRelRole.roleIdValue;


    public static final SqlColumn<String> roleRelUsage = baseRoleRelRole.roleRelUsage;


    public static final class BaseRoleRelRole extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<Integer> roleIdKey = column("role_id_key", JDBCType.INTEGER);

        public final SqlColumn<Integer> roleIdValue = column("role_id_value", JDBCType.INTEGER);

        public final SqlColumn<String> roleRelUsage = column("role_rel_usage", JDBCType.VARCHAR);

        public BaseRoleRelRole() {
            super("base_role_rel_role");
        }
    }
}