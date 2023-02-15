package mpms.service.node;

import mpms.common.BaseOperService;
import mpms.model.AgentFileModel;
import mpms.system.ServerConfigBean;
import org.springframework.stereotype.Service;

@Service
public class AgentFileService  extends BaseOperService<AgentFileModel> {

    public AgentFileService() {
        super(ServerConfigBean.AGENT_FILE);
    }
}
