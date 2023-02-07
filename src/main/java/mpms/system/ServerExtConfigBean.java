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
}
