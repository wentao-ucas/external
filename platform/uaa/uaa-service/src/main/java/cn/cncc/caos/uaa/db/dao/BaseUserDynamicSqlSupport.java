package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import java.sql.JDBCType;
import java.util.Date;

public final class BaseUserDynamicSqlSupport {

    public static final BaseUser baseUser = new BaseUser();


    public static final SqlColumn<Integer> id = baseUser.id;


    public static final SqlColumn<String> userName = baseUser.userName;


    public static final SqlColumn<String> realName = baseUser.realName;


    public static final SqlColumn<String> password = baseUser.password;


    public static final SqlColumn<String> phone = baseUser.phone;


    public static final SqlColumn<String> email = baseUser.email;


    public static final SqlColumn<Date> lastLoginTime = baseUser.lastLoginTime;


    public static final SqlColumn<String> imageUrl = baseUser.imageUrl;


    public static final SqlColumn<Integer> isOnline = baseUser.isOnline;


    public static final SqlColumn<Integer> isAdmin = baseUser.isAdmin;


    public static final SqlColumn<Date> createTime = baseUser.createTime;


    public static final SqlColumn<Integer> depId = baseUser.depId;


    public static final SqlColumn<String> locationName = baseUser.locationName;


    public static final SqlColumn<Integer> isValid = baseUser.isValid;


    public static final SqlColumn<Date> updateTime = baseUser.updateTime;


    public static final SqlColumn<Integer> isPublicUser = baseUser.isPublicUser;


    public static final SqlColumn<String> companyName = baseUser.companyName;


    public static final SqlColumn<String> dutyRole = baseUser.dutyRole;


    public static final class BaseUser extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> userName = column("user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> realName = column("real_name", JDBCType.VARCHAR);

        public final SqlColumn<String> password = column("password", JDBCType.VARCHAR);

        public final SqlColumn<String> phone = column("phone", JDBCType.VARCHAR);

        public final SqlColumn<String> email = column("email", JDBCType.VARCHAR);

        public final SqlColumn<Date> lastLoginTime = column("last_login_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> imageUrl = column("image_url", JDBCType.VARCHAR);

        public final SqlColumn<Integer> isOnline = column("is_online", JDBCType.INTEGER);

        public final SqlColumn<Integer> isAdmin = column("is_admin", JDBCType.INTEGER);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> depId = column("dep_id", JDBCType.INTEGER);

        public final SqlColumn<String> locationName = column("location_name", JDBCType.VARCHAR);

        public final SqlColumn<Integer> isValid = column("is_valid", JDBCType.INTEGER);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> isPublicUser = column("is_public_user", JDBCType.INTEGER);

        public final SqlColumn<String> companyName = column("company_name", JDBCType.VARCHAR);

        public final SqlColumn<String> dutyRole = column("duty_role", JDBCType.VARCHAR);

        public BaseUser() {
            super("base_user");
        }
    }
}