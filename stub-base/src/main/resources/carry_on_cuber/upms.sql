-- 用户表
CREATE TABLE t_user
(
	id  					    VARCHAR(64) NOT NULL PRIMARY KEY,
	user_name 				VARCHAR(256) NOT NULL,
	nick_name				  VARCHAR(256),
	password 				  VARCHAR(1024) NOT NULL,
	email 					  VARCHAR(256),
	mobile 					  VARCHAR(256),
	sex 					    VARCHAR(64),
	avatar 					  VARCHAR(1024),
	wx_open_id 				VARCHAR(256),
	ding_open_id			VARCHAR(256),
	domain 					  VARCHAR(256) NOT NULL,
	is_sys					  VARCHAR(2),
	last_login_id			VARCHAR(64),
	status          	VARCHAR(2),
	update_user_id    VARCHAR(64),
	create_user_id    VARCHAR(64),
	update_time     	TIMESTAMP WITHOUT TIME ZONE,
	create_time     	TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_USER_NAME_DOMAIN ON t_user (user_name,domain);

comment on table  t_user 					        is '用户表';
comment on column t_user.id 				      is '主键';
comment on column t_user.user_name 			  is '用户名';
comment on column t_user.nick_name 			  is '用户昵称';
comment on column t_user.password 			  is '用户密码';
comment on column t_user.email 				    is '用户邮箱';
comment on column t_user.mobile 			    is '用户手机';
comment on column t_user.sex 				      is '用户性别';
comment on column t_user.avatar 			    is '用户头像';
comment on column t_user.wx_open_id 		  is '微信公开ID';
comment on column t_user.ding_open_id 		is 'ding的用户号';
comment on column t_user.domain 			    is '所属domain';
comment on column t_user.is_sys 			    is '系统内置';
comment on column t_user.last_login_id 		is '最后一次登录ID';
comment on column t_user.status 			    is '用户状态 0/1/2/9 初始化/有效/无效/审核';
comment on column t_user.update_user_id 	is '更新操作员';
comment on column t_user.create_user_id 	is '创建操作员';
comment on column t_user.update_time 		  is '更新时间';
comment on column t_user.create_time 		  is '创建时间';

--登录日志
CREATE TABLE t_login_log
(
	id  					    VARCHAR(64) NOT NULL PRIMARY KEY,
	trace_id				  VARCHAR(64),
	login_type				VARCHAR(256),
	user_id					  VARCHAR(256),
	device					  VARCHAR(256),
	login_ip				  VARCHAR(256),
	user_agent        text,
	login_result			VARCHAR(64),
	fail_reason				text,
	logout_time			  TIMESTAMP WITHOUT TIME ZONE,
	login_time     		TIMESTAMP WITHOUT TIME ZONE,
	create_time     	TIMESTAMP WITHOUT TIME ZONE
);
CREATE INDEX I_LOGIN_USER_ID ON t_login_log (user_id);

comment on table  t_login_log 					      is '登陆日志表';
comment on column t_login_log.id 				      is '主键';
comment on column t_login_log.trace_id 			  is '轨迹ID';
comment on column t_login_log.login_type 			is '登陆类型 password/phone/email/wechat/ding';
comment on column t_login_log.user_id 			  is '用户ID';
comment on column t_login_log.device 				  is '设备类型';
comment on column t_login_log.login_ip 		    is '登陆IP';
comment on column t_login_log.user_agent 			is '用户的agent';
comment on column t_login_log.login_result 		is '登陆结果 1/2/9/r  成功/失败/异常/风控拦截';
comment on column t_login_log.fail_reason 		is '错误原因';
comment on column t_login_log.logout_time 		is '登出时间';
comment on column t_login_log.login_time 	    is '登陆时间';
comment on column t_login_log.create_time 		is '创建时间';

CREATE TABLE t_operate_log
(
	id  					  VARCHAR(64) NOT NULL PRIMARY KEY,
	login_id				VARCHAR(64),
	trace_id				VARCHAR(256),
	user_id					VARCHAR(256),
	app						  VARCHAR(256),
	host_name				VARCHAR(256),
	action					VARCHAR(256),
	url						  VARCHAR(256),
	content					text,
	operate_result	VARCHAR(64),
	complete_time		TIMESTAMP WITHOUT TIME ZONE,
	update_time     TIMESTAMP WITHOUT TIME ZONE,
	create_time     TIMESTAMP WITHOUT TIME ZONE
);
CREATE INDEX I_OPER_LOGIN_ID ON t_operate_log (login_id);

comment on table  t_operate_log 					        is '操作日志表';
comment on column t_operate_log.id 				        is '主键';
comment on column t_operate_log.login_id 			    is '登录ID';
comment on column t_operate_log.trace_id 			    is '轨迹ID';
comment on column t_operate_log.user_id 			    is '用户ID';
comment on column t_operate_log.app 				      is '项目名';
comment on column t_operate_log.host_name 		    is '主机名';
comment on column t_operate_log.action 			      is '动作名';
comment on column t_operate_log.url 				      is '操作的url';
comment on column t_operate_log.content 			    is '操作内容';
comment on column t_operate_log.operate_result 		is '操作日志,1/2/9  成功/失败/异常';
comment on column t_operate_log.complete_time 		is '完成时间';
comment on column t_operate_log.update_time 		  is '更新时间';
comment on column t_operate_log.create_time 		  is '创建时间';

CREATE TABLE t_user_group
(
	id  					    VARCHAR(64) NOT NULL PRIMARY KEY,
	group_name 				VARCHAR(256) NOT NULL,
	group_desc				VARCHAR(1024),
	group_type        VARCHAR(256),
	domain				    VARCHAR(256),
	is_sys					  VARCHAR(2),
	status          	VARCHAR(2),
	update_user_id    VARCHAR(64),
	create_user_id    VARCHAR(64),
	update_time     	TIMESTAMP WITHOUT TIME ZONE,
	create_time     	TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_GROUP_NAME_DOMAIN ON t_user_group (group_name,domain);

comment on table  t_user_group                  is '用户组';
comment on column t_user_group.id               is '主键';
comment on column t_user_group.group_name       is '组名';
comment on column t_user_group.group_desc       is '组描述';
comment on column t_user_group.group_type       is '组类型 role/report  角色组/报警组';
comment on column t_user_group.domain           is '所属domain';
comment on column t_user_group.is_sys           is '是否系统内置';
comment on column t_user_group.status           is '用户状态 0/1/2/9 初始化/有效/无效/审核';
comment on column t_user_group.update_user_id   is '更新操作员';
comment on column t_user_group.create_user_id   is '创建操作员';
comment on column t_user_group.update_time      is '更新时间';
comment on column t_user_group.create_time      is '创建时间';

CREATE TABLE t_role
(
	id  					    VARCHAR(64) NOT NULL PRIMARY KEY,
	role_name 				VARCHAR(256) NOT NULL,
	role_desc				  VARCHAR(1024),
	role_type				  VARCHAR(256),
	domain 			      VARCHAR(256) NOT NULL,
	is_sys					  VARCHAR(2),
	status          	VARCHAR(2),
	update_user_id    VARCHAR(64),
	create_user_id    VARCHAR(64),
	update_time     	TIMESTAMP WITHOUT TIME ZONE,
	create_time     	TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_ROLE_NAME_DOMAIN ON t_role (role_name,domain);

comment on table  t_role                  is '权限表';
comment on column t_role.id               is '主键';
comment on column t_role.role_name        is '权限名';
comment on column t_role.role_desc        is '权限描述';
comment on column t_role.role_type        is '权限类型 permission/workflow 权限角色/流程角色';
comment on column t_role.domain           is '所属domain';
comment on column t_role.is_sys           is '是否系统内置';
comment on column t_role.status           is '用户状态 0/1/2/9 初始化/有效/无效/审核';
comment on column t_role.update_user_id   is '更新操作员';
comment on column t_role.create_user_id   is '创建操作员';
comment on column t_role.update_time      is '更新时间';
comment on column t_role.create_time      is '创建时间';

CREATE TABLE t_permission
(
	id  					        VARCHAR(64) NOT NULL PRIMARY KEY,
	permission_name 			VARCHAR(256) NOT NULL,
	permission_desc				VARCHAR(1024),
	permission_type		    VARCHAR(256),
	authorize_obj_id      VARCHAR(64),
	domain 			          VARCHAR(256) NOT NULL,
	is_sys					      VARCHAR(2),
	status          	    VARCHAR(2),
	update_user_id        VARCHAR(64),
	create_user_id        VARCHAR(64),
	update_time     	    TIMESTAMP WITHOUT TIME ZONE,
	create_time     	    TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_PERMISSION_NAME_DOMAIN ON t_permission (permission_name,domain);

comment on table  t_permission                    is '权限表';
comment on column t_permission.id                 is '主键';
comment on column t_permission.permission_name    is '权限名';
comment on column t_permission.permission_desc    is '权限描述';
comment on column t_permission.permission_type    is '权限类型 menu/operate/element';
comment on column t_permission.authorize_obj_id   is '授权对象ID';
comment on column t_permission.domain             is '所属domain';
comment on column t_permission.is_sys             is '是否系统内置';
comment on column t_permission.status             is '用户状态 0/1/2/9 初始化/有效/无效/审核';
comment on column t_permission.update_user_id     is '更新操作员';
comment on column t_permission.create_user_id     is '创建操作员';
comment on column t_permission.update_time        is '更新时间';
comment on column t_permission.create_time        is '创建时间';


CREATE TABLE t_menu
(
	id  					        VARCHAR(64) NOT NULL PRIMARY KEY,
	menu_name 				    VARCHAR(256) NOT NULL,
	menu_desc				      VARCHAR(1024),
	menu_type             VARCHAR(256),
	parent_id       		  VARCHAR(64),
	menu_url				      VARCHAR(256),
	menu_icon				      VARCHAR(256) NOT NULL,
	menu_display_en			  VARCHAR(256),
	menu_display_zh			  VARCHAR(256),
	i18n_key				      VARCHAR(256),
	tree_sort				      SMALLINT NOT NULL,
	tree_leaf				      VARCHAR(2),
	tree_level 				    SMALLINT NOT NULL,
	domain 			          VARCHAR(256) NOT NULL,
	is_sys					      VARCHAR(2),
	status          		  VARCHAR(2),
	update_user_id        VARCHAR(64),
	create_user_id        VARCHAR(64),
	update_time     		  TIMESTAMP WITHOUT TIME ZONE,
	create_time     		  TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_MENU_NAME_DOMAIN ON t_menu (menu_name,domain);
CREATE INDEX I_PARENT_ID ON t_menu (parent_id);

comment on table  t_menu                    is '菜单表';
comment on column t_menu.id                 is '主键';
comment on column t_menu.menu_name          is '菜单名';
comment on column t_menu.menu_desc          is '菜单描述';
comment on column t_menu.menu_type          is '菜单类型  menu/element 菜单/页面元素';
comment on column t_menu.parent_id          is '父菜单ID';
comment on column t_menu.menu_url           is '菜单URL';
comment on column t_menu.menu_icon          is '菜单ICON';
comment on column t_menu.menu_display_en    is '菜单英文名';
comment on column t_menu.menu_display_zh    is '菜单中文名';
comment on column t_menu.i18n_key           is '菜单i18n_key';
comment on column t_menu.tree_sort          is '菜单次序';
comment on column t_menu.tree_leaf          is '是否子菜单 Y/N 是否子菜单';
comment on column t_menu.tree_level         is '菜单层级';
comment on column t_menu.domain             is '所属domain';
comment on column t_menu.is_sys             is '是否系统内置';
comment on column t_menu.status             is '用户状态 0/1/2/9 初始化/有效/无效/审核';
comment on column t_menu.update_user_id     is '更新操作员';
comment on column t_menu.create_user_id     is '创建操作员';
comment on column t_menu.update_time        is '更新时间';
comment on column t_menu.create_time        is '创建时间';

CREATE TABLE t_operate
(
	id  					        VARCHAR(64) NOT NULL PRIMARY KEY,
	operate_name 			    VARCHAR(256) NOT NULL,
	operate_desc				  VARCHAR(1024),
	operate_type		      VARCHAR(256),
	domain 			          VARCHAR(256) NOT NULL,
	is_sys					      VARCHAR(2),
	status          	    VARCHAR(2),
	update_user_id        VARCHAR(64),
	create_user_id        VARCHAR(64),
	update_time     	    TIMESTAMP WITHOUT TIME ZONE,
	create_time     	    TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_OPERATE_NAME_DOMAIN ON t_operate (operate_name,domain);

comment on table  t_operate                    is '操作表';
comment on column t_operate.id                 is '主键';
comment on column t_operate.operate_name       is '操作名';
comment on column t_operate.operate_desc       is '操作描述';
comment on column t_operate.operate_type       is '操作类型 todo';
comment on column t_operate.domain             is '所属domain';
comment on column t_operate.is_sys             is '是否系统内置';
comment on column t_operate.status             is '用户状态 0/1/2/9 初始化/有效/无效/审核';
comment on column t_operate.update_user_id     is '更新操作员';
comment on column t_operate.create_user_id     is '创建操作员';
comment on column t_operate.update_time        is '更新时间';
comment on column t_operate.create_time        is '创建时间';

CREATE TABLE tr_user_group
(
  id  					        VARCHAR(64) NOT NULL PRIMARY KEY,
	group_id 	            VARCHAR(64) NOT NULL,
	user_id 	            VARCHAR(64) NOT NULL,
	connect_time     	    TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_TR_USER_GROUP ON tr_user_group (group_id,user_id);
CREATE INDEX I_TR_USER_ID ON tr_user_group (user_id);

comment on table  tr_user_group                    is '用户和用户组关联表';
comment on column tr_user_group.id                 is '主键';
comment on column tr_user_group.group_id           is '组ID';
comment on column tr_user_group.user_id            is '用户ID';
comment on column tr_user_group.connect_time       is '关联时间';

CREATE TABLE tr_user_role
(
  id  					        VARCHAR(64) NOT NULL PRIMARY KEY,
	role_id 	            VARCHAR(64) NOT NULL,
	connect_type          VARCHAR(256) NOT NULL,
	sbj_user_id 	        VARCHAR(64) NOT NULL,
	connect_time     	    TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX I_TR_SBJ_USER_ID ON tr_user_role (sbj_user_id);
CREATE INDEX I_TR_ROLE_ID ON tr_user_role (role_id);


comment on table  tr_user_role                    is '用户和用户组关联表';
comment on column tr_user_role.id                 is '主键';
comment on column tr_user_role.role_id            is '角色ID';
comment on column tr_user_role.connect_type       is '关联类型 user/group  用户关联/用户组关联';
comment on column tr_user_role.sbj_user_id        is '关联的subject id';
comment on column tr_user_role.connect_time       is '关联时间';

CREATE TABLE tr_role_permission
(
  id  					        VARCHAR(64) NOT NULL PRIMARY KEY,
	role_id 	            VARCHAR(64) NOT NULL,
	permission_id 	      VARCHAR(64) NOT NULL,
	connect_time     	    TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_TR_ROLE_PERMISSION ON tr_role_permission (role_id,permission_id);
CREATE INDEX I_TR_ROLE_ID_P ON tr_role_permission (role_id);

comment on table  tr_role_permission                    is '用户和用户组关联表';
comment on column tr_role_permission.id                 is '主键';
comment on column tr_role_permission.role_id            is '角色ID';
comment on column tr_role_permission.permission_id      is '权限ID';
comment on column tr_role_permission.connect_time       is '关联时间';