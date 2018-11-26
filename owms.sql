SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `order_module`;
CREATE TABLE `order_module` (
  `module_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板编号',
  `module_type` bigint(20) DEFAULT NULL COMMENT '文件类型(1-订单宝模板;2-各平台订单模板;3-运单模板;4-库存文件)',
  `prefix` varchar(100) DEFAULT NULL COMMENT '前缀',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` date NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单模板';
DROP TABLE IF EXISTS `order_batch`;
CREATE TABLE `order_batch` (
  `order_id` varchar(100) NOT NULL DEFAULT '' COMMENT '订单编号',
  `order_batch` int(11) NOT NULL COMMENT '订单批次',
  `batch_date` date NOT NULL COMMENT '导入日期',
  PRIMARY KEY (`order_id`,`batch_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导入批次';
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
  `sku` varchar(200) NOT NULL COMMENT 'SKU',
  `quantity` varchar(200) DEFAULT NULL COMMENT '数量',
  `create_date` date NOT NULL COMMENT '创建日期',
  `actual_consignee_name` varchar(200) DEFAULT NULL COMMENT '实际收件人姓名',
  PRIMARY KEY (`order_id`,`sku`),
  KEY `create_date` (`create_date`,`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情';

DROP TABLE IF EXISTS `order_field_mapping`;
CREATE TABLE `order_field_mapping`(
  `module_id` bigint(20) NOT NULL COMMENT '模板编号',
  `module_type` int(10) NOT NULL COMMENT '文件类型(1-订单宝模板;2-各平台订单模板;3-运单模板;4-库存文件)',
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
  `order_id` varchar(100) NOT NULL COMMENT '订单编号',
  `sku` varchar(200) NOT NULL COMMENT 'SKU',
  `express_id` varchar(30) NOT NULL COMMENT '运单号',
  `express_company` varchar(200) DEFAULT NULL COMMENT '物流公司',
  `create_date` date NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`order_id`, `sku`, `express_id`),
  INDEX (`create_date`,`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='运单详情';

DROP TABLE IF EXISTS `stock_list`;
CREATE TABLE `stock_list` (
  `warehouse_code` varchar(200) NOT NULL COMMENT '仓库编号',
  `sku` varchar(200) NOT NULL COMMENT 'SKU',
  `stocks` int(10) NOT NULL COMMENT '库存',
  `actual_stocks` int(10) NOT NULL COMMENT '可用库存',
  `available_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '库存是否足够(0-不够;1-足够)',
  `data_date` date NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`warehouse_code`,`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存信息';

INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	(123, X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', X'32', X'6F726465725F6D6F64756C65', X'E6A8A1E69DBFE7B1BBE59E8B', 2, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(124, X'E59084E5B9B3E58FB0E8AEA2E58D95', X'31', X'6F726465725F6D6F64756C65', X'E6A8A1E69DBFE7B1BBE59E8B', 1, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(125, X'E8BF90E58D95E6A8A1E69DBF', X'33', X'6F726465725F6D6F64756C65', X'E6A8A1E69DBFE7B1BBE59E8B', 3, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(126, X'E4BB93E5BA93E4BBA3E7A081', X'77617265686F757365436F6465', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 1, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(127, X'E58F82E88083E7BC96E58FB7', X'6F726465724964', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 2, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(128, X'E6B4BEE98081E696B9E5BC8F', X'64656C69766572795374796C65', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 3, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(129, X'E4BF9DE999A9E7B1BBE59E8B', X'696E737572616E636554797065', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 4, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(130, X'E68A95E4BF9DE98791E9A29D', X'696E737572616E636556616C7565', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 5, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(131, X'E694B6E4BBB6E4BABAE5A793E5908D', X'636F6E7369676E65654E616D65', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 6, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(132, X'E694B6E4BBB6E4BABAE59BBDE5AEB6', X'636F6E7369676E6565436F756E747279', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 9, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(133, X'E8A197E98193', X'737472656574', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 12, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(134, X'E982AEE7BC96', X'7A6970436F6465', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 14, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(135, X'E694B6E4BBB6E4BABAE794B5E8AF9D', X'636F6E7369676E656550686F6E65', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 7, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(136, X'E694B6E4BBB6E4BABAE8BAABE4BBBDE8AF81E58FB7', X'636F6E7369676E65654964', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 8, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(137, X'E5B79E2FE79C81', X'70726F76696E6365', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 10, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(138, X'E59F8EE5B8822FE5B882', X'63697479', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 11, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(139, X'E997A8E7898CE58FB7', X'646F6F72706C617465', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 13, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(140, X'534B55284E29', X'736B75', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 15, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(141, X'E695B0E9878F284E29', X'7175616E74697479', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 16, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(143, X'E8AEA2E58D95E7BC96E58FB7', X'6F726465724964', X'6F726465725F6D6F64756C655F33', X'E8BF90E58D95E6A8A1E69DBF', 1, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(144, X'E4BE9BE5BA94E59586534B55', X'736B75', X'6F726465725F6D6F64756C655F33', X'E8BF90E58D95E6A8A1E69DBF', 2, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(145, X'E8BF90E58D95E58FB7', X'657870726573734964', X'6F726465725F6D6F64756C655F33', X'E8BF90E58D95E6A8A1E69DBF', 3, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(146, X'E789A9E6B581E585ACE58FB8', X'65787072657373436F6D70616E79', X'6F726465725F6D6F64756C655F33', X'E8BF90E58D95E6A8A1E69DBF', 4, NULL, NULL, NULL, NULL, NULL, X'', X''),
	(148, X'E5AE9EE99985E694B6E4BBB6E4BABA', X'61637475616C436F6E7369676E65654E616D65', X'6F726465725F6D6F64756C655F32', X'E8AEA2E58D95E5AE9DE6A8A1E69DBF', 6, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(149, X'E5BA93E5AD98E6A8A1E69DBF', X'34', X'6F726465725F6D6F64756C65', X'E6A8A1E69DBFE7B1BBE59E8B', 4, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(150, X'534B55', X'736B75', X'6F726465725F6D6F64756C655F34', X'E5BA93E5AD98E6A8A1E69DBF', 1, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(151, X'E8B4A7E59381E5908DE7A7B0', X'736B754E616D65', X'6F726465725F6D6F64756C655F34', X'E5BA93E5AD98E6A8A1E69DBF', 2, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(152, X'E4BB93E5BA93', X'77617265686F757365436F6465', X'6F726465725F6D6F64756C655F34', X'E5BA93E5AD98E6A8A1E69DBF', 3, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(154, X'E5BA93E5AD98', X'73746F636B73', X'6F726465725F6D6F64756C655F34', X'E5BA93E5AD98E6A8A1E69DBF', 4, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(155, X'E69BB4E696B0E697A5E69C9F', X'6461746144617465', X'6F726465725F6D6F64756C655F34', X'E5BA93E5AD98E6A8A1E69DBF', 6, NULL, NULL, NULL, NULL, NULL, X'', NULL),
	(156, X'E58FAFE794A8E5BA93E5AD98', X'61637475616C53746F636B73', X'6F726465725F6D6F64756C655F34', X'E5BA93E5AD98E6A8A1E69DBF', 5, NULL, NULL, NULL, NULL, NULL, X'', NULL);
