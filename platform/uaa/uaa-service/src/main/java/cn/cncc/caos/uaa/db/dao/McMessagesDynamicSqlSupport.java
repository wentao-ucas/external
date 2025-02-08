package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class McMessagesDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final McMessages mcMessages = new McMessages();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = mcMessages.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userId = mcMessages.userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> userRealName = mcMessages.userRealName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> system = mcMessages.system;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> function = mcMessages.function;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> title = mcMessages.title;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> isCanSkip = mcMessages.isCanSkip;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> params = mcMessages.params;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = mcMessages.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> readTime = mcMessages.readTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> isValid = mcMessages.isValid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = mcMessages.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = mcMessages.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> content = mcMessages.content;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class McMessages extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<String> userRealName = column("user_real_name", JDBCType.VARCHAR);

        public final SqlColumn<Byte> system = column("system", JDBCType.TINYINT);

        public final SqlColumn<Byte> function = column("function", JDBCType.TINYINT);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<Byte> isCanSkip = column("is_can_skip", JDBCType.TINYINT);

        public final SqlColumn<String> params = column("params", JDBCType.VARCHAR);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public final SqlColumn<Date> readTime = column("read_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Byte> isValid = column("is_valid", JDBCType.TINYINT);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> content = column("content", JDBCType.LONGVARCHAR);

        public McMessages() {
            super("mc_messages");
        }
    }
}