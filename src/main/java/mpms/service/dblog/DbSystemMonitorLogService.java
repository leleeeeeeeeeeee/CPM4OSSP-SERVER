package mpms.service.dblog;

import cn.hutool.db.Entity;
import cn.hutool.db.PageResult;
import mpms.model.log.SystemMonitorLog;
import mpms.service.h2db.BaseDbCommonService;
import org.springframework.stereotype.Service;

@Service
public class DbSystemMonitorLogService extends BaseDbCommonService<SystemMonitorLog> {

	public DbSystemMonitorLogService() {
		super(SystemMonitorLog.TABLE_NAME, "id", SystemMonitorLog.class);
	}

	public PageResult<SystemMonitorLog> getMonitorData(long startTime, long endTime) {
		Entity entity = new Entity(SystemMonitorLog.TABLE_NAME);
		entity.set(" MONITORTIME", ">= " + startTime);
		entity.set("MONITORTIME", "<= " + endTime);
		return listPage(entity, null);
	}
}
