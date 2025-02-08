package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BaseRuleDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseRule baseRule = new BaseRule();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> id = baseRule.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> name = baseRule.name;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> title = baseRule.title;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> formula = baseRule.formula;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> parentId = baseRule.parentId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> seq = baseRule.seq;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = baseRule.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = baseRule.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = baseRule.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseRule extends SqlTable {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<String> formula = column("formula", JDBCType.VARCHAR);

        public final SqlColumn<String> parentId = column("parent_id", JDBCType.VARCHAR);

        public final SqlColumn<Integer> seq = column("seq", JDBCType.INTEGER);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseRule() {
            super("base_rule");
        }
    }
}