CREATE DATABASE iykescodeschool;

use iykescodeschool;

select * from holidays;
select * from contact_msg;
select * from users;
select * from address;
select * from roles;

CREATE TABLE IF NOT EXISTS `contact_msg` (
     `id` int AUTO_INCREMENT  PRIMARY KEY,
     `name` varchar(100) NOT NULL,
     `mobile_num` varchar(10) NOT NULL,
     `email` varchar(100) NOT NULL,
     `subject` varchar(100) NOT NULL,
     `message` varchar(500) NOT NULL,
     `status` varchar(10) NOT NULL,
     `created_at` TIMESTAMP NOT NULL,
     `created_by` varchar(50) NOT NULL,
     `updated_at` TIMESTAMP DEFAULT NULL,
     `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `holidays` (
    `day` varchar(20) NOT NULL,
    `reason` varchar(100) NOT NULL,
    `type` varchar(20) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `roles` (
    `id` int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `address` (
    `id` int NOT NULL AUTO_INCREMENT,
    `address1` varchar(200) NOT NULL,
    `address2` varchar(200) DEFAULT NULL,
    `city` varchar(50) NOT NULL,
    `state` varchar(50) NOT NULL,
    `zip_code` int NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `users` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `email` varchar(50) NOT NULL,
    `mobile_number` varchar(20) NOT NULL,
    `password` varchar(200) NOT NULL,
    `role_id` int NOT NULL,
    `address_id` int NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE IF NOT EXISTS `class` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `users`
    ADD COLUMN `class_id` int NULL AFTER `address_id`,
    ADD CONSTRAINT `FK_CLASS_CLASS_ID` FOREIGN KEY (`class_id`)
        REFERENCES class(`id`);

CREATE TABLE IF NOT EXISTS `courses` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `fees` varchar(10) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user_courses` (
    `user_id` int NOT NULL,
    `course_id` int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    PRIMARY KEY (`user_id`,`course_id`)
);