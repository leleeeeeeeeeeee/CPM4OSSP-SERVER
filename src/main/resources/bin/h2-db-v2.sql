--
-
-- add new tables for storage build info, repo info...

-- 仓库信息
CREATE TABLE IF NOT EXISTS REPOSITORY
(
    ID               VARCHAR(50) not null comment 'id',
    CREATETIMEMILLIS BIGINT COMMENT '数据创建时间',
    MODIFYTIMEMILLIS BIGINT COMMENT '数据修改时间',
    `NAME`           VARCHAR(50) comment '仓库名称',
    GITURL           varchar(255) comment '仓库地址',
    REPOTYPE         int comment '仓库类型{0: GIT, 1: SVN}',
    PROTOCOL         int comment '拉取代码的协议{0: http, 1: ssh}',
    USERNAME         VARCHAR(50) comment '登录用户',
    PASSWORD         VARCHAR(50) comment '登录密码',
    RSAPUB           VARCHAR(2048) comment 'SSH RSA 公钥',
    RSAPRV           VARCHAR(4096) comment 'SSH RSA 私钥',
    STRIKE           int DEFAULT 0 comment '逻辑删除{1，删除，0 未删除(默认)}',
    CONSTRAINT REPOSITORY_PK PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT= '仓库信息';

-- 构建信息
CREATE TABLE IF NOT EXISTS BUILD_INFO
(
    ID                  VARCHAR(50) not null comment 'id',
    REPOSITORYID        VARCHAR(50) not null comment '仓库 ID',
    CREATETIMEMILLIS    BIGINT COMMENT '数据创建时间',
    MODIFYTIMEMILLIS    BIGINT COMMENT '数据修改时间',
    `NAME`              VARCHAR(50) comment '构建名称',
    BUILDID             int comment '构建 ID',
    `GROUP`             VARCHAR(50) comment '分组名称',
    BRANCHNAME          VARCHAR(50) comment '分支',
    SCRIPT              VARCHAR(200) comment '构建命令',
    RESULTDIRFILE       VARCHAR(50) comment '构建产物目录',
    RELEASEMETHOD       int comment '发布方法{0: 不发布, 1: 节点分发, 2: 分发项目, 3: SSH}',
    MODIFYUSER          VARCHAR(50) comment '修改人',
    `STATUS`            int comment '状态',
    TRIGGERTOKEN        VARCHAR(20) comment '触发器token',
    EXTRADATA           longtext comment '额外信息，JSON 字符串格式',
    RELEASEMETHODDATAID VARCHAR(200) comment '构建关联的数据ID',
    BRANCHTAGNAME VARCHAR(50) comment '标签',
    CONSTRAINT BUILD_INFO_PK PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT= '构建信息';



