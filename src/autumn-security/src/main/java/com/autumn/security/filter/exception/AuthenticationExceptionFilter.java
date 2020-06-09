package com.autumn.security.filter.exception;

import com.autumn.exception.AutumnError.ApplicationErrorCode;
import com.autumn.runtime.exception.filter.AbstractExceptionFilter;
import com.autumn.web.vo.ErrorInfoResult;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;

/**
 * 权限异常过滤
 * 
 * @author 老码农 2018-12-07 02:57:40
 */
public class AuthenticationExceptionFilter extends AbstractExceptionFilter {

	@Override
	protected ErrorInfoResult doInternalFilter(Throwable e) {
		UnauthenticatedException unauthenticatedException = getException(e, UnauthenticatedException.class);
		if (unauthenticatedException != null) {
			return new ErrorInfoResult(false, unauthenticatedException, ApplicationErrorCode.NOT_LOGIN_ERRORCODE,
					"未登录认证或认证已过期。", "");
		}
		UnauthorizedException unauthorizedException = getException(e, UnauthorizedException.class);
		if (unauthorizedException != null) {
			return new ErrorInfoResult(true, unauthorizedException, ApplicationErrorCode.AUTHORIZATION_ERRORCODE,
					"无权操作。", "");
		}
		IncorrectCredentialsException incorrectCredentialsException = getException(e,
				IncorrectCredentialsException.class);
		if (incorrectCredentialsException != null) {
			return new ErrorInfoResult(true, incorrectCredentialsException,
					ApplicationErrorCode.ACCOUNT_CREDENTIALS_ERRORCODE, "认证失败。", "");
		}
		AuthorizationException authorizationException = getException(e, AuthorizationException.class);
		if (authorizationException != null) {
			return new ErrorInfoResult(true, authorizationException, ApplicationErrorCode.AUTHORIZATION_ERRORCODE,
					"权限认证失败。", "");
		}
		return null;
	}

}
