package com.autumn.runtime.exception.filter;

import com.autumn.exception.AutumnError;
import com.autumn.web.vo.ErrorInfoResult;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

/**
 * Sql 异常过滤
 * 
 * @author 老码农 2018-12-07 02:07:55
 */
public class SQLExceptionFilter extends AbstractExceptionFilter {

	/**
	 * 
	 */
	@Override
	protected ErrorInfoResult doInternalFilter(Throwable e) {
		SQLException sqlException = getException(e, SQLException.class);
		if (sqlException != null) {
			return new ErrorInfoResult(true, sqlException, AutumnError.SystemErrorCode.DB_BASE_ERRORCODE, "数据库操作出错",
					"");
		}
		DataAccessException sqlDataAccessException = getException(e, DataAccessException.class);
		if (sqlDataAccessException != null) {
			return new ErrorInfoResult(true, sqlDataAccessException, AutumnError.SystemErrorCode.DB_BASE_ERRORCODE,
					"数据访问出错", "");
		}
		return null;
	}

}
