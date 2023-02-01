--
-- 构建历史记录表
--
CREATE TABLE IF NOT EXISTS `BUILDHISTORYLOG` (
  `ID` varchar(50) NOT NULL COMMENT '表id',
  `BUILDDATAID` varchar(50) DEFAULT NULL COMMENT '构建的数据id',
  `BUILDNUMBERID` int(11) DEFAULT NULL COMMENT '构建编号',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '构建状态',
  `STARTTIME` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `ENDTIME` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `RESULTDIRFILE` varchar(200) DEFAULT NULL COMMENT '构建产物目录',
  `BUILDUSER` varchar(50) DEFAULT NULL COMMENT '构建人',
  `RELEASEMETHOD` tinyint(4) DEFAULT NULL COMMENT '发布方式',
  `RELEASEMETHODDATAID` varchar(200) DEFAULT NULL COMMENT '发布的数据id',
  `AFTEROPT` tinyint(4) DEFAULT NULL COMMENT '发布后操作',
  `CLEAROLD` tinyint(4) DEFAULT NULL COMMENT '是否清空发布',
  `NAME` varchar(100) DEFAULT NULL COMMENT '构建名称',
  `RELEASECOMMAND` longtext COMMENT '发布命令',
  `RELEASEPATH` varchar(100) DEFAULT NULL COMMENT '发布到的目录',
  `BUILDNAME` varchar(100) DEFAULT NULL COMMENT '构建名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='构建历史记录';

--
-- 构建信息表
--
CREATE TABLE IF NOT EXISTS `BUILD_INFO` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `REPOSITORYID` varchar(50) NOT NULL COMMENT '仓库 ID',
  `CREATETIMEMILLIS` bigint(20) DEFAULT NULL COMMENT '数据创建时间',
  `MODIFYTIMEMILLIS` bigint(20) DEFAULT NULL COMMENT '数据修改时间',
  `NAME` varchar(50) DEFAULT NULL COMMENT '构建名称',
  `BUILDID` int(11) DEFAULT NULL COMMENT '构建 ID',
  `GROUP` varchar(50) DEFAULT NULL COMMENT '分组名称',
  `BRANCHNAME` varchar(50) DEFAULT NULL COMMENT '分支',
  `SCRIPT` varchar(200) DEFAULT NULL COMMENT '构建命令',
  `RESULTDIRFILE` varchar(50) DEFAULT NULL COMMENT '构建产物目录',
  `RELEASEMETHOD` int(11) DEFAULT NULL COMMENT '发布方法{0: 不发布, 1: 节点分发, 2: 分发项目, 3: SSH}',
  `MODIFYUSER` varchar(50) DEFAULT NULL COMMENT '修改人',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `TRIGGERTOKEN` varchar(20) DEFAULT NULL COMMENT '触发器token',
  `EXTRADATA` longtext COMMENT '额外信息，JSON 字符串格式',
  `RELEASEMETHODDATAID` varchar(200) DEFAULT NULL COMMENT '构建关联的数据ID',
  `BRANCHTAGNAME` varchar(50) DEFAULT NULL COMMENT '标签',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='构建信息';


--
-- 软件包信息模板
--
DROP TABLE IF EXISTS `PackageInfoTemplate`;
CREATE TABLE IF NOT EXISTS `PackageInfoTemplate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `status` varchar(10) NOT NULL COMMENT '包状态',
  `name` varchar(50) NOT NULL COMMENT '包名',
  `version` varchar(50) NOT NULL COMMENT '包版本',
  `pkg_arch` varchar(20) NOT NULL COMMENT '包架构',
  `content` varchar(100) COMMENT '包描述',
  `pkg_class` varchar(20) DEFAULT 'necessary' COMMENT '包分类',
  `os_arch` varchar(20) DEFAULT '80_amd64' NOT NULL COMMENT '系统架构', 
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='软件包信息模板';

LOAD DATA LOCAL INFILE '/usr/share/mpms_serve/deb_template' INTO TABLE PackageInfoTemplate
FIELDS TERMINATED BY ' '
LINES TERMINATED BY '\n';

--
-- 耗时任务表
--
CREATE TABLE IF NOT EXISTS `DelayedTask` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `task_id` int(11) NOT NULL COMMENT '任务id',
  `task_priority` int(11) DEFAULT NULL COMMENT '任务优先级',
  `task_action` text COMMENT '任务操作',
  `task_target` text COMMENT '任务目标',
  `task_status` int(11) DEFAULT NULL COMMENT '任务状态',
  `task_content` text COMMENT '任务结果内容',
  `task_view_times` int(11) COMMENT '任务被查看次数',
  `task_group_id` int(11) DEFAULT NULL COMMENT '任务组id',
  `task_node_id` text DEFAULT NULL COMMENT '任务所属节点id',
  `task_extra` text COMMENT '任务拓展项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=389 DEFAULT CHARSET=utf8 COMMENT='耗时任务表';


--
-- 监控异常日志记录表
--
CREATE TABLE IF NOT EXISTS `MONITORNOTIFYLOG` (
  `LOGID` varchar(50) NOT NULL COMMENT '记录id',
  `MONITORID` varchar(50) DEFAULT NULL COMMENT '监控id',
  `NODEID` varchar(30) DEFAULT NULL COMMENT '节点id',
  `PROJECTID` varchar(30) DEFAULT NULL COMMENT '项目id',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '异常时间',
  `TITLE` varchar(100) DEFAULT NULL COMMENT '异常描述',
  `CONTENT` longtext COMMENT '异常内容',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '当前状态',
  `NOTIFYSTYLE` tinyint(4) DEFAULT NULL COMMENT '通知方式',
  `NOTIFYSTATUS` tinyint(4) DEFAULT NULL COMMENT '通知状态',
  `NOTIFYOBJECT` varchar(10000) DEFAULT NULL COMMENT '通知对象',
  `NOTIFYERROR` longtext COMMENT '通知异常内容',
  PRIMARY KEY (`LOGID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控异常日志记录';

--
-- 凝思系统软件安装管理平台系统日志表
--
CREATE TABLE IF NOT EXISTS `MiniSysLog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '日志类型 节点/平台',
  `ip` text COMMENT 'IP地址',
  `time` text COMMENT '日志时间',
  `content` text COMMENT '日志内容',
  `level` int(11) DEFAULT NULL COMMENT '日志等级',
  `extra` text COMMENT '日志拓展',
  PRIMARY KEY (`id`),
  UNIQUE KEY `MiniSysLog_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8500 DEFAULT CHARSET=utf8 COMMENT='凝思系统软件安装管理平台系统日志';

--
-- 监控信息表
--
CREATE TABLE IF NOT EXISTS `MonitorInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `node_id` text NOT NULL COMMENT '节点id',
  `monitor_time` bigint(20) NOT NULL COMMENT '监控时间',
  `disk_usage` text NOT NULL COMMENT '磁盘使用率',
  `memory_usage` text NOT NULL COMMENT '内存使用率',
  `cpu_usage` text NOT NULL COMMENT 'CPU使用率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18122 DEFAULT CHARSET=utf8 COMMENT='监控信息';

--
--
--
CREATE TABLE IF NOT EXISTS `MyUser` (
  `UserID` int(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `UserName` varchar(30) DEFAULT NULL COMMENT '',
  `Power` varchar(30) DEFAULT NULL COMMENT '',
  `Detail` varchar(50) DEFAULT NULL COMMENT '',
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='';

--
--
--
CREATE TABLE IF NOT EXISTS `NodeInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `Name` varchar(30) NOT NULL COMMENT '',
  `Json` text COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NodeInfo_Name_uindex` (`Name`),
  UNIQUE KEY `NodeInfo_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='';


--
-- 分发日志表
--
CREATE TABLE IF NOT EXISTS `OUTGIVINGLOG` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `OUTGIVINGID` varchar(50) DEFAULT NULL COMMENT '分发id',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态',
  `STARTTIME` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `ENDTIME` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `RESULT` longtext COMMENT '消息',
  `NODEID` varchar(100) DEFAULT NULL COMMENT '节点id',
  `PROJECTID` varchar(100) DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分发日志';

--
-- 仓库信息表
--
CREATE TABLE IF NOT EXISTS `REPOSITORY` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `CREATETIMEMILLIS` bigint(20) DEFAULT NULL COMMENT '数据创建时间',
  `MODIFYTIMEMILLIS` bigint(20) DEFAULT NULL COMMENT '数据修改时间',
  `NAME` varchar(50) DEFAULT NULL COMMENT '仓库名称',
  `GITURL` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `REPOTYPE` int(11) DEFAULT NULL COMMENT '仓库类型{0: GIT, 1: SVN}',
  `PROTOCOL` int(11) DEFAULT NULL COMMENT '拉取代码的协议{0: http, 1: ssh}',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '登录用户',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `RSAPUB` varchar(2048) DEFAULT NULL COMMENT 'SSH RSA 公钥',
  `RSAPRV` varchar(4096) DEFAULT NULL COMMENT 'SSH RSA 私钥',
  `STRIKE` int(11) DEFAULT '0' COMMENT '逻辑删除{1，删除，0 未删除(默认)}',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库信息';


--
-- ssh 终端操作记录表
--
CREATE TABLE IF NOT EXISTS `SSHTERMINALEXECUTELOG` (
  `ID` varchar(50) NOT NULL COMMENT 'ID',
  `IP` varchar(30) DEFAULT NULL COMMENT '客户端IP地址',
  `USERID` varchar(30) DEFAULT NULL COMMENT '操作的用户ID',
  `OPTTIME` bigint(20) DEFAULT NULL COMMENT '操作时间',
  `USERAGENT` varchar(300) DEFAULT NULL COMMENT '浏览器标识',
  `COMMANDS` varchar(500) DEFAULT NULL COMMENT '操作的命令',
  `SSHID` varchar(50) DEFAULT NULL COMMENT '操作的sshid',
  `SSHNAME` varchar(50) DEFAULT NULL COMMENT '操作的ssh name',
  `REFUSE` int(11) DEFAULT NULL COMMENT '拒绝执行',
  `CREATETIMEMILLIS` bigint(20) DEFAULT NULL COMMENT '数据创建时间',
  `MODIFYTIMEMILLIS` bigint(20) DEFAULT NULL COMMENT '数据修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ssh 终端操作记录';

--
-- 系统监控记录表
--
CREATE TABLE IF NOT EXISTS `SYSTEMMONITORLOG` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `NODEID` varchar(100) DEFAULT NULL COMMENT '节点id',
  `MONITORTIME` bigint(20) DEFAULT NULL COMMENT '监控时间',
  `OCCUPYCPU` double DEFAULT NULL COMMENT '占用cpu',
  `OCCUPYMEMORY` double DEFAULT NULL COMMENT '占用内存',
  `OCCUPYDISK` double DEFAULT NULL COMMENT '占用磁盘',
  `OCCUPYMEMORYUSED` double DEFAULT NULL COMMENT '占用内存 (使用)',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYSTEMMONITORLOG_INDEX1` (`NODEID`,`MONITORTIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统监控记录';


--
-- 源配置信息表
--
CREATE TABLE IF NOT EXISTS `SourceConfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `os_version` varchar(30) DEFAULT NULL COMMENT '系统版本',
  `arch` varchar(10) DEFAULT NULL COMMENT '系统构架',
  `package_type` varchar(20) DEFAULT NULL COMMENT '包类型',
  `uri` text COMMENT '类地址',
  `codename` text COMMENT '版本代号',
  `components` text COMMENT '其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='源配置信息';


--
-- 操作日志表
--
CREATE TABLE IF NOT EXISTS `USEROPERATELOGV1` (
  `REQID` varchar(50) NOT NULL COMMENT '请求ID',
  `IP` varchar(30) DEFAULT NULL COMMENT '客户端IP地址',
  `USERID` varchar(30) DEFAULT NULL COMMENT '操作的用户ID',
  `RESULTMSG` longtext COMMENT '操作的结果信息',
  `OPTTYPE` int(11) DEFAULT NULL COMMENT '操作类型',
  `OPTSTATUS` int(11) DEFAULT NULL COMMENT '操作状态 成功/失败',
  `OPTTIME` bigint(20) DEFAULT NULL COMMENT '操作时间',
  `NODEID` varchar(30) DEFAULT NULL COMMENT '节点ID',
  `DATAID` varchar(50) DEFAULT NULL COMMENT '操作的数据ID',
  `USERAGENT` varchar(300) DEFAULT NULL COMMENT '浏览器标识',
  `REQDATA` longtext COMMENT '用户请求参数',
  PRIMARY KEY (`REQID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

--
-- 用户信息表
--
CREATE TABLE IF NOT EXISTS `UserInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` text COMMENT '',
  `lock_time` int(11) DEFAULT NULL COMMENT '',
  `modify_time` text COMMENT '',
  `password` text COMMENT '',
  `system_user` tinyint(1) DEFAULT NULL COMMENT '',
  `last_pwd_error_time` int(11) DEFAULT NULL COMMENT '',
  `user_md5_key` text COMMENT '',
  `pwd_error_count` int(11) DEFAULT NULL COMMENT '',
  `parent` text COMMENT '',
  `demo_user` tinyint(1) DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UserInfo_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息';

--
--
--
CREATE TABLE IF NOT EXISTS `UserInfo2` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `json` text COMMENT '',
  `name` varchar(30) NOT NULL COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UserInfo2_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='';

--
-- 用户角色信息表
--
CREATE TABLE IF NOT EXISTS `UserRolesInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` text NOT NULL COMMENT '',
  `json` text NOT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='用户角色信息';

--
-- 策略文件信息表
--
CREATE TABLE IF NOT EXISTS `Project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` text NOT NULL COMMENT '',
  `json` text NOT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='策略文件信息';

--
-- 源配置方案表
--
CREATE TABLE IF NOT EXISTS `SourcePlan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `source_list` text NOT NULL COMMENT '',
  `plan_name` text NOT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='源配置方案';

--
-- 软件包信息表
--
CREATE TABLE IF NOT EXISTS `PackageInfo` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '',
    `Package` varchar(100) NOT NULL COMMENT '',
    `Version` varchar(100) NOT NULL COMMENT '',
    `classification` varchar(100) NOT NULL COMMENT '',
    `Architecture` varchar(100) NOT NULL COMMENT '',
    `model_name` varchar(100) NOT NULL COMMENT '',
    PRIMARY KEY (`id`,model_name)
    ) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT=''
    PARTITION BY KEY(model_name)
    PARTITIONS 127;

