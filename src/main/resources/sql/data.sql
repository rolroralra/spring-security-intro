-- INSERT INTO users(username, password, enabled) VALUES('rolroralra', 'password2', 1);
--
-- INSERT INTO authorities(username, authority) VALUES('rolroralra', 'read');
-- INSERT INTO authorities(username, authority) VALUES('rolroralra', 'write');
-- INSERT INTO authorities(username, authority) VALUES('rolroralra', 'delete');

INSERT INTO `user_info` ( `username`, `password`, `algorithm`) VALUES ( 'rolroralra', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');
INSERT INTO `user_info` ( `username`, `password`, `algorithm`) VALUES ( 'admin', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');

INSERT INTO `user_authority` (`user_authorities`, `user_id`) VALUES ('READ', 1);
INSERT INTO `user_authority` (`user_authorities`, `user_id`) VALUES ('WRITE', 2);


INSERT INTO `product`(name, price, currency) VALUES('product01', 1000, 'WON');
INSERT INTO `product`(name, price, currency) VALUES('product02', 2000, 'WON');
INSERT INTO `product`(name, price, currency) VALUES('product03', 3000, 'WON');
INSERT INTO `product`(name, price, currency) VALUES('product04', 4000, 'WON');
INSERT INTO `product`(name, price, currency) VALUES('product05', 4000, 'USD');
