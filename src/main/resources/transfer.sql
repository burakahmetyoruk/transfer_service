CREATE TABLE `transfer` (
  `id` bigint(20) NOT NULL,
  `transfer_amount` decimal(19,2) DEFAULT NULL,
  `transferred_id` bigint(20) DEFAULT NULL,
  `transferrer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_TRANSFER_ACCOUNT_TRANSFERRED_ID` (`transferred_id`),
  KEY `FK_TRANSFER_ACCOUNT_TRANSFERRER_ID` (`transferrer_id`),
  CONSTRAINT `FK_TRANSFER_ACCOUNT_TRANSFERRED_ID` FOREIGN KEY (`transferred_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FK_TRANSFER_ACCOUNT_TRANSFERRER_ID` FOREIGN KEY (`transferrer_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;