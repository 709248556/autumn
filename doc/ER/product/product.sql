/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/1/4 11:04:44                            */
/*==============================================================*/


drop table if exists attribute_enum;

drop table if exists attribute_enum_value;

drop table if exists attribute_item;

drop table if exists attribute_price_spec;

drop table if exists attribute_template;

drop table if exists brand;

drop table if exists brand_category;

drop table if exists brand_series;

drop table if exists category_basic;

drop table if exists category_manager;

drop table if exists product;

drop table if exists product_sku;

drop table if exists spec;

drop table if exists spu_attribute;

drop table if exists spu_spec;

/*==============================================================*/
/* Table: attribute_enum                                        */
/*==============================================================*/
create table attribute_enum
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   name                 varchar(50) not null,
   binding_type         int not null comment '1=通用
            2=类别绑定
            3=品牌绑定
            4=品牌系列绑定',
   described            text,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            bit not null,
   gmt_delete           datetime,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_attribute_enum_name                                */
/*==============================================================*/
create index ix_attribute_enum_name on attribute_enum
(
   name
);

/*==============================================================*/
/* Index: ix_attribute_enum_sort                                */
/*==============================================================*/
create index ix_attribute_enum_sort on attribute_enum
(
   sort_id
);

/*==============================================================*/
/* Table: attribute_enum_value                                  */
/*==============================================================*/
create table attribute_enum_value
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   name                 varchar(50) not null,
   enum_id              bigint not null comment '1=通用
            2=类别
            3=品牌系列',
   category_id          bigint comment '绑定(2和3)时必填',
   brand_id             bigint,
   brand_series_id      bigint comment '如果父级选择为3时必填',
   described            text,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_attr_enum_value_name                               */
/*==============================================================*/
create index ix_attr_enum_value_name on attribute_enum_value
(
   name
);

/*==============================================================*/
/* Index: ix_attr_enum_value_sort                               */
/*==============================================================*/
create index ix_attr_enum_value_sort on attribute_enum_value
(
   sort_id
);

/*==============================================================*/
/* Index: ix_attr_enum_value_enum                               */
/*==============================================================*/
create index ix_attr_enum_value_enum on attribute_enum_value
(
   enum_id
);

/*==============================================================*/
/* Index: ix_attr_enum_value_series                             */
/*==============================================================*/
create index ix_attr_enum_value_series on attribute_enum_value
(
   brand_series_id
);

/*==============================================================*/
/* Table: attribute_item                                        */
/*==============================================================*/
create table attribute_item
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   attr_id              bigint not null,
   name                 varchar(50) not null,
   data_type            int not null comment '1=字符
            2=整数
            3=小数
            4=日期',
   input_type           int not null comment '1=直接输入
            2=选择枚举输入(仅支持字符类型)
            3=自定义枚举(仅支持字符类型)',
   input_enum_type_id   bigint,
   custom_enum_list     text comment '多个之间用分号',
   is_control_input     bit not null,
   is_search            bit not null,
   described            text,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_attr_item_name                                     */
/*==============================================================*/
create index ix_attr_item_name on attribute_item
(
   name
);

/*==============================================================*/
/* Index: ix_attr_item_sort                                     */
/*==============================================================*/
create index ix_attr_item_sort on attribute_item
(
   sort_id
);

/*==============================================================*/
/* Table: attribute_price_spec                                  */
/*==============================================================*/
create table attribute_price_spec
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   category_id          bigint not null,
   name                 varchar(50) not null,
   is_custom_enum       bit,
   input_enum_type_id   bigint,
   custom_enum_list     text comment '多个之间用分号',
   primary key (id)
);

/*==============================================================*/
/* Index: ix_attr_price_spec_category                           */
/*==============================================================*/
create unique index ix_attr_price_spec_category on attribute_price_spec
(
   category_id,
   name
);

/*==============================================================*/
/* Index: ix_attr_price_spec_enum                               */
/*==============================================================*/
create index ix_attr_price_spec_enum on attribute_price_spec
(
   input_enum_type_id
);

