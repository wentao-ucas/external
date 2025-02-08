package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseSysClassDynamicSqlSupport {

    public static final BaseSysClass baseSysClass = new BaseSysClass();


    public static final SqlColumn<Integer> id = baseSysClass.id;


    public static final SqlColumn<String> className = baseSysClass.className;


    public static final SqlColumn<String> classDesc = baseSysClass.classDesc;


    public static final SqlColumn<String> sysAssemble = baseSysClass.sysAssemble;


    public static final SqlColumn<Integer> isValid = baseSysClass.isValid;


    public static final SqlColumn<Date> updateTime = baseSysClass.updateTime;


    public static final class BaseSysClass extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> className = column("class_name", JDBCType.VARCHAR);

        public final SqlColumn<String> classDesc = column("class_desc", JDBCType.VARCHAR);

        public final SqlColumn<String> sysAssemble = column("sys_assemble", JDBCType.VARCHAR);

        public final SqlColumn<Integer> isValid = column("is_valid", JDBCType.INTEGER);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseSysClass() {
            super("base_sys_class");
        }
    }
}