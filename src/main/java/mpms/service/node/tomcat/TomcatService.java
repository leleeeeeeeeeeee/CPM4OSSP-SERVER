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

    /**
     * 查询tomcat信息
     *
     * @param nodeModel 节点信息
     * @param id        tomcat的id
     * @return tomcat的信息
     */
    public JSONObject getTomcatInfo(NodeModel nodeModel, String id) {
        return NodeForward.requestData(nodeModel, NodeUrl.Tomcat_GetItem, JSONObject.class, "id", id);
    }


    public JSONArray getTomcatProjectList(NodeModel nodeModel, String id) {
        return NodeForward.requestData(nodeModel, NodeUrl.Tomcat_GetTomcatProjectList, JSONArray.class, "id", id);
    }

    /**
     * tomcat项目管理
     *
     * @param nodeModel 节点信息
     * @param request   请求信息
     * @return 操作结果
     */
    public String tomcatProjectManage(NodeModel nodeModel, HttpServletRequest request) {
        return NodeForward.request(nodeModel, request, NodeUrl.Tomcat_TomcatProjectManage).toString();
    }

    /**
     * 新增Tomcat
     *
     * @param nodeModel 节点信息
     * @param request   请求信息
     * @return 操作结果
     */
    public String addTomcat(NodeModel nodeModel, HttpServletRequest request) {
        return NodeForward.request(nodeModel, request, NodeUrl.Tomcat_Add).toString();
    }

    /**
     * 更新Tomcat信息
     *
     * @param nodeModel 节点信息
     * @param request   请求信息
     * @return 操作结果
     */
    public String updateTomcat(NodeModel nodeModel, HttpServletRequest request) {
        return NodeForward.request(nodeModel, request, NodeUrl.Tomcat_Update).toString();
    }

}
