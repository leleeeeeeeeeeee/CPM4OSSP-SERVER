package io.jpom.permission;

import cn.hutool.core.comparator.PropertyComparator;
import cn.hutool.core.util.StrUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import cn.jiangzeyin.common.spring.SpringUtil;
import cn.jiangzeyin.controller.base.AbstractController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jpom.common.BaseServerController;
import io.jpom.model.BaseModel;
import io.jpom.model.data.RoleModel;
import io.jpom.model.data.UserModel;
import io.jpom.plugin.ClassFeature;
import io.jpom.service.user.RoleService;

import java.util.*;
import java.util.stream.Collectors;

/**
 *

 */
public interface BaseDynamicService {

	/***
	 *  过滤角色数据
	 * @param jsonArray 原array
	 * @param classFeature 功能
	 * @return 过滤后的，如果当前没有登录信息就不过滤
	 */
	default JSONArray filter(JSONArray jsonArray, ClassFeature classFeature) {
		// 获取当前用户
		UserModel userModel = BaseServerController.getUserModel();
		if (jsonArray == null || userModel == null) {
			return jsonArray;
		}
		if (userModel.isSystemUser()) {
			// 系统管理全部权限
			return jsonArray;
		}
		RoleService bean = SpringUtil.getBean(RoleService.class);
		String parentId = getParameterValue(classFeature);
		Set<String> dynamicList = bean.getDynamicList(userModel, classFeature, parentId);
		if (dynamicList == null) {
			return null;
		}
		List<Object> collect = jsonArray.stream().filter(o -> {
			JSONObject jsonObject = (JSONObject) o;
			String id = jsonObject.getString("id");
			return dynamicList.contains(id);
		}).collect(Collectors.toList());
		return (JSONArray) JSONArray.toJSON(collect);
	}

	/***
	 *  过滤角色数据
	 * @param list 原list
	 * @param classFeature 功能
	 * @return 过滤后的，如果当前没有登录信息就不过滤
	 */
	default List<? extends BaseModel> filter(List<? extends BaseModel> list, ClassFeature classFeature) {
		// 获取当前用户
		UserModel userModel = BaseServerController.getUserModel();
		if (list == null || userModel == null) {
			return list;
		}
		if (userModel.isSystemUser()) {
			// 系统管理全部权限
			return list;
		}
        return list;
//		RoleService bean = SpringUtil.getBean(RoleService.class);
//		String parentId = getParameterValue(classFeature);
//        System.out.println("parentId = " + parentId);
//		Set<String> dynamicList = bean.getDynamicList(userModel, classFeature, parentId);
//		if (dynamicList == null) {
//			// 没有角色没有权限
//			return null;
//		}
//		//
//		return list.stream().filter(baseModel -> dynamicList.contains(baseModel.getId())).collect(Collectors.toList());
	}

	/**
	 * 获取参数
	 *
	 * @param classFeature 功能
	 * @return 参数
	 */
	default String getParameterValue(ClassFeature classFeature) {
		ClassFeature parent = classFeature.getParent();
		if (parent == null) {
			return null;
		}
		DynamicData dynamicData = DynamicData.getDynamicData(parent);
		if (dynamicData == null) {
			return null;
		}
		String parameterName = dynamicData.getChildrenParameterName();
		return AbstractController.getRequestAttributes().getRequest().getParameter(parameterName);
	}
}