/*==============================================================*/
/* Index: ix_attr_price_spec_sort                               */
/*==============================================================*/
create index ix_attr_price_spec_sort on attribute_price_spec
(
   sort_id
);

/*==============================================================*/
/* Table: attribute_template                                    */
/*==============================================================*/
create table attribute_template
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   name                 varchar(50) not null,
   described            text,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            bit not null,
   gmt_delete           datetime,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_attr_template_name                                 */
/*==============================================================*/
create index ix_attr_template_name on attribute_template
(
   name
);

/*==============================================================*/
/* Index: ix_attr_template_sort                                 */
/*==============================================================*/
create index ix_attr_template_sort on attribute_template
(
   sort_id
);

/*==============================================================*/
/* Table: brand                                                 */
/*==============================================================*/
create table brand
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   name                 varchar(50) not null,
   logo_path            varchar(255),
   described            text,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            bit not null,
   gmt_delete           datetime,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_brand_name                                         */
/*==============================================================*/
create index ix_brand_name on brand
(
   name
);

/*==============================================================*/
/* Index: ix_brand_sort                                         */
/*==============================================================*/
create index ix_brand_sort on brand
(
   sort_id
);

/*==============================================================*/
/* Table: brand_category                                        */
/*==============================================================*/
create table brand_category
(
   id                   bigint not null auto_increment,
   brand_id             bigint not null,
   category_id          bigint not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            bit not null,
   gmt_delete           datetime,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_brand_category_category_id                         */
/*==============================================================*/
create index ix_brand_category_category_id on brand_category
(
   category_id
);

/*==============================================================*/
/* Index: ix_brand_category_brand_id                            */
/*==============================================================*/
create index ix_brand_category_brand_id on brand_category
(
   brand_id
);

/*==============================================================*/
/* Table: brand_series                                          */
/*==============================================================*/
create table brand_series
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   brand_category_id    bigint not null,
   name                 varchar(50) not null,
   level                int not null,
   parent_id            bigint,
   described            text,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            bit not null,
   gmt_delete           datetime,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_brand_series_name                                  */
/*==============================================================*/
create index ix_brand_series_name on brand_series
(
   name
);

/*==============================================================*/
/* Index: ix_brand_series_sort                                  */
/*==============================================================*/
create index ix_brand_series_sort on brand_series
(
   sort_id
);

/*==============================================================*/
/* Index: ix_brand_series_brand_category                        */
/*==============================================================*/
create index ix_brand_series_brand_category on brand_series
(
   brand_category_id
);

/*==============================================================*/
/* Index: ix_brand_series_parent                                */
/*==============================================================*/
create index ix_brand_series_parent on brand_series
(
   parent_id
);

/*==============================================================*/
/* Table: category_basic                                        */
/*==============================================================*/
create table category_basic
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   name                 varchar(50) not null,
   level                int not null,
   parent_id            bigint,
   seo_title            varchar(50),
   seo_keywords         varchar(50),
   template_id          bigint,
   described            text,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            bit not null,
   gmt_delete           datetime,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_category_basics_name                               */
/*==============================================================*/
create index ix_category_basics_name on category_basic
(
   name
);

/*==============================================================*/
/* Index: ix_category_basics_sort                               */
/*==============================================================*/
create index ix_category_basics_sort on category_basic
(
   sort_id
);

/*==============================================================*/
/* Index: ix_category_basics_parent                             */
/*==============================================================*/
create index ix_category_basics_parent on category_basic
(
   parent_id
);

/*==============================================================*/
/* Table: category_manager                                      */
/*==============================================================*/
create table category_manager
(
   id                   bigint not null auto_increment,
   sort_id              int not null,
   name                 varchar(50) not null,
   parent_id            bigint,
   category_basics_id   bigint,
   described            text,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            bit not null,
   gmt_delete           datetime,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_category_manager_name                              */
/*==============================================================*/
create index ix_category_manager_name on category_manager
(
   name
);

/*==============================================================*/
/* Index: ix_category_manager_sort                              */
/*==============================================================*/
create index ix_category_manager_sort on category_manager
(
   sort_id
);

/*==============================================================*/
/* Index: ix_category_manager_parent                            */
/*==============================================================*/
create index ix_category_manager_parent on category_manager
(
   parent_id
);

/*==============================================================*/
/* Index: ix_category_manager_basics_id                         */
/*==============================================================*/
create index ix_category_manager_basics_id on category_manager
(
   category_basics_id
);

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
create table product
(
   id                   bigint not null auto_increment,
   category_id          bigint not null,
   shop_id              varchar(64) not null,
   sort_id              int not null,
   name                 varchar(50) not null,
   brand_name           varchar(50) not null,
   series_name          varchar(50) not null,
   series_value         varchar(50) not null,
   status               int not null,
   described            text,
   web_body             text,
   mobile_body          text,
   seo_title            varchar(50),
   seo_keywords         varchar(50),
   volume               decimal(38,4),
   weight               decimal(38,4),
   unit                 varchar(10),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            bit not null,
   gmt_delete           datetime,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_product_name                                       */
/*==============================================================*/
create index ix_product_name on product
(
   name
);

/*==============================================================*/
/* Index: ix_product_sort                                       */
/*==============================================================*/
create index ix_product_sort on product
(
   sort_id
);

/*==============================================================*/
/* Index: ix_product_shop                                       */
/*==============================================================*/
create index ix_product_shop on product
(
   shop_id
);

/*==============================================================*/
/* Index: ix_product_category                                   */
/*==============================================================*/
create index ix_product_category on product
(
   category_id
);

/*==============================================================*/
/* Table: product_sku                                           */
/*==============================================================*/
create table product_sku
(
   id                   varchar(64) not null,
   code                 varchar(64) not null,
   spu_id               bigint not null,
   auto_sort_id         int not null,
   spec_value           varchar(64) not null,
   spec_friendly        varchar(255) not null,
   price                decimal(38,2) not null,
   stock                bigint not null,
   status               int not null,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_product_sku_spu                                    */
/*==============================================================*/
create index ix_product_sku_spu on product_sku
(
   spu_id
);

/*==============================================================*/
/* Index: ix_product_sku_auto_sort                              */
/*==============================================================*/
create index ix_product_sku_auto_sort on product_sku
(
   auto_sort_id
);

/*==============================================================*/
/* Table: spec                                                  */
/*==============================================================*/
create table spec
(
   id                   bigint not null auto_increment,
   name                 varchar(20) not null,
   value                varchar(50) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: ix_spec_name_value                                    */
/*==============================================================*/
create unique index ix_spec_name_value on spec
(
   name,
   value
);

/*==============================================================*/
/* Table: spu_attribute                                         */
/*==============================================================*/
create table spu_attribute
(
   id                   bigint not null auto_increment,
   spu_id               bigint not null,
   name                 varchar(20) not null,
   value                varchar(50) not null comment '每个商品均从1自动开始',
   primary key (id)
);

/*==============================================================*/
/* Index: spu_attr_spu                                          */
/*==============================================================*/
create index spu_attr_spu on spu_attribute
(
   spu_id
);

/*==============================================================*/
/* Table: spu_spec                                              */
/*==============================================================*/
create table spu_spec
(
   id                   bigint not null auto_increment,
   spu_id               bigint not null,
   spec_id              bigint not null,
   auto_sort_id         int not null comment '每个商品均从1自动开始',
   primary key (id)
);

/*==============================================================*/
/* Index: ix_spu_spec_key                                       */
/*==============================================================*/
create unique index ix_spu_spec_key on spu_spec
(
   spu_id,
   spec_id
);

/*==============================================================*/
/* Index: ix_spu_spec_auto_sort                                 */
/*==============================================================*/
create index ix_spu_spec_auto_sort on spu_spec
(
   auto_sort_id
);

