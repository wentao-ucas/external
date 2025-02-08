package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseCompanyDynamicSqlSupport {

    public static final BaseCompany baseCompany = new BaseCompany();


    public static final SqlColumn<Integer> id = baseCompany.id;


    public static final SqlColumn<String> companyName = baseCompany.companyName;


    public static final SqlColumn<String> contactInfo = baseCompany.contactInfo;


    public static final SqlColumn<String> chargeList = baseCompany.chargeList;


    public static final SqlColumn<Integer> isValid = baseCompany.isValid;


    public static final SqlColumn<Date> updateTime = baseCompany.updateTime;


    public static final class BaseCompany extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> companyName = column("company_name", JDBCType.VARCHAR);

        public final SqlColumn<String> contactInfo = column("contact_info", JDBCType.VARCHAR);

        public final SqlColumn<String> chargeList = column("charge_list", JDBCType.VARCHAR);

        public final SqlColumn<Integer> isValid = column("is_valid", JDBCType.INTEGER);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseCompany() {
            super("base_company");
        }
    }
}