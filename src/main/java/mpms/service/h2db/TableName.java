package mpms.service.h2db;

import java.lang.annotation.*;

/**
 * 数据库表名
 *
 * 
 * @since 2021/8/13
 */
@Documented
@Target({ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {

	/**
	 * 表名
	 *
	 * @return tableName
	 */
	String value();
}
