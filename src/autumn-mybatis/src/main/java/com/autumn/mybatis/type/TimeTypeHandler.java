package com.autumn.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.autumn.util.StringUtils;
import com.autumn.util.Time;

/**
 * 时间类型处理器
 *
 * @author 老码农
 * <p>
 * 2017-10-11 10:51:28
 */
public class TimeTypeHandler extends BaseTypeHandler<Time> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Time parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setTime(i, new java.sql.Time(parameter.getHour(), parameter.getMinute(), parameter.getSecond()));
    }

    private Time getTime(java.sql.Time time) {
        if (time == null) {
            return null;
        }
        return new Time(time.getHours(), time.getMinutes(), time.getSeconds());
    }

    @Override
    public Time getNullableResult(ResultSet rs, String columnName) throws SQLException {
        java.sql.Time time = rs.getTime(columnName);
        return this.getTime(time);
    }

    @Override
    public Time getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        java.sql.Time time = rs.getTime(columnIndex);
        return this.getTime(time);
    }

    @Override
    public Time getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        java.sql.Time time = cs.getTime(columnIndex);
        return this.getTime(time);
    }

}
