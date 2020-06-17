CREATE TABLE `category` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(150) UNIQUE NOT NULL,
  `not_deleted` bit(1) DEFAULT b'1'
);

CREATE TABLE `vendor` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(150) UNIQUE NOT NULL,
  `not_deleted` bit(1) DEFAULT b'1'
);

CREATE TABLE `product` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(150) UNIQUE NOT NULL,
  `image` mediumblob,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT '0',
  `description` longtext,
  `vendor` int(11) NOT NULL,
  `category` int(11) NOT NULL,
  `not_deleted` bit(1) DEFAULT b'1',
  FOREIGN KEY (`category`) REFERENCES `category` (`id`),
  FOREIGN KEY (`vendor`) REFERENCES `vendor` (`id`)
);

CREATE TABLE `role` (
  `id` int(11) PRIMARY KEY,
  `name` varchar(45) UNIQUE NOT NULL
);

CREATE TABLE `user` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `user_name` varchar(45) UNIQUE NOT NULL,
  `name` varchar(150) NOT NULL,
  `password` varchar(45) NOT NULL,
  `address` varchar(500),
  `phone` varchar(45) UNIQUE,
  `mail` varchar(150) UNIQUE,
  `active` bit(1) DEFAULT b'1',
  `role` int(11) NOT NULL DEFAULT '1',
  FOREIGN KEY (`role`) REFERENCES `role` (`id`)
);

CREATE TABLE `customer_fav_product` (
  `customer` int(11) NOT NULL DEFAULT '0',
  `product` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`customer`,`product`),
  FOREIGN KEY (`customer`) REFERENCES `user` (`id`),
  FOREIGN KEY (`product`) REFERENCES `product` (`id`)
);

