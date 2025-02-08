package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class McMessagesHistoryDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final McMessagesHistory mcMessagesHistory = new McMessagesHistory();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = mcMessagesHistory.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userId = mcMessagesHistory.userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> userRealName = mcMessagesHistory.userRealName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> system = mcMessagesHistory.system;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> function = mcMessagesHistory.function;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> title = mcMessagesHistory.title;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> content = mcMessagesHistory.content;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> isCanSkip = mcMessagesHistory.isCanSkip;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> params = mcMessagesHistory.params;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = mcMessagesHistory.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> readTime = mcMessagesHistory.readTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> isValid = mcMessagesHistory.isValid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = mcMessagesHistory.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = mcMessagesHistory.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class McMessagesHistory extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<String> userRealName = column("user_real_name", JDBCType.VARCHAR);

        public final SqlColumn<Byte> system = column("system", JDBCType.TINYINT);

        public final SqlColumn<Byte> function = column("function", JDBCType.TINYINT);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<String> content = column("content", JDBCType.VARCHAR);

        public final SqlColumn<Byte> isCanSkip = column("is_can_skip", JDBCType.TINYINT);

        public final SqlColumn<String> params = column("params", JDBCType.VARCHAR);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public final SqlColumn<Date> readTime = column("read_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Byte> isValid = column("is_valid", JDBCType.TINYINT);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public McMessagesHistory() {
            super("mc_messages_history");
        }
    }
}