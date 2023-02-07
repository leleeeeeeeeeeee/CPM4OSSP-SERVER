package io.jpom.system;

import cn.jiangzeyin.common.JsonMessage;

/**
 * 授权错误
 */
public class AuthorizeException extends RuntimeException {
	private final JsonMessage<?> jsonMessage;

}
