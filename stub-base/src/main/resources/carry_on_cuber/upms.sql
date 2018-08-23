-- 用户表
CREATE TABLE t_user
(
	id  					    VARCHAR(64) NOT NULL PRIMARY KEY,
	user_name 				VARCHAR(256) NOT NULL,
	nick_name				  VARCHAR(256),
	password 				  VARCHAR(1024) NOT NULL,
	pay_password      VARCHAR(1024) NOT NULL,
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
	operator        	VARCHAR(256),
	update_time     	TIMESTAMP WITHOUT TIME ZONE,
	create_time     	TIMESTAMP WITHOUT TIME ZONE
);

CREATE UNIQUE INDEX I_USER_NAME_DOMAIN ON t_user (user_name,domain);

comment on table t_user 					        is '用户表';
comment on column t_user.id 				      is '主键';
comment on column t_user.user_name 			  is '用户名';
comment on column t_user.nick_name 			  is '用户昵称';
comment on column t_user.password 			  is '用户密码';
comment on column t_user.pay_password 		is '用户支付密码...以后估计不会放这';
comment on column t_user.email 				    is '用户邮箱';
comment on column t_user.mobile 			    is '用户手机';
comment on column t_user.sex 				      is '用户性别';
comment on column t_user.avatar 			    is '用户头像';
comment on column t_user.wx_open_id 		  is '微信公开ID';
comment on column t_user.ding_open_id 		is 'ding的用户号';
comment on column t_user.domain 			    is '所属domain';
comment on column t_user.is_sys 			    is '系统内置';
comment on column t_user.last_login_id 		is '最后一次登录ID';
comment on column t_user.status 			    is '用户状态';
comment on column t_user.operator 			  is '操作员';
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

comment on table t_login_log 					        is '登陆日志表';
comment on column t_login_log.id 				      is '主键';
comment on column t_login_log.trace_id 			  is '轨迹ID';
comment on column t_login_log.login_type 			is '登陆类型 password/phone/email/wechat/ding';
comment on column t_login_log.user_id 			  is '用户ID';
comment on column t_login_log.device 				  is '设备类型';
comment on column t_login_log.login_ip 		    is '登陆IP';
comment on column t_login_log.user_agent 			is '用户的agent类型';
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
	biz_status			VARCHAR(64),
	complete_time		TIMESTAMP WITHOUT TIME ZONE,
	update_time     TIMESTAMP WITHOUT TIME ZONE,
	create_time     TIMESTAMP WITHOUT TIME ZONE
);
CREATE INDEX I_OPER_LOGIN_ID ON t_operate_log (login_id);

comment on table t_operate_log 					          is '操作日志表';
comment on column t_operate_log.id 				        is '主键';
comment on column t_operate_log.login_id 			    is '登录ID';
comment on column t_operate_log.trace_id 			    is '轨迹ID';
comment on column t_operate_log.user_id 			    is '用户ID';
comment on column t_operate_log.app 				      is '项目名';
comment on column t_operate_log.host_name 		    is '主机名';
comment on column t_operate_log.action 			      is '动作名';
comment on column t_operate_log.url 				      is '操作的url';
comment on column t_operate_log.content 			    is '操作内容';
comment on column t_operate_log.biz_status 		    is '操作日志,1/2/9  成功/失败/异常';
comment on column t_operate_log.complete_time 		is '完成时间';
comment on column t_operate_log.update_time 		  is '更新时间';
comment on column t_operate_log.create_time 		  is '创建时间';




-- 权限表
CREATE TABLE t_role
(
	id  					INTEGER NOT NULL PRIMARY KEY,	
	role_name 				VARCHAR(256) NOT NULL,
	role_alias				VARCHAR(256),
	role_type				VARCHAR(256),
	upms_domain 			VARCHAR(256) NOT NULL,
	is_sys					VARCHAR(2),
	status          		VARCHAR(2),
	operator        		VARCHAR(256),
	update_time     		TIMESTAMP WITHOUT TIME ZONE,
	create_time     		TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_role increment by 1 minvalue 1 no maxvalue start with 1 CACHE  20;

CREATE UNIQUE INDEX idx_name_role ON t_role (role_name);

comment on table t_role is '权限表';
comment on column t_role.role_name is '权限名';
comment on column t_role.role_alias is '权限别名';
comment on column t_role.role_type is '权限类型';
comment on column t_role.upms_domain is '所属domain';
comment on column t_role.is_sys is '是否系统内置';

CREATE TABLE t_menu
(
	id  					INTEGER NOT NULL PRIMARY KEY,	
	menu_name 				VARCHAR(256) NOT NULL,
	menu_alias				VARCHAR(256),
	parent_menu_name		VARCHAR(256),
	menu_url				VARCHAR(256),
	menu_icon				VARCHAR(256) NOT NULL,
	menu_display_en			VARCHAR(256),
	menu_display_zh			VARCHAR(256),
	i18n_key				VARCHAR(256),
	tree_sort				SMALLINT NOT NULL,
	tree_leaf				VARCHAR(2),
	tree_level 				SMALLINT NOT NULL,
	menu_type				VARCHAR(256),
	upms_domain 			VARCHAR(256) NOT NULL,
	is_sys					VARCHAR(2),
	status          		VARCHAR(2),
	operator        		VARCHAR(256),
	update_time     		TIMESTAMP WITHOUT TIME ZONE,
	create_time     		TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_menu increment by 1 minvalue 1 no maxvalue start with 1 CACHE  20;

CREATE UNIQUE INDEX idx_name_menu ON t_menu (menu_name);
CREATE INDEX idx_parent_name_menu ON t_menu (parent_menu_name);

comment on table t_menu is '菜单表';
comment on column t_menu.menu_name is '菜单名';
comment on column t_menu.menu_alias is '菜单别名';
comment on column t_menu.parent_menu_name is '父菜单名';
comment on column t_menu.menu_url is '菜单URL';
comment on column t_menu.menu_icon is '菜单ICON';
comment on column t_menu.menu_display_en is '菜单英文名';
comment on column t_menu.menu_display_zh is '菜单中文名';
comment on column t_menu.i18n_key is '菜单i18n_key';
comment on column t_menu.tree_sort is '菜单次序';
comment on column t_menu.tree_leaf is '是否子菜单';
comment on column t_menu.tree_level is '菜单层级';
comment on column t_menu.menu_type is '菜单类型';
comment on column t_menu.upms_domain is '菜单所属';
comment on column t_menu.is_sys is '是否系统内置';

CREATE TABLE t_menu_role
(
	role_name 	VARCHAR(128) NOT NULL,
	menu_name 	VARCHAR(128) NOT NULL,
	PRIMARY KEY (role_name, menu_name)
); 

comment on table t_menu_role is '权限菜单映射表';
comment on column t_menu_role.menu_name is '菜单名';
comment on column t_menu_role.role_name is '权限名';

CREATE TABLE t_user_role
(
	user_name 	VARCHAR(128) NOT NULL,
	role_name 	VARCHAR(128) NOT NULL,
	PRIMARY KEY (user_name,role_name)
); 
comment on table t_user_role is '权限用户映射表';
comment on column t_user_role.user_name is '用户名';
comment on column t_user_role.role_name is '权限名';

