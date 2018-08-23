CREATE TABLE t_country_3166(
  id              			INTEGER NOT NULL PRIMARY KEY,
  alpha_code_3    			VARCHAR(6) NOT NULL,
  alpha_code_2    			VARCHAR(4) NOT NULL ,
  number_code     			VARCHAR(6) NOT NULL ,
  ISO3166_2       			VARCHAR(32),
  locale_name     			VARCHAR(256),
  zh_name	      			VARCHAR(256),
  name						VARCHAR(256),
  region          			VARCHAR(256),
  sub_region      			VARCHAR(256),
  intermediate_region 		VARCHAR(256),
  region_code     			VARCHAR(6),
  sub_region_code 			VARCHAR(6),
  intermediate_region_code  VARCHAR(6),
  country_code	  			VARCHAR(6),
  currency_code   			VARCHAR(6),
  currency_codes  			VARCHAR(128),
  status          			VARCHAR(2),
  operator        			VARCHAR(256),
  update_time     			TIMESTAMP WITHOUT TIME ZONE,
  create_time     			TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_country increment by 1 minvalue 1 no maxvalue start with 1 cache 20;

CREATE INDEX idx_alpha3_country ON t_country_3166 (alpha_code_3);
CREATE INDEX idx_alpha2_country ON t_country_3166 (alpha_code_2);
CREATE INDEX idx_numic_country ON t_country_3166 (number_code);

comment on table t_country_3166 is '国家列表,https://github.com/lukes/ISO-3166-Countries-with-Regional-Codes';
comment on column t_country_3166.alpha_code_2 is '国家2字码';
comment on column t_country_3166.alpha_code_3 is '国家3字码';
comment on column t_country_3166.number_code is '国家数字码';
comment on column t_country_3166.ISO3166_2 is '国家3166-2字码';
comment on column t_country_3166.locale_name is '国家本地名';
comment on column t_country_3166.zh_name is '国家中文名';
comment on column t_country_3166.name is '国家英文名';
comment on column t_country_3166.region is '国家地区';
comment on column t_country_3166.sub_region is '国家子地区';
comment on column t_country_3166.intermediate_region is '国家中间地区';
comment on column t_country_3166.region_code is '国家地区编码';
comment on column t_country_3166.sub_region_code is '国家子地区编码';
comment on column t_country_3166.intermediate_region_code is '国家中间地区编码';
comment on column t_country_3166.country_code is '国家的电话号码,https://countrycode.org/';
comment on column t_country_3166.currency_code is '国家通行主币种';
comment on column t_country_3166.currency_codes is '国家通行主币种';

CREATE TABLE t_currency_4217
(
  id              INTEGER NOT NULL PRIMARY KEY,
  alpha_code      VARCHAR(6) NOT NULL ,
  number_code     VARCHAR(6) ,
  minor_unit      SMALLINT DEFAULT 0,
  symbol_currency VARCHAR(6),
  locale_name     VARCHAR(256),
  zh_name         VARCHAR(256),
  name            VARCHAR(256),
  country         VARCHAR(256),
  scope			  VARCHAR(256),
  status          VARCHAR(2),
  operator        VARCHAR(256),
  update_time     TIMESTAMP WITHOUT TIME ZONE,
  create_time     TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_currency increment by 1 minvalue 1 no maxvalue start with 1 cache 20;


CREATE INDEX idx_alpha_country ON t_currency_4217 (alpha_code);
CREATE INDEX idx_numic_country ON t_currency_4217 (number_code);

comment on table t_currency_4217 is '币种列表';
comment on column t_currency_4217.alpha_code is '币种字母码';
comment on column t_currency_4217.number_code is '币种数字码';
comment on column t_currency_4217.minor_unit is '币种最小单位';
comment on column t_currency_4217.symbol_currency is '币种符号';
comment on column t_currency_4217.locale_name is '币种本地名';
comment on column t_currency_4217.zh_name is '币种中文名';
comment on column t_currency_4217.name is '币种英文名';
comment on column t_currency_4217.country is '币种使用实体';
comment on column t_currency_4217.scope is '字典表currency.scope';

CREATE TABLE t_i18n_message_resource
(
  id              INTEGER NOT NULL PRIMARY KEY,
  i18n_key        VARCHAR(128) NOT NULL,
  i18n_lang       VARCHAR(64) NOT NULL,
  item_value_1    VARCHAR(4096),
  item_value_2    VARCHAR(4096),
  item_value_3    VARCHAR(4096),
  status          VARCHAR(2),
  operator        VARCHAR(256),
  update_time     TIMESTAMP WITHOUT TIME ZONE,
  create_time     TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_i18n increment by 1 minvalue 1 no maxvalue start with 1 cache 20;

CREATE INDEX idx_i18n_message ON t_i18n_message_resource (i18n_key,i18n_lang);

comment on table t_i18n_message_resource is '国际化资源列表';
comment on column t_i18n_message_resource.i18n_key is 'i18n的key';
comment on column t_i18n_message_resource.i18n_lang is 'i18n的语言';
comment on column t_i18n_message_resource.item_value_1 is 'i18n的值1';
comment on column t_i18n_message_resource.item_value_2 is 'i18n的值2';
comment on column t_i18n_message_resource.item_value_3 is 'i18n的值3';

CREATE TABLE t_foreign_2_inner
(
  id              		INTEGER NOT NULL PRIMARY KEY,
  deal_type           	VARCHAR(256),
  foreign_result_code   VARCHAR(256) NOT NULL,
  foreign_code          VARCHAR(32),
  org_result_desc     	VARCHAR(256),
  inner_stats     	    VARCHAR(256),
  inner_code          	VARCHAR(8),
  status              	VARCHAR(2),
  operator            	VARCHAR(256),
  update_time     		TIMESTAMP WITHOUT TIME ZONE,
  create_time     		TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_foreign increment by 1 minvalue 1 no maxvalue start with 1 cache 20;

CREATE INDEX idx_code_foreign ON t_foreign_2_inner (deal_type,foreign_result_code,foreign_code);

comment on table t_foreign_2_inner is '外部编码到内部编码映射数据表';
comment on column t_foreign_2_inner.deal_type is '交易类型';
comment on column t_foreign_2_inner.foreign_result_code is '外部返回码';
comment on column t_foreign_2_inner.foreign_code is '外部的code';
comment on column t_foreign_2_inner.org_result_desc is '外部返回码描述';
comment on column t_foreign_2_inner.inner_stats is '内部状态';
comment on column t_foreign_2_inner.inner_code is '内部code';

CREATE TABLE t_display_2_out
(
  id              		INTEGER NOT NULL PRIMARY KEY,
  biz_code				VARCHAR(256),
  inner_code           	VARCHAR(256),
  inner_message   		VARCHAR(256),
  display_status     	VARCHAR(256),
  display_code          VARCHAR(8),
  i18n_key				VARCHAR(128),
  status              	VARCHAR(2),
  operator            	VARCHAR(256),
  update_time     		TIMESTAMP WITHOUT TIME ZONE,
  create_time     		TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_display increment by 1 minvalue 1 no maxvalue start with 1 cache 20;

CREATE INDEX idx_diplay_2_out ON t_display_2_out (biz_code,inner_code);

comment on table t_display_2_out is '展示的映射表';
comment on column t_display_2_out.biz_code is '业务code';
comment on column t_display_2_out.inner_code is '内部返回码';
comment on column t_display_2_out.inner_message is '内部返回消息';
comment on column t_display_2_out.display_status is '展示的状态';
comment on column t_display_2_out.display_code is '展示的CODE';
comment on column t_display_2_out.i18n_key is 'i18n的key';


CREATE TABLE t_geo_state
(
  id              		INTEGER NOT NULL PRIMARY KEY,
  name					VARCHAR(256),
  zh_name         		VARCHAR(256),
  locale_name			VARCHAR(256),
  standard_code   		VARCHAR(256),
  country_code	     	VARCHAR(6),
  latitude          	DOUBLE,
  longitude				DOUBLE,
  status              	VARCHAR(2),
  operator            	VARCHAR(256),
  update_time     		TIMESTAMP WITHOUT TIME ZONE,
  create_time     		TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_state increment by 1 minvalue 1 no maxvalue start with 1 cache 20;

CREATE INDEX idx_state_country ON t_geo_state (country_code);

comment on table t_geo_state is '州/省映射表';
comment on column t_geo_state.name is '州英文名';
comment on column t_geo_state.zh_name is '州中文';
comment on column t_geo_state.locale_name is '本地名';
comment on column t_geo_state.standard_code is '标准CODE';
comment on column t_geo_state.country_code is '国家alpha_code';
comment on column t_geo_state.latitude is '经度';
comment on column t_geo_state.longitude is '维度';

CREATE TABLE t_geo_city
(
  id              		INTEGER NOT NULL PRIMARY KEY,
  name					VARCHAR(256),
  zh_name         		VARCHAR(256),
  locale_name			VARCHAR(256),
  standard_code   		VARCHAR(256),
  state_id	     		INTEGER,
  latitude          	DOUBLE,
  longitude				DOUBLE,
  status              	VARCHAR(2),
  operator            	VARCHAR(256),
  update_time     		TIMESTAMP WITHOUT TIME ZONE,
  create_time     		TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_city increment by 1 minvalue 1 no maxvalue start with 1 cache 20;

CREATE INDEX idx_city_state ON t_geo_city (state_id);

comment on table t_geo_city is '州/省映射表';
comment on column t_geo_city.name is '州英文名';
comment on column t_geo_city.zh_name is '州中文';
comment on column t_geo_city.locale_name is '本地名';
comment on column t_geo_city.standard_code is '标准CODE';
comment on column t_geo_city.state_id is 'stateID';
comment on column t_geo_city.latitude is '经度';
comment on column t_geo_city.longitude is '维度';

-- 字典类型表
CREATE TABLE t_sys_dict_type
(
	id  					INTEGER NOT NULL PRIMARY KEY,	
	dict_name 				VARCHAR(128) NOT NULL,
	zh_name 				VARCHAR(128) NOT NULL,
	dict_type 				VARCHAR(128) NOT NULL,
	is_sys 					VARCHAR(2) 	NOT NULL,
	remarks 				VARCHAR(1024),
	status              	VARCHAR(2),
	operator            	VARCHAR(256),
	update_time     		TIMESTAMP WITHOUT TIME ZONE,
	create_time     		TIMESTAMP WITHOUT TIME ZONE
);

create sequence seq_dict_type increment by 1 minvalue 1 no maxvalue start with 1 cache 20;

CREATE UNIQUE INDEX idx_dict_name ON t_sys_dict_type (dict_name);

comment on table t_sys_dict_type is '字典类型表';
comment on column t_sys_dict_type.dict_name is '字典名';
comment on column t_sys_dict_type.zh_name is '字典中名';
comment on column t_sys_dict_type.dict_type is '字典类型';
comment on column t_sys_dict_type.is_sys is '是否系统字典//是系统的不可删除';
comment on column t_sys_dict_type.remarks is '字典名备注';





