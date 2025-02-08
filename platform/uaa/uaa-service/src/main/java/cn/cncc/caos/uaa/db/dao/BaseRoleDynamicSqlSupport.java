package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseRoleDynamicSqlSupport {

    public static final BaseRole baseRole = new BaseRole();


    public static final SqlColumn<Integer> id = baseRole.id;


    public static final SqlColumn<String> roleName = baseRole.roleName;


    public static final SqlColumn<String> roleDesc = baseRole.roleDesc;


    public static final SqlColumn<Integer> sysId = baseRole.sysId;


    public static final SqlColumn<Integer> isValid = baseRole.isValid;


    public static final SqlColumn<Date> updateTime = baseRole.updateTime;


    public static final class BaseRole extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> roleName = column("role_name", JDBCType.VARCHAR);

        public final SqlColumn<String> roleDesc = column("role_desc", JDBCType.VARCHAR);

        public final SqlColumn<Integer> sysId = column("sys_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> isValid = column("is_valid", JDBCType.INTEGER);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseRole() {
            super("base_role");
        }
    }
}