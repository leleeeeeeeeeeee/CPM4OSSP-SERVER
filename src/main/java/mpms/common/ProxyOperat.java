package io.jpom.common;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jpom.dao.NodeinfoDao;
import io.jpom.entity.Nodeinfo;
import io.jpom.model.data.NodeModel;
import io.jpom.util.CommandUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author stone
 */

@Component
public class ProxyOperat {
    public static final String CONF_PATH = "/etc/linxsaclient.conf";
    public static final String LOCAL_HOST = "127.0.0.1 ";
    public static final String COMMUNICATION_PORT = "57000";
    public static final String PROXY_START_CMD = "/etc/linxsaclientd restart";


    @Resource
    private NodeinfoDao nodeinfoDao;

    public String proxyRestart() {
        String result = CommandUtil.execSystemCommand(PROXY_START_CMD);
        return null;
    }

    public String saveToConf(NodeModel nodeModel) {
        writeToFile(nodeModel);
        proxyRestart();
        return null;
    }

    public String updateConf() {
        List<NodeModel> nodeList = list(NodeModel.class);
        FileUtil.writeUtf8String("", CONF_PATH);
        for (NodeModel tmp : nodeList){
            if (tmp.getProxyPort() !=null){
                writeToFile(tmp);
            }
        }
        proxyRestart();
        return null;
    }

    private void writeToFile(NodeModel nodeModel) {
        String nodeIp = nodeModel.getUrl().split(":")[0];
        String proxyPort = nodeModel.getProxyPort();
        String conf = LOCAL_HOST + proxyPort + " " + nodeIp + " " + COMMUNICATION_PORT + '\n';
        FileUtil.appendString(conf, CONF_PATH, StandardCharsets.UTF_8);
    }

    private JSONObject getInfoFromDb() {
        JSONObject allData = new JSONObject();
        List<Nodeinfo> nodeInfo = nodeinfoDao.queryAllData();
        for (Nodeinfo it : nodeInfo) {
            allData.put(it.getName(), it.getJson());
        }
        return allData;
    }



}
