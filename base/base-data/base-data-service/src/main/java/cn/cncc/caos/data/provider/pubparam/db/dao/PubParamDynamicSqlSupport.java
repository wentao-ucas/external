package cn.cncc.caos.data.provider.pubparam.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class PubParamDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final PubParam pubParam = new PubParam();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> parmid = pubParam.parmid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> parmname = pubParam.parmname;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> parmval = pubParam.parmval;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> encryptflag = pubParam.encryptflag;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> parmdesc = pubParam.parmdesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> operator = pubParam.operator;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updttime = pubParam.updttime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> type = pubParam.type;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createtime = pubParam.createtime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> status = pubParam.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class PubParam extends SqlTable {
        public final SqlColumn<String> parmid = column("PARMID", JDBCType.VARCHAR);

        public final SqlColumn<String> parmname = column("PARMNAME", JDBCType.VARCHAR);

        public final SqlColumn<String> parmval = column("PARMVAL", JDBCType.VARCHAR);

        public final SqlColumn<String> encryptflag = column("ENCRYPTFLAG", JDBCType.CHAR);

        public final SqlColumn<String> parmdesc = column("PARMDESC", JDBCType.VARCHAR);

        public final SqlColumn<String> operator = column("OPERATOR", JDBCType.VARCHAR);

        public final SqlColumn<Date> updttime = column("UPDTTIME", JDBCType.TIMESTAMP);

        public final SqlColumn<String> type = column("TYPE", JDBCType.CHAR);

        public final SqlColumn<Date> createtime = column("CREATETIME", JDBCType.TIMESTAMP);

        public final SqlColumn<String> status = column("STATUS", JDBCType.CHAR);

        public PubParam() {
            super("caos_pub_param");
        }
    }
}