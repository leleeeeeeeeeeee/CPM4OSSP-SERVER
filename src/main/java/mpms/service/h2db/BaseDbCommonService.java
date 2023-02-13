package io.jpom.service.h2db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import io.jpom.system.LinuxRuntimeException;
import io.jpom.system.db.DbConfig;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * db 日志记录表
 *
 *
 */
public abstract class BaseDbCommonService<T> {

	static {
		// 配置页码是从 1 开始
		PageUtil.setFirstPageNo(1);
	}

	/**
	 * 表名
	 */
	protected final String tableName;
	protected final Class<T> tClass;
	/**
	 * 主键
	 */
	protected final String key;

	public BaseDbCommonService(String tableName, String key, Class<T> tClass) {
		this.tableName = this.covetTableName(tableName, tClass);
		this.tClass = tClass;
		this.key = key;
	}

	@SuppressWarnings("unchecked")
	public BaseDbCommonService(String tableName, String key) {
		this.tClass = (Class<T>) TypeUtil.getTypeArgument(this.getClass());
		this.tableName = this.covetTableName(tableName, this.tClass);
		this.key = key;
	}

	/**
	 * 转换表面
	 *
	 * @param tableName 表面
	 * @param tClass    类
	 * @return 转换后的表名
	 */
	protected String covetTableName(String tableName, Class<T> tClass) {
		return tableName;
	}

}
