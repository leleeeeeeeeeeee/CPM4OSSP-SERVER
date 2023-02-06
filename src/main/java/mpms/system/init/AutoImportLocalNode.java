package io.jpom.system.init;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import cn.jiangzeyin.common.PreLoadClass;
import cn.jiangzeyin.common.PreLoadMethod;
import cn.jiangzeyin.common.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import io.jpom.common.JpomManifest;
import io.jpom.common.Type;
import io.jpom.model.data.NodeModel;
import io.jpom.model.system.AgentAutoUser;
import io.jpom.service.node.NodeService;
import io.jpom.system.ConfigBean;
import io.jpom.system.ServerConfigBean;
import io.jpom.util.JsonFileUtil;
import io.jpom.util.JvmUtil;

import java.io.File;
import java.util.List;

/**
 * 自动导入本机节点
 */
@PreLoadClass
public class AutoImportLocalNode {

	private static final String AGENT_MAIN_CLASS = "io.jpom.JpomAgentApplication";
	private static NodeService nodeService;

	@PreLoadMethod
	private static void install() {
		File file = FileUtil.file(ConfigBean.getInstance().getDataPath(), ServerConfigBean.INSTALL);
		if (file.exists()) {
			return;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("installId", IdUtil.fastSimpleUUID());
		jsonObject.put("installTime", DateTime.now().toString());
		jsonObject.put("desc", "请勿删除此文件,服务端安装id和插件端互通关联");
		JsonFileUtil.saveJson(file.getAbsolutePath(), jsonObject);
	}
}
