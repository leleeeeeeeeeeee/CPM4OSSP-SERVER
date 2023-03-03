package io.jpom.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.jwt.JWT;
import cn.jiangzeyin.common.JsonMessage;
import cn.jiangzeyin.common.interceptor.InterceptorPattens;
import cn.jiangzeyin.common.spring.SpringUtil;
import io.jpom.common.BaseServerController;
import io.jpom.common.ServerOpenApi;
import io.jpom.model.data.UserModel;
import io.jpom.service.user.UserService;
import io.jpom.system.ServerConfigBean;
import io.jpom.system.ServerExtConfigBean;
import io.jpom.util.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录拦截器
 *
 */
@InterceptorPattens(sort = -1, exclude = ServerOpenApi.API + "**")
public class LoginInterceptor extends BaseLinxInterceptor {



    /**
     * 尝试获取 header 中的信息
     *
     * @param session ses
     * @param request req
     * @return true 获取成功
     */
    private boolean tryGetHeaderUser(HttpServletRequest request, HttpSession session) {
        String header = request.getHeader(ServerOpenApi.USER_TOKEN_HEAD);
        if (StrUtil.isEmpty(header)) {
            // 兼容就版本 登录状态
            UserModel user = (UserModel) session.getAttribute(SESSION_NAME);
            return user != null;
        }
        UserService userService = SpringUtil.getBean(UserService.class);
        UserModel userModel = userService.checkUser(header);
        if (userModel == null) {
            return false;
        }
        session.setAttribute(LoginInterceptor.SESSION_NAME, userModel);
        return true;
    }

    /**
     * 提示登录
     *
     * @param request       req
     * @param response      res
     * @param handlerMethod 方法
     * @throws IOException 异常
     */
    private void responseLogin(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, int code) throws IOException {
        ServletUtil.write(response, JsonMessage.getString(code, "登录信息已失效,重新登录"), MediaType.APPLICATION_JSON_VALUE);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        BaseServerController.remove();
    }


}
