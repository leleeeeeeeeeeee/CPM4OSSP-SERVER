package io.jpom.system;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.script.ScriptUtil;
import cn.jiangzeyin.common.spring.SpringUtil;
import io.jpom.system.db.DbConfig;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 外部配置文件
 */
@Configuration
public class ServerExtConfigBean implements DisposableBean {

	/**
	 * 系统最多能创建多少用户
	 */
	@Value("${user.maxCount:10}")
	public int userMaxCount;
	/**
	 * 用户连续登录失败次数，超过此数将自动不再被允许登录，零是不限制
	 */
	@Value("${user.alwaysLoginError:5}")
	public int userAlwaysLoginError;

	/**
	 * 当ip连续登录失败，锁定对应IP时长，单位毫秒
	 */
	@Value("${user.ipErrorLockTime:60*60*5*1000}")
	private String ipErrorLockTime;
	private long ipErrorLockTimeValue = -1;

	/**
	 * 数据库账号、默认为 root
	 */
	@Value("${db.userName:}")
	private String dbUserName;

	/**
	 * 数据库密码、默认为 root
	 */
	@Value("${db.userPwd:}")
	private String dbUserPwd;

	/**
	 * 服务端api token,长度要求大于等于6位，字母数字符号组合
	 */
	@Value("${mpms.authorize.token:}")
	private String authorizeToken;

	/**
	 * 登录token失效时间(单位：小时),默认为24
	 */
	@Value("${mpms.authorize.expired:24}")
	private int authorizeExpired;

	/**
	 * 登录token失效后自动续签时间（单位：分钟），默认为60，
	 */
	@Value("${mpms.authorize.renewal:60}")
	private int authorizeRenewal;

	/**
	 * 登录token 加密的key 长度建议控制到 16位
	 */
	@Value("${mpms.authorize.key:}")
	private String authorizeKey;

	/**
	 * 构建最多保存多少份历史记录
	 */
	@Value("${build.maxHistoryCount:1000}")
	private int buildMaxHistoryCount;


	@Value("${build.itemMaxHistoryCount:50}")
	private int buildItemMaxHistoryCount;

	/**
	 * ssh 中执行命令 初始化的环境变量
	 */
	@Value("${ssh.initEnv:}")
	private String sshInitEnv;

	/**
	 * 上传文件的超时时间 单位秒,最短5秒中
	 */
	@Value("${node.uploadFileTimeOut:300}")
	private int uploadFileTimeOut;


}
