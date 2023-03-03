package io.jpom.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.jiangzeyin.common.JsonMessage;
import cn.jiangzeyin.common.interceptor.BaseInterceptor;
import cn.jiangzeyin.common.interceptor.InterceptorPattens;
import io.jpom.common.ServerOpenApi;
import io.jpom.system.ServerExtConfigBean;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@InterceptorPattens(value = "/api/**")
public class OpenApiInterceptor extends BaseInterceptor {



}
