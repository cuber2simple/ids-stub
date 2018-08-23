--初始化一个系统管理员 初始化密码为root
INSERT INTO t_user (id, user_name, nick_name, password, email, mobile, sex, avatar, wx_open_id, ding_open_id, upms_domain, pwd_question, pwd_question_answer, pwd_question_2, pwd_question_answer_2, pwd_question_3, pwd_question_answer_3, is_sys, last_login_id, status, operator, update_time,create_time) 
VALUES(NEXTVAL('seq_user'),'root','root','4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2','root@ipaylinks.com','08613888888888','unknown', null, null, null, 'poss', null, null, null, null, null, null, 'Y', null, '1','system',now(),now());

INSERT INTO t_role (id, role_name, role_alias, role_type, upms_domain, is_sys, status, operator, update_time,create_time) VALUES(NEXTVAL('seq_role'),'ROLE_ROOT','超级管理员','ROLE_ROOT', 'poss','Y','1','system',now(),now());


--主页
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'index','index', null, '/index', 'fa-home','home','主页','menu.home', 0, 'N', 0, 'main_page', 'poss', 'Y', '1','system',now(),now());
--系统管理
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'sys_conf','sys_conf', 'index', null, 'fa-cog','System Config','系统配置','menu.sys_conf', 1, 'N', 1, 'wrap_page', 'poss', 'Y', '1','system',now(),now());
--用户权限
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'upms_conf','upms_conf', 'sys_conf', null, 'fa-user','User Permissions','用户权限','menu.upms', 1, 'N', 2, 'wrap_page', 'poss', 'Y', '1','system',now(),now());
--用户管理
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'user_manager','user_manager', 'upms_conf', '/upms/user', 'fa-user-plus','user manager','用户管理','menu.user_manager', 1, 'Y', 3, 'curd_page', 'poss', 'Y', '1','system',now(),now());
--权限管理,给用户赋权
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'role_manager','role_manager', 'upms_conf', '/upms/role', 'fa-hand-o-right','role manager','权限管理','menu.role_manager', 2, 'Y', 3, 'curd_page_grant', 'poss', 'Y', '1','system',now(),now());
--菜单管理,给菜单分配角色
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'menu_manager','menu_manager', 'upms_conf', '/upms/menu', 'fa-th-list','menu manager','菜单管理','menu.menu_manager', 3, 'Y', 3, 'curd_page_grant', 'poss', 'Y', '1','system',now(),now());
--基础管理
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'base_conf','base_conf', 'sys_conf', null, 'fa-cogs','Base Config','基础配置','menu.base', 2, 'N', 2, 'wrap_page', 'poss', 'Y', '1','system',now(),now());
--字典管理
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'dict_manager','dict_manager', 'base_conf', '/base/dictionary', 'fa-book','Dictionary manager','字典管理','menu.dict_manager', 1, 'Y', 3, 'curd_page', 'poss', 'Y', '1','system',now(),now());
--外部映射码
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'foreign_code_mapping','foreign_code_mapping', 'base_conf', '/base/foreign', 'fa-file-text','Foreign Code Mapping','外部映射码','menu.foreign_code_mapping', 2, 'Y', 3, 'curd_page', 'poss', 'Y', '1','system',now(),now());
--对外映射码
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'code_mapping','code_mapping', 'base_conf', '/base/code', 'fa-file-text-o','Code Mapping','映射码','menu.code_mapping', 3, 'Y', 3, 'curd_page', 'poss', 'Y', '1','system',now(),now());
--国家,州,城市管理
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'area','area', 'base_conf', '/base/area', 'fa-map','Area Config','地区配置','menu.area', 4, 'Y', 3, 'union_page', 'poss', 'Y', '1','system',now(),now());
--银行管理
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'bank','bank', 'base_conf', '/base/bank', 'fa-bank','Bank Config','银行管理','menu.bank', 5, 'Y', 3, 'union_page', 'poss', 'Y', '1','system',now(),now());
--工作流管理
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'workflow','workflow', 'index', null, 'fa-edit','Workflow Manager','工作流管理','menu.workflow', 3, 'N', 1, 'wrap_page', 'poss', 'Y', '1','system',now(),now());
--模板配置
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'workflow_model','workflow_model', 'workflow', '/workflow/model', 'fa-file-image-o','Workflow Model','工作流模型','menu.workflow_model', 1, 'Y', 2, 'union_page', 'poss', 'Y', '1','system',now(),now());
--工作流实例配置
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'workflow_instance','workflow_instance', 'workflow', '/workflow/instance', 'fa-list','Workflow Instance','工作流实例','menu.workflow_instance', 2, 'Y', 2, 'union_page', 'poss', 'Y', '1','system',now(),now());
--待办
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'workflow_todo','workflow_todo', 'workflow', '/workflow/todo', 'fa-list-ol','Workflow TODO','我的待办','menu.workflow_todo', 3, 'Y', 2, 'union_page', 'poss', 'Y', '1','system',now(),now());
--已办
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'workflow_done','workflow_done', 'workflow', '/workflow/done', 'fa-list-ul','Workflow DONE','我的已办','menu.workflow_todo', 4, 'Y', 2, 'union_page', 'poss', 'Y', '1','system',now(),now());
--客户管理
INSERT INTO t_menu (id, menu_name, menu_alias, parent_menu_name, menu_url, menu_icon, menu_display_en, menu_display_zh, i18n_key, tree_sort, tree_leaf, tree_level, menu_type, upms_domain, is_sys,  status, operator, update_time,create_time)
VALUES(NEXTVAL('seq_menu'), 'customer','customer', 'index', null, ' fa-user-secret','Customer Manager','客户管理','menu.workflow', 3, 'N', 1, 'wrap_page', 'poss', 'Y', '1','system',now(),now());



