package io.jpom.permission;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.jpom.plugin.ClassFeature;
import io.jpom.plugin.Feature;
import io.jpom.plugin.MethodFeature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 缓存
 *
 */
public class CacheControllerFeature {

	private static final Map<ClassFeature, Set<MethodFeature>> FEATURE_MAP = new HashMap<>();

	private static final Map<String, UrlFeature> URL_FEATURE_MAP = new TreeMap<>();

	/**
	 * 系统管理员使用的权限
	 */
	private static final List<String> SYSTEM_URL = new ArrayList<>();

	public static Map<ClassFeature, Set<MethodFeature>> getFeatureMap() {
		return FEATURE_MAP;
	}

	/**
	 * 判断是否为系统管理员权限url
	 *
	 * @param url url
	 * @return true 只能是系统管理员访问
	 */
	public static boolean isSystemUrl(String url) {
		return SYSTEM_URL.contains(url);
	}

	/**
	 * 获取url 功能方法对象
	 *
	 * @param url url
	 * @return url功能
	 */
	public static UrlFeature getUrlFeature(String url) {
		return URL_FEATURE_MAP.get(url);
	}

}
