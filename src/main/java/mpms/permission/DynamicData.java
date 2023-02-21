package io.jpom.permission;

import io.jpom.common.BaseServerController;
import io.jpom.plugin.ClassFeature;
import io.jpom.plugin.MethodFeature;
import io.jpom.service.node.NodeService;
//import io.jpom.service.node.OutGivingServer;
//import io.jpom.service.node.manage.ProjectInfoService;
import io.jpom.service.node.script.ScriptServer;
//import io.jpom.service.node.ssh.SshService;
import io.jpom.service.node.tomcat.TomcatService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态数据权限
 *
 */
public class DynamicData {

    private static final Map<ClassFeature, DynamicData> DYNAMIC_DATA_MAP = new HashMap<>();

    /**
     * 二级数据
     */
    private static final Map<ClassFeature, Set<ClassFeature>> PARENT = new HashMap<>();

}
