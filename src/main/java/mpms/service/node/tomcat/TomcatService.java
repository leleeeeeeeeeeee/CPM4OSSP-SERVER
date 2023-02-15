package io.jpom.service.node.tomcat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jpom.common.forward.NodeForward;
import io.jpom.common.forward.NodeUrl;
import io.jpom.model.data.NodeModel;
import io.jpom.permission.BaseDynamicService;
import io.jpom.plugin.ClassFeature;
import io.jpom.service.node.NodeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * tomcat
 *
 * 
 */
@Service
public class TomcatService implements BaseDynamicService {

    @Resource
    private NodeService nodeService;

    /**
     * 查询tomcat列表
     *
     * @param nodeModel 节点信息
     * @return tomcat的信息
     */
    public JSONArray getTomcatList(NodeModel nodeModel) {
        if (!nodeModel.isOpenStatus()) {
            return null;
        }
        JSONArray jsonArray = NodeForward.requestData(nodeModel, NodeUrl.Tomcat_List, JSONArray.class, null, null);
        return filter(jsonArray, ClassFeature.TOMCAT);
    }

}
