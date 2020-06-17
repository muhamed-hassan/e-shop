INSERT INTO `role` VALUES (1,'Customer'),
                            (2,'Admin');

INSERT INTO `user` (`user_name`, `name`, `password`, `address`, `phone`, `mail`, `role`) VALUES
                    ('admin', 'Administrator', 'XTL8hQTbZL6v9VgTDVbtFQ==', NULL, NULL, 'admin@local.com', 2),
                    ('demo', 'Demo Customer', 'XTL8hQTbZL6v9VgTDVbtFQ==', 'Cairo', '0112233', 'demo@local.com', 1);