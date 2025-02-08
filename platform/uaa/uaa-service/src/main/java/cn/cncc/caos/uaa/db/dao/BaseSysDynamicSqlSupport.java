package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseSysDynamicSqlSupport {

    public static final BaseSys baseSys = new BaseSys();


    public static final SqlColumn<Integer> id = baseSys.id;


    public static final SqlColumn<String> sysName = baseSys.sysName;


    public static final SqlColumn<String> sysTitle = baseSys.sysTitle;


    public static final SqlColumn<String> sysDesc = baseSys.sysDesc;


    public static final SqlColumn<Integer> isBuiltIn = baseSys.isBuiltIn;


    public static final SqlColumn<String> sysUrl = baseSys.sysUrl;


    public static final SqlColumn<String> sysStatus = baseSys.sysStatus;


    public static final SqlColumn<String> sysVersion = baseSys.sysVersion;


    public static final SqlColumn<String> developer = baseSys.developer;


    public static final SqlColumn<String> imageName = baseSys.imageName;


    public static final SqlColumn<Integer> interfaceNum = baseSys.interfaceNum;


    public static final SqlColumn<String> interfaceHelpUrl = baseSys.interfaceHelpUrl;


    public static final SqlColumn<Integer> isValid = baseSys.isValid;


    public static final SqlColumn<Date> updateTime = baseSys.updateTime;


    public static final class BaseSys extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> sysName = column("sys_name", JDBCType.VARCHAR);

        public final SqlColumn<String> sysTitle = column("sys_title", JDBCType.VARCHAR);

        public final SqlColumn<String> sysDesc = column("sys_desc", JDBCType.VARCHAR);

        public final SqlColumn<Integer> isBuiltIn = column("is_built_in", JDBCType.INTEGER);

        public final SqlColumn<String> sysUrl = column("sys_url", JDBCType.VARCHAR);

        public final SqlColumn<String> sysStatus = column("sys_status", JDBCType.VARCHAR);

        public final SqlColumn<String> sysVersion = column("sys_version", JDBCType.VARCHAR);

        public final SqlColumn<String> developer = column("developer", JDBCType.VARCHAR);

        public final SqlColumn<String> imageName = column("image_name", JDBCType.VARCHAR);

        public final SqlColumn<Integer> interfaceNum = column("interface_num", JDBCType.INTEGER);

        public final SqlColumn<String> interfaceHelpUrl = column("interface_help_url", JDBCType.VARCHAR);

        public final SqlColumn<Integer> isValid = column("is_valid", JDBCType.INTEGER);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseSys() {
            super("base_sys");
        }
    }
}