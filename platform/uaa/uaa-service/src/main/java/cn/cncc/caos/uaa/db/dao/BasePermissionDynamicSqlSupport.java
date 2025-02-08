package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BasePermissionDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BasePermission basePermission = new BasePermission();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = basePermission.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> name = basePermission.name;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> title = basePermission.title;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> path = basePermission.path;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> parentName = basePermission.parentName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> seq = basePermission.seq;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = basePermission.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> env = basePermission.env;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> notice = basePermission.notice;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = basePermission.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = basePermission.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BasePermission extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<String> path = column("path", JDBCType.VARCHAR);

        public final SqlColumn<String> parentName = column("parent_name", JDBCType.VARCHAR);

        public final SqlColumn<Integer> seq = column("seq", JDBCType.INTEGER);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public final SqlColumn<String> env = column("env", JDBCType.VARCHAR);

        public final SqlColumn<Byte> notice = column("notice", JDBCType.TINYINT);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BasePermission() {
            super("base_permission");
        }
    }
}