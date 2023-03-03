package io.jpom.common.interceptor;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.jiangzeyin.common.JsonMessage;
import cn.jiangzeyin.common.interceptor.InterceptorPattens;
import cn.jiangzeyin.common.spring.SpringUtil;
import io.jpom.common.ServerOpenApi;
import io.jpom.model.data.SystemIpConfigModel;
import io.jpom.service.system.SystemIpConfigService;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ip 访问限制拦截器
 *
 *
 */
@InterceptorPattens(sort = -2, exclude = ServerOpenApi.API + "**")
public class IpInterceptor extends BaseLinxInterceptor {





}
