package io.jpom.service.node;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.jiangzeyin.common.JsonMessage;
import com.alibaba.fastjson.JSONArray;
import io.jpom.common.BaseOperService;
import io.jpom.common.JpomManifest;
import io.jpom.common.ProxyOperat;
import io.jpom.common.forward.NodeForward;
import io.jpom.common.forward.NodeUrl;
import io.jpom.minisyslog.entity.Minisyslog;
import io.jpom.minisyslog.service.MinisyslogService;
import io.jpom.model.Cycle;
import io.jpom.model.data.NodeModel;
import io.jpom.monitor.NodeMonitor;
import io.jpom.permission.BaseDynamicService;
import io.jpom.plugin.ClassFeature;
import io.jpom.system.ServerConfigBean;
import io.jpom.util.StringUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 节点管理
 */
@Service
public class NodeService extends BaseOperService<NodeModel> implements BaseDynamicService {
	@Resource
	private ProxyOperat proxyOperat;

	@Resource
	private MinisyslogService minisyslogService;

	private static final TimedCache<String, List<NodeModel>> TIMED_CACHE = new TimedCache<>(TimeUnit.MINUTES.toMillis(5));


	public NodeService() {
		super(ServerConfigBean.NODE);
	}


}
