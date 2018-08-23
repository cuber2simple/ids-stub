--授权类型
INSERT INTO t_sys_dict_type (id, dict_name, zh_name, dict_type, is_sys, remarks, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_type'),'oauth2.grant_type','oauth2授权类型','oauth2','Y','oauth2授权类型','1','system',now(),now());
																																												
INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'oauth2.grant_type', 'oauth2.grant_type.authorization_code', NULL, 1, 'Y', 1,'oauth2','authorization_code', '授权码模式(即先登录获取code,再获取token)','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'oauth2.grant_type', 'oauth2.grant_type.password', NULL, 2, 'Y', 1,'oauth2','password', '密码模式(将用户名,密码传过去,直接获取token)','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'oauth2.grant_type', 'oauth2.grant_type.client_credentials', NULL, 3, 'Y', 1,'oauth2','client_credentials', '客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向’服务端’获取资源)','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'oauth2.grant_type', 'oauth2.grant_type.implicit', NULL, 4, 'Y', 1,'oauth2','implicit', '简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'oauth2.grant_type', 'oauth2.grant_type.refresh_token', NULL, 5, 'Y', 1,'oauth2','refresh_token', '刷新access_token','system',now(),now());

--配置的status
INSERT INTO t_sys_dict_type (id, dict_name, zh_name, dict_type, is_sys, remarks, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_type'),'config.status','一般配置表中的状态','config','Y','一般配置表中的状态','1','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'config.status', 'config.status.0', NULL, 1, 'Y', 1,'config','0', '无效状态','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'config.status', 'config.status.1', NULL, 2, 'Y', 1,'config','1', '有效状态','system',now(),now());

--全局boolean
INSERT INTO t_sys_dict_type (id, dict_name, zh_name, dict_type, is_sys, remarks, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_type'),'config.boolean','一般配置的布尔类型','config','Y','一般配置的布尔类型','1','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'config.boolean', 'config.boolean.Y', NULL, 1, 'Y', 1,'config','0', 'true','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'config.boolean', 'config.boolean.N', NULL, 2, 'Y', 1,'config','1', 'false','system',now(),now());

--client_scope
INSERT INTO t_sys_dict_type (id, dict_name, zh_name, dict_type, is_sys, remarks, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_type'),'oauth2.client.scope','oauth2 client 访问权限','oauth2','Y','oauth2 client 访问权限','1','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'oauth2.client.scope', 'coauth2.client.scope.read', NULL, 1, 'Y', 1,'config','read', '可读','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'oauth2.client.scope', 'coauth2.client.scope.write', NULL, 2, 'Y', 1,'config','write', '可写','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'oauth2.client.scope', 'coauth2.client.scope.trust', NULL, 3, 'Y', 1,'config','trust', '可信任','system',now(),now());

--币种的使用范围
INSERT INTO t_sys_dict_type (id, dict_name, zh_name, dict_type, is_sys, remarks, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_type'),'currency.scope','币种的使用范围','currency','Y','币种的使用范围','1','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'currency.scope', 'currency.scope.sale', NULL, 1, 'Y', 1,'config','sale', '交易的币种','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'currency.scope', 'currency.scope.settle', NULL, 2, 'Y', 1,'config','settle', '结算币种','system',now(),now());


--用户的domain
INSERT INTO t_sys_dict_type (id, dict_name, zh_name, dict_type, is_sys, remarks, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_type'),'umps.domain','用户的权限','umps','Y','umps.domain','1','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.domain', 'umps.domain.poss', NULL, 1, 'Y', 1,'umps','poss', '内部操作用户','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.domain', 'umps.domain.mps', NULL, 2, 'Y', 1,'umps','mps', '商户用户','system',now(),now());

--用户性别
INSERT INTO t_sys_dict_type (id, dict_name, zh_name, dict_type, is_sys, remarks, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_type'),'user.sex','用户性别','user','Y','user.sex','1','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'user.sex', 'user.sex.male', NULL, 1, 'Y', 1,'user','male', '男性','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'user.sex', 'user.sex.female', NULL, 2, 'Y', 1,'user','female', '女性','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'user.sex', 'user.sex.unknown', NULL, 3, 'Y', 1,'user','unknown', '未知','system',now(),now());

--权限的类型
INSERT INTO t_sys_dict_type (id, dict_name, zh_name, dict_type, is_sys, remarks, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_type'),'umps.role_type','权限类型','umps','Y','umps.role_type','1','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type', 'umps.role_type.user', NULL, 1, 'N', 1,'role_scope','user', '系统使用层','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type', 'umps.role_type.operator', NULL, 2, 'N', 1,'role_scope','operator', '使用使用层','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type', 'umps.role_type.mps', NULL, 3, 'N', 1,'role_scope','mps', 'mps使用者','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type', 'umps.role_type.senstive', NULL, 4, 'N', 1,'role_scope','senstive', '敏感信息权限','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.user', 'umps.role_type.user.root', NULL, 1, 'Y', 1,'role.user','ROLE_ROOT', '超级管理员','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.user', 'umps.role_type.user.admin', NULL, 2, 'Y', 1,'role.user','ROLE_ADMIN', '管理员','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.user', 'umps.role_type.user.user', NULL, 3, 'Y', 1,'role.user','ROLE_USER', '系统用户','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.user', 'umps.role_type.user.notify', NULL, 4, 'Y', 1,'role.user','ROLE_NOTIFY', '系统报警人员','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.operator', 'umps.role_type.operator.watch', NULL, 1, 'Y', 1,'role.operator','ROLE_WATCHER', '查看者','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.operator', 'umps.role_type.operator.work', NULL, 2, 'Y', 1,'role.operator','ROLE_WORKER', '工作者','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.operator', 'umps.role_type.operator.audit', NULL, 3, 'Y', 1,'role.operator','ROLE_AUDITER', '审核者','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.mps', 'umps.role_type.mps.admin', NULL, 1, 'Y', 1,'role.mps','ROLE_MPS_ADMIN', 'MPS系统管理员','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.mps', 'umps.role_type.mps.audit', NULL, 2, 'Y', 1,'role.mps','ROLE_MPS_AUDIT', 'MPS审计人员','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.mps', 'umps.role_type.mps.dev', NULL, 3, 'Y', 1,'role.mps','ROLE_MPS_DEV', 'MPS开发人员','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.senstive', 'umps.role_type.senstive.card', NULL, 1, 'Y', 1,'role.senstive','ROLE_SENSTIVE_CARD', '卡号敏感信息','system',now(),now());

INSERT INTO t_dict_data (id, dict_name, dict_code, parent_code, tree_sort, tree_leaf, tree_level, dict_label, dict_value, remarks,operator, update_time,create_time) VALUES(NEXTVAL('seq_dict_data'),'umps.role_type.senstive', 'umps.role_type.senstive.customer', NULL, 2, 'Y', 1,'role.senstive','ROLE_SENSTIVE_CUSTOMER', '客户敏感信息','system',now(),now());
