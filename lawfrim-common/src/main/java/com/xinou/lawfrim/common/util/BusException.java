package com.xinou.lawfrim.common.util;

/**
 * @author lizhongyuan
 */
public class BusException extends RuntimeException {
	private static final long serialVersionUID = -4368873598973127606L;
	private  final String code;
	private final Object data;

	public BusException(String code, String message) {
		super(message);
		this.code = code;
		this.data = null;
	}

	public BusException(String code, String message, Object data, Exception ex) {
		super(message, ex);
		this.code = code;
		this.data = data;
	}
	public BusException(String code, String message, Exception ex) {
		super(message, ex);
		this.code = code;
		this.data = null;
	}
	public Throwable getInnerException() {
		return super.getCause();
	}
	public Object getData() {
		return data;
	}
	public String getCode() {
		return code;
	}

	public static final BusException TokenExapiredException = new BusException(Config.RE_LOGIN_OVERDUE_CODE,Config.RE_LOGIN_OVERDUE_MSG);

}
