CREATE DATABASE IF NOT EXISTS `tayckner_desktop_db`;
USE `tayckner_desktop_db`;

DROP TABLE IF EXISTS `schedule`;
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

CREATE TABLE `schedule` (
`id` int NOT NULL auto_increment,
`name` varchar(45) NOT NULL,
`start_time` datetime NOT NULL,
`end_time` datetime,
`duration` double,
`user_id` int  NOT NULL,

primary key (`id`),

KEY `FK_user_idx` (`user_id`),
CONSTRAINT `FK_user_in_schedule`
FOREIGN KEY (`user_id`)
REFERENCES `user` (`id`)

on delete no action on update no action
) engine=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user` VALUES
	(1, 'test_user', 'secret', 'test', 'testowski', 'test@test.pl');
    
select * from `user`;

