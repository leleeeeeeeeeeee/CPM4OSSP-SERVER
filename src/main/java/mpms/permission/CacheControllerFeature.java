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

}
