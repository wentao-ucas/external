package cn.cncc.caos.uaa.db.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BaseDepDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseDep baseDep = new BaseDep();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> id = baseDep.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> depName = baseDep.depName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> depDesc = baseDep.depDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> depCode = baseDep.depCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> parentId = baseDep.parentId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> level = baseDep.level;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> isValid = baseDep.isValid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = baseDep.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseDep extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> depName = column("dep_name", JDBCType.VARCHAR);

        public final SqlColumn<String> depDesc = column("dep_desc", JDBCType.VARCHAR);

        public final SqlColumn<String> depCode = column("dep_code", JDBCType.VARCHAR);

        public final SqlColumn<Integer> parentId = column("parent_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> level = column("level", JDBCType.INTEGER);

        public final SqlColumn<Integer> isValid = column("is_valid", JDBCType.INTEGER);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseDep() {
            super("base_dep");
        }
    }
}