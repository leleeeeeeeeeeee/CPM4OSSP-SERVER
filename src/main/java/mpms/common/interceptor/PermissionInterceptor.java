package io.jpom.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.jiangzeyin.common.JsonMessage;
import cn.jiangzeyin.common.interceptor.InterceptorPattens;
import cn.jiangzeyin.common.spring.SpringUtil;
import io.jpom.common.BaseServerController;
import io.jpom.model.data.NodeModel;
import io.jpom.model.data.UserModel;
import io.jpom.permission.DynamicData;
import io.jpom.permission.SystemPermission;
import io.jpom.plugin.ClassFeature;
import io.jpom.plugin.Feature;
import io.jpom.plugin.MethodFeature;
import io.jpom.service.node.NodeService;
import io.jpom.service.user.RoleService;
import io.jpom.system.AgentException;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拦截器
 *
 
 
 */
@InterceptorPattens(sort = 1)
public class PermissionInterceptor extends BaseLinxInterceptor {

	private NodeService nodeService;
	private RoleService roleService;

	/**
	 * 操作类型参数
	 */
	private static final String TYPE = "type";

	/**
	 * 新增操作
	 */
	private static final String TYPE_ADD = "add";






}
