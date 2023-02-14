package io.jpom.service.node.ssh;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.ChannelType;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.*;
import io.jpom.common.BaseOperService;
import io.jpom.model.data.NodeModel;
import io.jpom.model.data.SshModel;
import io.jpom.permission.BaseDynamicService;
import io.jpom.plugin.ClassFeature;
import io.jpom.service.node.NodeService;
import io.jpom.system.ConfigBean;
import io.jpom.system.LinuxRuntimeException;
import io.jpom.system.ServerConfigBean;
import io.jpom.system.ServerExtConfigBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SshService extends BaseOperService<SshModel> implements BaseDynamicService {

	@Resource
	private NodeService nodeService;

	public SshService() {
		super(ServerConfigBean.SSH_LIST);
	}

	@Override
	public void addItem(SshModel sshModel) {
		sshModel.setId(IdUtil.fastSimpleUUID());
		super.addItem(sshModel);
	}

	@Override
	public JSONArray listToArray(String dataId) {
		return (JSONArray) JSONArray.toJSON(this.list());
	}

	@Override
	public List<SshModel> list() {
		return (List<SshModel>) filter(super.list(), ClassFeature.SSH);
	}

	public JSONArray listSelect(String nodeId) {
		// 查询ssh
		List<SshModel> sshModels = list();
		List<NodeModel> list = nodeService.list();
		JSONArray sshList = new JSONArray();
		if (sshModels == null) {
			return sshList;
		}
		sshModels.forEach(sshModel -> {
			String sshModelId = sshModel.getId();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", sshModelId);
			jsonObject.put("name", sshModel.getName());
			if (list != null) {
				for (NodeModel nodeModel : list) {
					if (!StrUtil.equals(nodeId, nodeModel.getId()) && StrUtil.equals(sshModelId, nodeModel.getSshId())) {
						jsonObject.put("disabled", true);
						break;
					}
				}
			}
			sshList.add(jsonObject);
		});
		return sshList;
	}
}
