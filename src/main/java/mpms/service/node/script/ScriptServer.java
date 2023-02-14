package mpms.service.node.script;

import com.alibaba.fastjson.JSONArray;
import mpms.common.forward.NodeForward;
import mpms.common.forward.NodeUrl;
import mpms.model.data.NodeModel;
import mpms.permission.BaseDynamicService;
import mpms.plugin.ClassFeature;
import mpms.service.node.NodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ScriptServer implements BaseDynamicService {

	@Resource
	private NodeService nodeService;

	@Override
	public JSONArray listToArray(String dataId) {
		NodeModel item = nodeService.getItem(dataId);
		if (item == null || !item.isOpenStatus()) {
			return null;
		}
		return listToArray(item);
	}

	public JSONArray listToArray(NodeModel nodeModel) {
		JSONArray jsonArray = NodeForward.requestData(nodeModel, NodeUrl.Script_List, null, JSONArray.class);
		return filter(jsonArray, ClassFeature.SCRIPT);
	}
}
