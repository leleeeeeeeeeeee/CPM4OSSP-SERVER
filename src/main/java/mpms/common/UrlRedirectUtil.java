package io.jpom.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

/**
 * url 重定向
 * 配置nginx 代理实现
 *
 */
public class UrlRedirectUtil {

	private static int getPort(HttpServletRequest request) {
		String proxyPort = ServletUtil.getHeaderIgnoreCase(request, "X-Forwarded-Port");
		int port = 0;
		if (StrUtil.isNotEmpty(proxyPort)) {
			port = Integer.parseInt(proxyPort);
		}
		return port;
	}




}
