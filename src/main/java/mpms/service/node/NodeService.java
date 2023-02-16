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

	public HashSet<String> getAllGroup() {
		//获取所有分组
		List<NodeModel> nodeModels = list();
		HashSet<String> hashSet = new HashSet<>();
		if (nodeModels == null) {
			return hashSet;
		}
		for (NodeModel nodeModel : nodeModels) {
			hashSet.add(nodeModel.getGroup());
		}
		return hashSet;
	}

	/**
	 * 获取所有节点 和节点下面的项目
	 *
	 * @return list
	 */
	public List<NodeModel> listAndProject() {
		List<NodeModel> nodeModels = this.list();
		Iterator<NodeModel> iterator = nodeModels.iterator();
		while (iterator.hasNext()) {
			NodeModel nodeModel = iterator.next();
			if (!nodeModel.isOpenStatus()) {
				iterator.remove();
				continue;
			}
			try {
				// 获取项目信息不需要状态
				JSONArray jsonArray = NodeForward.requestData(nodeModel, NodeUrl.Manage_GetProjectInfo, JSONArray.class, "notStatus", "true");
				if (jsonArray != null) {
					nodeModel.setProjects(jsonArray);
				} else {
					iterator.remove();
				}
			} catch (Exception e) {
				iterator.remove();
			}
		}
		return nodeModels;
	}

	/**
	 * 获取所有节点 和节点下面的项目和状态
	 *
	 * @return list
	 */
	public List<NodeModel> listAndProjectAndStatus() {
		List<NodeModel> nodeModels = this.list();
		Iterator<NodeModel> iterator = nodeModels.iterator();
		while (iterator.hasNext()) {
			NodeModel nodeModel = iterator.next();
			if (!nodeModel.isOpenStatus()) {
				iterator.remove();
				continue;
			}
			try {
				// 获取项目信息不需要状态
				JSONArray jsonArray = NodeForward.requestData(nodeModel, NodeUrl.Manage_GetProjectInfo, JSONArray.class, null, "true");
				if (jsonArray != null) {
					nodeModel.setProjects(jsonArray);
				} else {
					iterator.remove();
				}
			} catch (Exception e) {
				iterator.remove();
			}
		}
		return nodeModels;

	}


	public String cacheNodeList(List<NodeModel> list) {
		String reqId = IdUtil.fastUUID();
		TIMED_CACHE.put(reqId, list);
		return reqId;
	}

	/**
	 * 获取页面编辑的节点信息
	 *
	 * @param id id
	 * @return list
	 */
	public List<NodeModel> getNodeModel(String id) {
		return TIMED_CACHE.get(id);
	}

	@SneakyThrows
	public String addNode(NodeModel nodeModel, HttpServletRequest request) {
        System.out.println("添加的节点信息： " + nodeModel.toString());
		Pattern pattern = Pattern.compile("[0-9]*");
		if ((!StringUtil.isGeneral(nodeModel.getId(), 2, 20))||(!pattern.matcher(nodeModel.getId()).matches())) {
			return JsonMessage.getString(405, "节点id必须为2-20位纯数字");
		}
		if (getItem(nodeModel.getId()) != null) {
			return JsonMessage.getString(405, "节点id已经存在啦");
		}
		String error = checkData(nodeModel);
		if (error != null) {
			return error;
		}
		// 补充日志信息
		Minisyslog minisyslog = new Minisyslog();
		minisyslog.setContent("Node information was added successfully.");
		minisyslog.setLevel(0);
		minisyslog.setType(0);
		minisyslog.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 地址是否可达
		Boolean isOpen = testUrl("http://"+nodeModel.getUrl()+"/node"+NodeUrl.SetTopCycle, 2000);
		if(isOpen&&nodeModel.isOpenStatus()){
			try {
				NodeForward.request(nodeModel, request, NodeUrl.SetTopCycle);
			}catch (Exception e){
				return JsonMessage.getString(400, "帐号密码不正确");
			}
			minisyslog.setExtra("新增id为"+nodeModel.getId()+"的节点,当前节点状态为启用,节点地址为"+nodeModel.getUrl()+"！");
			// 将该节点信息记录到数据库
			addItem(nodeModel);
			// 新增节点日志
			minisyslogService.insert(minisyslog);
			return JsonMessage.getString(200, "节点信息新增成功,并且节点可用");
		}else{
			minisyslog.setExtra("新增id为"+nodeModel.getId()+"的节点,当前节点状态为禁用,节点地址为"+nodeModel.getUrl()+"！");
			// 设置不可用
			nodeModel.setOpenStatus(false);
			// 将该节点信息记录到数据库
			addItem(nodeModel);
			// 新增节点日志
			minisyslogService.insert(minisyslog);
			return JsonMessage.getString(200, "节点信息新增成功，但是节点不可用");
		}

	}

	public String updateNode(NodeModel nodeModel, HttpServletRequest request) {
		NodeModel exit = getItem(nodeModel.getId());
		if (exit == null) {
			return JsonMessage.getString(405, "节点不存在");
		}
		String error = checkData(nodeModel);
		if (error != null) {
			return error;
		}
		if(nodeModel!=null){
			int timeOut = nodeModel.getTimeOut();
			if(timeOut <0 || timeOut> 180){
				return JsonMessage.getString(405, "设置的超时时间必须在3分钟内");
			}
		}
		// 补充日志信息
		Minisyslog minisyslog = new Minisyslog();
		minisyslog.setContent("The node information is updated successfully.");
		minisyslog.setLevel(0);
		minisyslog.setType(0);
		minisyslog.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 地址是否可达
		Boolean isOpen = testUrl("http://"+nodeModel.getUrl()+"/node"+NodeUrl.SetTopCycle, 2000);
		if(isOpen&&nodeModel.isOpenStatus()){
			minisyslog.setExtra("更新id为"+nodeModel.getId()+"的节点，当前节点状态为启用,节点地址为"+nodeModel.getUrl()+"！");
			try{
				NodeForward.request(nodeModel, request, NodeUrl.SetTopCycle);
			}catch (Exception e){
				return JsonMessage.getString(400, "帐号密码不正确");
			}
			// 更新节点数据到数据库
			updateItem(nodeModel);
			// 新增节点日志
			minisyslogService.insert(minisyslog);
			return JsonMessage.getString(200, "节点信息更新成功，并且节点可用");
		}else{
			minisyslog.setExtra("更新id为"+nodeModel.getId()+"的节点，当前节点状态为禁用,节点地址为"+nodeModel.getUrl()+"！");
			// 设置不可用
			nodeModel.setOpenStatus(false);
			// 更新节点数据到数据库
			updateItem(nodeModel);
			// 新增节点日志
			minisyslogService.insert(minisyslog);
			return JsonMessage.getString(200, "节点信息更新成功，但是节点不可用");
		}
	}

	/**
	 * 测试URL是否可用
	 * @param urlString
	 * @param timeOutMillSeconds
	 */
	public static Boolean testUrl(String urlString,int timeOutMillSeconds){
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlString);
			//创建连接
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(timeOutMillSeconds);
			//开始连接
			connection.connect();
		} catch (Exception e1) {
			return false;
		}finally {
			url = null;
			if(connection!=null){
				connection.disconnect();
			}
		}
		return true;
	}

	private String checkData(NodeModel nodeModel) {
		String name = nodeModel.getName();
		if (name == null || "".equals(name)) {
			return JsonMessage.getString(405, "节点名称 不能为空");
		}
		String loginPwd = nodeModel.getLoginPwd();
		if (loginPwd == null || "".equals(loginPwd)) {
			return JsonMessage.getString(405, "节点密码 不能为空");
		}
		String loginName = nodeModel.getLoginName();
		if (loginName == null || "".equals(loginName)) {
			return JsonMessage.getString(405, "节点帐号 不能为空");
		}
		String id = nodeModel.getId();
		if (id == null || "".equals(id)) {
			return JsonMessage.getString(405, "节点ID 不能为空");
		}
		List<NodeModel> nodeList = list();
		for (NodeModel item:nodeList) {
			// 判断节点名称是否重复
			if(nodeModel.getName().equals(item.getName()) && !item.getId().equals(nodeModel.getId())){
				return JsonMessage.getString(405, "节点名称重复");
			}
		}
		List<NodeModel> list = list();
		if (list != null) {
			for (NodeModel model : list) {
				if (model.getUrl().equalsIgnoreCase(nodeModel.getUrl()) && !model.getId().equalsIgnoreCase(nodeModel.getId())) {
					return JsonMessage.getString(405, "已经存在相同的节点地址啦");
				}
			}
		}
		return null;
	}

	@Override
	public JSONArray listToArray(String dataId) {
		return (JSONArray) JSONArray.toJSON(this.list());
	}

