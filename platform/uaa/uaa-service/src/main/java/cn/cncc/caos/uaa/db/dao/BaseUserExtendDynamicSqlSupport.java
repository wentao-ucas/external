package cn.cncc.caos.uaa.db.dao;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BaseUserExtendDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BaseUserExtend baseUserExtend = new BaseUserExtend();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userId = baseUserExtend.userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> dutyManager = baseUserExtend.dutyManager;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> primaryDutyManager = baseUserExtend.primaryDutyManager;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> approverMobile = baseUserExtend.approverMobile;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Byte> status = baseUserExtend.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = baseUserExtend.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = baseUserExtend.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BaseUserExtend extends SqlTable {
        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<Byte> dutyManager = column("duty_manager", JDBCType.TINYINT);

        public final SqlColumn<Byte> primaryDutyManager = column("primary_duty_manager", JDBCType.TINYINT);

        public final SqlColumn<Byte> approverMobile = column("approver_mobile", JDBCType.TINYINT);

        public final SqlColumn<Byte> status = column("status", JDBCType.TINYINT);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BaseUserExtend() {
            super("base_user_extend");
        }
    }
}