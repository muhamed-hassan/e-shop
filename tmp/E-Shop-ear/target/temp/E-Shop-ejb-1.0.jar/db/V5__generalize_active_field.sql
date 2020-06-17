ALTER TABLE `category` CHANGE `not_deleted` `active` bit(1);

ALTER TABLE `vendor` CHANGE `not_deleted` `active` bit(1);

ALTER TABLE `product` CHANGE `not_deleted` `active` bit(1);