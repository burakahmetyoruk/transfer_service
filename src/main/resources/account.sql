CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_account_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;