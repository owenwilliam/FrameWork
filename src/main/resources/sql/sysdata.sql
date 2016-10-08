--创建客户
CREATE TABLE customer (
id bigint(20) NOT NULL AUTO_INCREMENT,
name varchar(255) DEFAULT NULL,
contact varchar(255) DEFAULT NULL,
telephone varchar(255) DEFAULT NULL,
email varchar(255) DEFAULT NULL,
remark text,
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

TRUNCATE customer;
INSERT INTO customer VALUES ('1','customer1','Jack','1351245678','jack@gmail.com',null);
INSERT INTO customer VALUES('2','customer2','Rose','1351245679','rose@gmail.com',null);
