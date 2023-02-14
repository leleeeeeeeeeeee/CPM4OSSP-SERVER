package io.jpom.service.node.script;

import com.alibaba.fastjson.JSONArray;
import io.jpom.common.forward.NodeForward;
import io.jpom.common.forward.NodeUrl;
import io.jpom.model.data.NodeModel;
import io.jpom.permission.BaseDynamicService;
import io.jpom.plugin.ClassFeature;
import io.jpom.service.node.NodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ScriptServer implements BaseDynamicService {

	@Resource
	private NodeService nodeService;

}
