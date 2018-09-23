SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `order_module`;
CREATE TABLE `order_module` (
  `module_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(10) DEFAULT NULL COMMENT '文件类型(1-订单宝模板;2-各平台订单模板;3-运单模板)',
  `prefix` varchar(100) DEFAULT NULL COMMENT '前缀',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` date NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单模板';

DROP TABLE IF EXISTS `order_list`;
CREATE TABLE `order_list` (
  `order_id` bigint(20) NOT NULL COMMENT '订单编号',
  `module_id` int(10) NOT NULL COMMENT '模板编号',
  `consignee_name` varchar(200) DEFAULT NULL COMMENT '收件人姓名',
  `consignee_phone` varchar(200) DEFAULT NULL COMMENT '收件人电话',
  `consignee_country` varchar(200) DEFAULT NULL COMMENT '收件人国家',
  `province` varchar(200) DEFAULT NULL COMMENT '州/省',
  `city` varchar(200) DEFAULT NULL COMMENT '城市/市',
  `doorplate` varchar(200) DEFAULT NULL COMMENT '门牌号/区',
  `zip_code` varchar(200) DEFAULT NULL COMMENT '邮编',
  `sku` varchar(200) DEFAULT NULL COMMENT 'SKU',
  `quntity` varchar(200) DEFAULT NULL COMMENT '数量',
  `create_date` date NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`order_id`),
  INDEX (`create_date`,`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单模板';

