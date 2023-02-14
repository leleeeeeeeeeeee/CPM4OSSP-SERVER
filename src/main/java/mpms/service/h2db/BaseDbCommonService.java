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

	protected String getTableName() {
		return tableName;
	}

	protected String getKey() {
		return key;
	}

	/**
	 * 插入数据
	 *
	 * @param t 数据
	 */
	public void insert(T t) {
		Db db = Db.use();
		db.setWrapper((Character) null);
		try {
			Entity entity = this.dataBeanToEntity(t);
			db.insert(entity);
		} catch (SQLException e) {
			throw new LinuxRuntimeException("数据库异常", e);
		}
	}

	/**
	 * 插入数据
	 *
	 * @param t 数据
	 */
	public void insert(Collection<T> t) {
		if (!DbConfig.getInstance().isInit() || CollUtil.isEmpty(t)) {
			// ignore
			return;
		}
		Db db = Db.use();
		db.setWrapper((Character) null);
		try {
			List<Entity> entities = t.stream().map(this::dataBeanToEntity).collect(Collectors.toList());
			db.insert(entities);
		} catch (SQLException e) {
			throw new LinuxRuntimeException("数据库异常", e);
		}
	}

	/**
	 * 实体转 entity
	 *
	 * @param data 实体对象
	 * @return entity
	 */
	protected Entity dataBeanToEntity(T data) {
		Entity entity = new Entity(tableName);
		// 转换为 map
		Map<String, Object> beanToMap = BeanUtil.beanToMap(data, new LinkedHashMap<>(), true, s -> StrUtil.format("`{}`", s));
		entity.putAll(beanToMap);
		return entity;
	}

	/**
	 * 插入数据
	 *
	 * @param entity 要修改的数据
	 * @return 影响行数
	 */
	public int insert(Entity entity) {
		if (!DbConfig.getInstance().isInit()) {
			// ignore
			return 0;
		}
		Db db = Db.use();
		db.setWrapper((Character) null);
		entity.setTableName(tableName);
		try {
			return db.insert(entity);
		} catch (SQLException e) {
			throw new LinuxRuntimeException("数据库异常", e);
		}
	}

	/**
	 * 修改数据，需要自行实现
	 *
	 * @param t 数据
	 * @return 影响行数
	 */
	public int update(T t) {
		return 0;
	}

	/**
	 * 修改数据
	 *
	 * @param entity 要修改的数据
	 * @param where  条件
	 * @return 影响行数
	 */
	public int update(Entity entity, Entity where) {
		if (!DbConfig.getInstance().isInit()) {
			// ignore
			return 0;
		}
		Db db = Db.use();
		db.setWrapper((Character) null);
		if (where.isEmpty()) {
			throw new LinuxRuntimeException("没有更新条件");
		}
		entity.setTableName(tableName);
		where.setTableName(tableName);
		try {
			return db.update(entity, where);
		} catch (SQLException e) {
			throw new LinuxRuntimeException("数据库异常", e);
		}
	}

	/**
	 * 根据主键查询实体
	 *
	 * @param keyValue 主键值
	 * @return 数据
	 */
	public T getByKey(String keyValue) {
		if (StrUtil.isEmpty(keyValue)) {
			return null;
		}
		if (!DbConfig.getInstance().isInit()) {
			// ignore
			return null;
		}
		Entity where = new Entity(tableName);
		where.set(key, keyValue);
		Db db = Db.use();
		db.setWrapper((Character) null);
		Entity entity;
		try {
			entity = db.get(where);
		} catch (SQLException e) {
			throw new LinuxRuntimeException("数据库异常", e);
		}
		return this.entityToBean(entity, this.tClass);
	}


}
