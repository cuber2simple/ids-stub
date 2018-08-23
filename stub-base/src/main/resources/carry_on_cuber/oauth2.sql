CREATE TABLE IF NOT EXISTS oauth_client_details (
  id  						INTEGER NOT NULL PRIMARY KEY,
  client_id 				VARCHAR(256),
  resource_ids 				VARCHAR(256),
  client_secret 			VARCHAR(256),
  scope 					VARCHAR(256),
  authorized_grant_types 	VARCHAR(256),
  web_server_redirect_uri 	VARCHAR(256),
  authorities 				VARCHAR(256),
  access_token_validity 	INTEGER,
  refresh_token_validity 	INTEGER,
  additional_information 	VARCHAR(4096),
  autoapprove 				VARCHAR(256),
  status 					VARCHAR(2),
  operator 					VARCHAR(64),
  update_time 				TIMESTAMP WITHOUT TIME ZONE,
  create_time 				TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_client increment by 1 minvalue 1 no maxvalue start with 1 cache 20;

comment on table oauth_client_details is '客户端资源表';
comment on column oauth_client_details.client_id is '客户端ID/appKey';
comment on column oauth_client_details.resource_ids is '可以访问的资源ID';
comment on column oauth_client_details.client_secret is '客户端密钥/appSecret';
comment on column oauth_client_details.scope is '对资源的权限范围,见字典表oauth2.client.scope';
comment on column oauth_client_details.authorized_grant_types is '授权类型见字典表oauth2.grant_type, 若支持多个grant_type用逗号(,)分隔';
comment on column oauth_client_details.web_server_redirect_uri is 'authorization_code下,客户端的重定向URI,可为空, 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致';
comment on column oauth_client_details.authorities is '指定客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔, 如: "ROLE_UNITY,ROLE_USER"';
comment on column oauth_client_details.access_token_validity is '设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时)';
comment on column oauth_client_details.refresh_token_validity is '设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天)';
comment on column oauth_client_details.additional_information is '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据';
comment on column oauth_client_details.autoapprove is '设置用户是否自动Approval操作, 默认值为 ''false'', 可选值包括 ''true'',''false'', ''read'',''write''. ';


--客户端需要,和多个oauth2对接需要保存的表
CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id 			VARCHAR(256) PRIMARY KEY,
  token 			bytea,
  authentication_id VARCHAR(256),
  user_name 		VARCHAR(256),
  client_id 		VARCHAR(256),
  token_type 		VARCHAR(64),
  expired_time 		TIMESTAMP WITHOUT TIME ZONE,
  create_time 		TIMESTAMP WITHOUT TIME ZONE
);

comment on table oauth_client_token is '客户端保存的token表';
comment on column oauth_client_token.token_id is '授权的tokenId';
comment on column oauth_client_token.token is 'OAuth2AccessToken.java对象序列化后的二进制数据.';
comment on column oauth_client_token.authentication_id is '该字段具有唯一性, 是根据当前的username(如果有),client_id与scope通过MD5加密生成的.';
comment on column oauth_client_token.user_name is '登录时的用户名';
comment on column oauth_client_token.client_id is '授权客户端ID';
comment on column oauth_client_token.token_type is 'token类型';
comment on column oauth_client_token.expired_time is '过期时间';
comment on column oauth_client_token.create_time is '生成时间';

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id 			VARCHAR(256) PRIMARY KEY,
  token 			bytea,
  authentication_id VARCHAR(256),
  user_name 		VARCHAR(256),
  client_id 		VARCHAR(256),
  authentication 	bytea,
  refresh_token 	VARCHAR(256),
  token_type 		VARCHAR(64),
  expired_time 		TIMESTAMP WITHOUT TIME ZONE,
  create_time 		TIMESTAMP WITHOUT TIME ZONE
);
comment on table oauth_access_token is '授权的token访问';
comment on column oauth_access_token.token_id is '该字段的值是将access_token的值通过MD5加密后存储的';
comment on column oauth_access_token.token is '存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值.';
comment on column oauth_access_token.authentication_id is '该字段具有唯一性, 其值是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 具体实现请参考DefaultAuthenticationKeyGenerator.java类.';
comment on column oauth_access_token.user_name is '登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id';
comment on column oauth_access_token.client_id is '授权客户端ID';
comment on column oauth_access_token.authentication is '存储将OAuth2Authentication.java对象序列化后的二进制数据.';
comment on column oauth_access_token.client_id is '刷新的token';
comment on column oauth_access_token.refresh_token is '字段的值是将refresh_token的值通过MD5加密后存储的.';
comment on column oauth_access_token.expired_time is '过期时间';
comment on column oauth_access_token.create_time is '生成时间';

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id 			VARCHAR(256)  PRIMARY KEY,
  token 			bytea,
  authentication 	bytea,
  expired_time 		TIMESTAMP WITHOUT TIME ZONE,
  create_time 		TIMESTAMP WITHOUT TIME ZONE
);

comment on table oauth_refresh_token is 'freshToken表';
comment on column oauth_refresh_token.token_id is '该字段的值是将refresh_token的值通过MD5加密后存储的';
comment on column oauth_refresh_token.token is '存储将OAuth2RefreshToken.java对象序列化后的二进制数据.';
comment on column oauth_refresh_token.authentication is '储将OAuth2Authentication.java对象序列化后的二进制数据.';
comment on column oauth_refresh_token.expired_time is '过期时间';
comment on column oauth_refresh_token.create_time is '创建时间';

CREATE TABLE IF NOT EXISTS oauth_code (
  code 				VARCHAR(256)  PRIMARY KEY,
  authentication 	bytea
);
comment on table oauth_code is 'authorization_code才用到的表';
comment on column oauth_code.token_id is '存储服务端系统生成的code的值(未加密).';
comment on column oauth_code.token is '存储将AuthorizationRequestHolder.java对象序列化后的二进制数据.';