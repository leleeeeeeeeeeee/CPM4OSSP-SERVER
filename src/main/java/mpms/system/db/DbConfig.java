package io.jpom.system.db;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import cn.hutool.db.sql.Direction;
import cn.hutool.db.sql.Order;
import cn.jiangzeyin.common.DefaultSystemLog;

import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * 数据库配置
 */
public class DbConfig {

	private static final String DB = "db";

	/**
	 * 默认的账号或者密码
	 */
	public static final String DEFAULT_USER_OR_PWD = "jpom";

	private static DbConfig dbConfig;

	/**
	 * 是否初始化成功
	 */
	private volatile boolean init;

	/**
	 * 单利模式
	 *
	 * @return config
	 */
	public static DbConfig getInstance() {
		if (dbConfig == null) {
			dbConfig = new DbConfig();
		}
		return dbConfig;
	}

	public void initOk() {
		init = true;
	}

	public boolean isInit() {
		return init;
	}

	/**
	 * 清除超限制数量的数据
	 *
	 * @param tableName 表名
	 * @param timeClo   时间字段名
	 */
	public static void autoClear(String tableName, String timeClo) {

	}


	public static void autoClear(String tableName, String timeClo, int maxCount, Consumer<Long> consumer) {
		autoClear(tableName, timeClo, maxCount, null, consumer);
	}

	/**
	 * 自动清理数据接口
	 *
	 * @param tableName 表名
	 * @param timeClo   时间字段
	 * @param maxCount  最大数量
	 * @param consumer  查询出超过范围的时间回调
	 */
	public static void autoClear(String tableName, String timeClo, int maxCount, Consumer<Entity> whereCon, Consumer<Long> consumer) {
		if (maxCount <= 0) {
			return;
		}
		ThreadUtil.execute(() -> {
			Entity entity = Entity.create(tableName);
			if (whereCon != null) {
				// 条件
				whereCon.accept(entity);
			}
			Page page = new Page(maxCount, 1);
			page.addOrder(new Order(timeClo, Direction.DESC));
			PageResult<Entity> pageResult;
			try {
				pageResult = Db.use().setWrapper((Character) null).page(entity, page);
				if (pageResult.isEmpty()) {
					return;
				}
				Entity entity1 = pageResult.get(0);
				long time = Convert.toLong(entity1.get(timeClo.toUpperCase()), 0L);
				if (time <= 0) {
					return;
				}
				consumer.accept(time);
			} catch (SQLException e) {
				DefaultSystemLog.getLog().error("数据库查询异常", e);
			}
		});
	}
}
