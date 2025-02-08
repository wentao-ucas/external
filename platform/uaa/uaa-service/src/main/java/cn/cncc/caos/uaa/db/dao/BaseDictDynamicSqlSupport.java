package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseDictDynamicSqlSupport {

    public static final BaseDict baseDict = new BaseDict();


    public static final SqlColumn<Integer> id = baseDict.id;


    public static final SqlColumn<String> dictType = baseDict.dictType;


    public static final SqlColumn<String> dictCode = baseDict.dictCode;


    public static final SqlColumn<String> dictValue = baseDict.dictValue;


    public static final SqlColumn<String> dictDesc = baseDict.dictDesc;


    public static final SqlColumn<Integer> dictSeq = baseDict.dictSeq;


    public static final SqlColumn<Integer> parentId = baseDict.parentId;


    public static final class BaseDict extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> dictType = column("dict_type", JDBCType.VARCHAR);

        public final SqlColumn<String> dictCode = column("dict_code", JDBCType.VARCHAR);

        public final SqlColumn<String> dictValue = column("dict_value", JDBCType.VARCHAR);

        public final SqlColumn<String> dictDesc = column("dict_desc", JDBCType.VARCHAR);

        public final SqlColumn<Integer> dictSeq = column("dict_seq", JDBCType.INTEGER);

        public final SqlColumn<Integer> parentId = column("parent_id", JDBCType.INTEGER);

        public BaseDict() {
            super("base_dict");
        }
    }
}