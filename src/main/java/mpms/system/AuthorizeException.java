package io.jpom.system;

import cn.jiangzeyin.common.JsonMessage;

/**
 * 授权错误
 */
public class AuthorizeException extends RuntimeException {
	private final JsonMessage<?> jsonMessage;

	public AuthorizeException(JsonMessage<?> jsonMessage, String msg) {
		super(msg);
		this.jsonMessage = jsonMessage;
	}
}
