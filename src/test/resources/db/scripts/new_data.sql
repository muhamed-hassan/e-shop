INSERT INTO `vendor` (`id`, `name`) VALUES (1, 'Sony'), (2, 'Toshiba'), (3, 'Apple'), (4, 'HP'), (5, 'Samsung'),
(6, 'Asus'), (7, 'Lenovo'), (8, 'Acer'), (9, 'Dell'), (10, 'Nokia');
INSERT INTO `category` (`id`, `name`) VALUES (1, 'Laptops'), (2, 'Mobiles'), (3, 'Tablets'), (4, 'Accessories'),
(5, 'Refrigerators'), (6, 'Dishwashers'), (7, 'Air conditioners'), (8, 'Microwave ovens'), (9, 'Water coolers'), (10, 'Coffeemakers');
INSERT INTO `product` (`id`, `name`, `price`, `quantity`, `description`, `active`, `vendor`, `category`, `image_uploaded`)
VALUES (1, 'test product 1', 65.2, 60, 'ttttttttt666ttttt', true, 2, 2, false);
INSERT INTO `product` (`id`, `name`, `price`, `quantity`, `description`, `active`, `vendor`, `category`, `image_uploaded`)
VALUES (2, 'test product 2', 75.2, 70, 'ttttttttt777ttttt', true, 1, 1, false);