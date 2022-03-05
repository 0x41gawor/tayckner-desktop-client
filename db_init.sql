CREATE DATABASE IF NOT EXISTS `tayckner_desktop_db`;
USE `tayckner_desktop_db`;

DROP TABLE IF EXISTS `activity`;
DROP TABLE IF EXISTS `schedule`;
DROP TABLE IF EXISTS `habit`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
`id` int NOT NULL auto_increment,
`username` varchar(45) NOT NULL unique,
`password` varchar(64) NOT NULL,
`first_name` varchar(45),
`last_name` varchar(45),
`email` varchar(45) NOT NULL unique,

primary key (`id`)
) engine=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `category` (
`id` int NOT NULL auto_increment,
`name` varchar(45)  NOT NULL,
`description` varchar(255) default null,
`color` varchar(7)  NOT NULL,
`user_id` int  NOT NULL,

primary key (`id`),

KEY `FK_user_idx` (`user_id`),
CONSTRAINT `FK_user_in_category`
FOREIGN KEY (`user_id`)
REFERENCES `user` (`id`)

on delete no action on update no action
) engine=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8, COLLATE=utf8_unicode_ci;

CREATE TABLE `activity` (
`id` int NOT NULL auto_increment,
`name` varchar(45) NOT NULL,
`start_time` time NOT NULL,
`end_time` time,
`date` date NOT NULL,
`duration` int NOT NULL,
`breaks` int default 0 NOT NULL,
`category_id` int NOT NULL,

primary key (`id`),

KEY `FK_category_idx` (`category_id`),
CONSTRAINT `FK_category`
FOREIGN KEY (`category_id`)
REFERENCES `category` (`id`)

on delete no action on update no action
) engine=InnoDB AUTO_INCREMENT=1 CHARSET=utf8, COLLATE=utf8_unicode_ci;

CREATE TABLE `schedule` (
`id` int NOT NULL auto_increment,
`name` varchar(100) NOT NULL,
`start_time` time,
`end_time` time,
`date` date NOT NULL,
`duration` double,
`user_id` int  NOT NULL,

primary key (`id`),

KEY `FK_user_idx` (`user_id`),
CONSTRAINT `FK_user_in_schedule`
FOREIGN KEY (`user_id`)
REFERENCES `user` (`id`)

on delete no action on update no action
) engine=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8, COLLATE=utf8_unicode_ci;

CREATE TABLE `habit` (
`id` int NOT NULL auto_increment,
`name` varchar(45) NOT NULL,
`description` varchar(255) NOT NULL,
`color` varchar(7)  NOT NULL,
`user_id` int NOT NULL,

primary key (`id`),

KEY `FK_user_idx` (`user_id`),

CONSTRAINT `FK_user_in_habit`
FOREIGN KEY (`user_id`)
REFERENCES `user` (`id`)

on delete no action on update no action
) engine=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8, COLLATE=utf8_unicode_ci;

INSERT INTO `user` VALUES
	(1, 'test_user', 'secret', 'test', 'testowski', 'test@test.pl');
    
INSERT INTO `schedule` VALUES
	(2, 'test', '12:00', '13:00', '2022-02-25', 0.5, 1);
    


USE `tayckner_desktop_db`;
select * from `user`;
select * from `schedule`;
select * from `category`;
select * from `activity`;
select * from `habit`;

select * from user where id = 3
