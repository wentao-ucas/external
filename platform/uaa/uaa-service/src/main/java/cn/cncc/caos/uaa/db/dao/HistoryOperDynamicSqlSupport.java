package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class HistoryOperDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final HistoryOper historyOper = new HistoryOper();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = historyOper.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> userName = historyOper.userName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> operType = historyOper.operType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> operTime = historyOper.operTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userId = historyOper.userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> depId = historyOper.depId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> realName = historyOper.realName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> operData = historyOper.operData;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class HistoryOper extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<String> userName = column("user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> operType = column("oper_type", JDBCType.VARCHAR);

        public final SqlColumn<Date> operTime = column("oper_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> depId = column("dep_id", JDBCType.INTEGER);

        public final SqlColumn<String> realName = column("real_name", JDBCType.VARCHAR);

        public final SqlColumn<String> operData = column("oper_data", JDBCType.LONGVARCHAR);

        public HistoryOper() {
            super("history_oper");
        }
    }
}