DROP DATABASE IF EXISTS ssmCnode;
CREATE DATABASE ssmCnode CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ssmCnode;
CREATE TABLE `ssmCnode`.Todo
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `content`   VARCHAR(255) NOT NULL,
    `completed` BIT(1) NOT NULL DEFAULT 0,
    `username` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `ssmCnode`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `avatar` VARCHAR(100) NOT NULL,
  `role` ENUM('admin', 'guest', 'normal') NOT NULL,
  `source` INT NOT NULL DEFAULT 0,
  `note` VARCHAR(255) NOT NULL DEFAULT '这家伙很懒，什么个性签名都没有留下。',
  `createdTime` BIGINT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `ssmCnode`.`Topic` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `content` text NOT NULL,
    `title` VARCHAR(45) NOT NULL,
    `deleted` BIT(1) NOT NULL DEFAULT 0,
    `createdTime` BIGINT NOT NULL,
    `updatedTime` BIGINT NOT NULL,
    `userId` INT NOT NULL,
    `lastReplyUserId` INT,
    `viewCount` INT NOT NULL DEFAULT 0,
    `replyCount` INT NOT NULL DEFAULT 0,
    `tab` VARCHAR(45) NOT NULL,
    `top` BIT(1) NOT NULL DEFAULT 0,
    `good` BIT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`));

CREATE TABLE `ssmCnode`.`Comment` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `content` text NULL,
    `deleted` BIT(1) NULL DEFAULT 0,
    `createdTime` BIGINT NULL,
    `updatedTime` BIGINT NULL,
    `userId` INT NOT NULL,
    `topicId` INT NULL,
    `floor` INT NULL,
    PRIMARY KEY (`id`));

CREATE TABLE `ssmCnode`.`Message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `senderId` INT NOT NULL,
  `readerId` INT NOT NULL,
  `topicId` INT NOT NULL,
  `read` BIT(1) NULL DEFAULT 0,
  `createdTime` BIGINT NULL,
  `type` ENUM('reply', 'mark') NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `ssmCnode`.`TopicCollection` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `userId` INT NOT NULL,
    `topicId` INT NOT NULL,
    `createdTime` BIGINT NULL,
    PRIMARY KEY (`id`));
# TopicCollection 联合索引
ALTER TABLE `ssmCnode`.`TopicCollection`
ADD UNIQUE INDEX `userId_topicId` (`userId` ASC, `topicId` ASC);



