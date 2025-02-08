package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BasePostDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BasePost basePost = new BasePost();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = basePost.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> postCode = basePost.postCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> postName = basePost.postName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> postSort = basePost.postSort;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = basePost.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> createUser = basePost.createUser;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = basePost.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> updateUser = basePost.updateUser;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = basePost.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> remark = basePost.remark;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BasePost extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<String> postCode = column("post_code", JDBCType.VARCHAR);

        public final SqlColumn<String> postName = column("post_name", JDBCType.VARCHAR);

        public final SqlColumn<Integer> postSort = column("post_sort", JDBCType.INTEGER);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public final SqlColumn<String> createUser = column("create_user", JDBCType.VARCHAR);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> updateUser = column("update_user", JDBCType.VARCHAR);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public BasePost() {
            super("base_post");
        }
    }
}