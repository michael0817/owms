SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `order_module`;
CREATE TABLE `order_module` (
  `module_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板编号',
  `module_type` bigint(20) DEFAULT NULL COMMENT '文件类型(1-订单宝模板;2-各平台订单模板;3-运单模板)',
  `prefix` varchar(100) DEFAULT NULL COMMENT '前缀',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` date NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单模板';

DROP TABLE IF EXISTS `order_list`;
CREATE TABLE `order_list` (
  `order_id` varchar(30) NOT NULL COMMENT '订单编号',
  `module_id` bigint(20) NOT NULL COMMENT '模板编号',
  `warehouse_code` varchar(200) DEFAULT NULL COMMENT '仓库代码',
  `delivery_style` varchar(200) DEFAULT NULL COMMENT '配送方式',
  `insurance_type` varchar(200) DEFAULT NULL COMMENT '保险类型',
  `insurance_value` varchar(200) DEFAULT NULL COMMENT '投保金额',
  `consignee_name` varchar(200) DEFAULT NULL COMMENT '收件人姓名',
  `consignee_phone` varchar(200) DEFAULT NULL COMMENT '收件人电话',
  `consignee_country` varchar(200) DEFAULT NULL COMMENT '收件人国家',
  `consignee_id` varchar(200) DEFAULT NULL COMMENT '收件人身份证号',
  `province` varchar(200) DEFAULT NULL COMMENT '州/省',
  `city` varchar(200) DEFAULT NULL COMMENT '城市/市',
  `street` varchar(500) DEFAULT NULL COMMENT '街道',
  `doorplate` varchar(200) DEFAULT NULL COMMENT '门牌号/区',
  `zip_code` varchar(200) DEFAULT NULL COMMENT '邮编',
  `sku` varchar(200) DEFAULT NULL COMMENT 'SKU',
  `quantity` varchar(200) DEFAULT NULL COMMENT '数量',
  `create_date` date NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`order_id`,`sku`),
  INDEX (`create_date`,`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单详情';


DROP TABLE IF EXISTS `order_field_mapping`;
CREATE TABLE `order_field_mapping`(
  `module_id` bigint(20) NOT NULL COMMENT '模板编号',
  `module_type` bigint(20) NOT NULL COMMENT '文件类型(1-订单宝模板;2-各平台订单模板;3-运单模板)',
  `business_field_name` varchar(200) NOT NULL COMMENT '业务字段',
  `excel_field_name` varchar(200) DEFAULT NULL COMMENT 'EXCEL字段名',
  PRIMARY KEY (`module_id`,`module_type`,`business_field_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='模板字段映射';

DROP TABLE IF EXISTS `order_excel_fields`;
CREATE TABLE `order_excel_fields`(
  `module_name` varchar(200) NOT NULL COMMENT '模板文件名',
  `excel_field_name` varchar(200) DEFAULT NULL COMMENT 'EXCEL字段名'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Excel模板字段';

DROP TABLE IF EXISTS `express_list`;
CREATE TABLE `express_list` (
  `order_id` varchar(30) NOT NULL COMMENT '订单编号',
  `sku` varchar(200) NOT NULL COMMENT 'SKU',
  `express_id` varchar(30) NOT NULL COMMENT '运单号',
  `express_company` varchar(200) DEFAULT NULL COMMENT '物流公司',
  `create_date` date NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`order_id`, `sku`, `express_id`),
  INDEX (`create_date`,`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='运单详情';
