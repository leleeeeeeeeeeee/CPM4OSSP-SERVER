package io.jpom.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import io.jpom.model.data.UserModel;
import io.jpom.system.ServerExtConfigBean;

/**
 * jwt 工具类
 *
 * 
 
 */
public class JwtUtil {

	/**
	 * 加密算法
	 */
	private static final String ALGORITHM = "HS256";
	/**
	 * token的的加密key
	 */
	private static byte[] KEY;
	public static final String KEY_USER_ID = "userId";

	private static byte[] getKey() {
		if (KEY == null) {
			KEY = ServerExtConfigBean.getInstance().getAuthorizeKey();
		}
		return KEY;
	}
}
