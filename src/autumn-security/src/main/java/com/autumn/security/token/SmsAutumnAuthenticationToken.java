package com.autumn.security.token;

/**
 * 短信认证登录
 * 
 * @author 老码农 2018-12-06 09:50:34
 */
public class SmsAutumnAuthenticationToken extends AbstractAutumnAuthenticationToken {

	public static final String SMS_PROVIDER = "SMS_TOKEN";

	/**
	 * 
	 */
	private static final long serialVersionUID = -4544909392106771310L;

	private final String mobilePhone;
	private final String token;
	private final String smsCode;

	/**
	 * @param mobilePhone
	 * @param token
	 * @param smsCode
	 */
	public SmsAutumnAuthenticationToken(String mobilePhone, String token, String smsCode) {
		this.mobilePhone = mobilePhone;
		this.token = token;
		this.smsCode = smsCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getToken() {
		return token;
	}

	public String getSmsCode() {
		return smsCode;
	}

	@Override
	public Object getPrincipal() {
		return this.getMobilePhone();
	}

	@Override
	public Object getCredentials() {
		return this.getToken();
	}

	@Override
	public ToeknAuditedLog createAuditedLog() {
		ToeknAuditedLog log = new ToeknAuditedLog();
		log.setUserAccount(this.getMobilePhone());
		log.setProvider(SMS_PROVIDER);
		log.setProviderKey(this.getSmsCode());
		log.setFailStatusMessage("短信验证码不正确");
		return log;
	}
}
