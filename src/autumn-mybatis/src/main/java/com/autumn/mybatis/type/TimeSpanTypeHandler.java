package com.autumn.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.autumn.util.TimeSpan;

/**
 * 时间间隔类型处理器
 * 
 * @author 老码农
 *
 *         2017-10-11 10:53:52
 */
public class TimeSpanTypeHandler extends BaseTypeHandler<TimeSpan> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, TimeSpan parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setLong(i, parameter.getTotalMilliseconds());
	}

	@Override
	public TimeSpan getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if (rs.wasNull()) {
			return null;
		}
		return new TimeSpan(rs.getLong(columnName));
	}

	@Override
	public TimeSpan getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if (rs.wasNull()) {
			return null;
		}
		return new TimeSpan(rs.getLong(columnIndex));
	}

	@Override
	public TimeSpan getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if (cs.wasNull()) {
			return null;
		}
		return new TimeSpan(cs.getLong(columnIndex));
	}

}
