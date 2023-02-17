package io.jpom.service.node;

import com.alibaba.fastjson.JSONArray;
import io.jpom.common.BaseOperService;
import io.jpom.model.data.OutGivingModel;
import io.jpom.model.data.OutGivingNodeProject;
import io.jpom.permission.BaseDynamicService;
import io.jpom.plugin.ClassFeature;
import io.jpom.system.ServerConfigBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分发管理
 */
@Service
public class OutGivingServer extends BaseOperService<OutGivingModel> implements BaseDynamicService {

    public OutGivingServer() {
        super(ServerConfigBean.OUTGIVING);
    }

}
