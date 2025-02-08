package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseUserRelRoleDynamicSqlSupport {

    public static final BaseUserRelRole baseUserRelRole = new BaseUserRelRole();


    public static final SqlColumn<Integer> id = baseUserRelRole.id;


    public static final SqlColumn<Integer> roleId = baseUserRelRole.roleId;


    public static final SqlColumn<Integer> userId = baseUserRelRole.userId;


    public static final SqlColumn<Integer> isValid = baseUserRelRole.isValid;


    public static final SqlColumn<Date> updateTime = baseUserRelRole.updateTime;


    public static final class BaseUserRelRole extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<Integer> roleId = column("role_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> isValid = column("is_valid", JDBCType.INTEGER);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseUserRelRole() {
            super("base_user_rel_role");
        }
    }
}