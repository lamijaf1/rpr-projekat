BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `subjects` (
	`id`	INTEGER,
	`name`	TEXT,
	`program`	TEXT,
	`professor`	INTEGER,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`professor`) REFERENCES `person`(`id`)
);
INSERT INTO `subjects` VALUES (1,'Physics','undergraduate',1);
INSERT INTO `subjects` VALUES (2,'math','undergraduate',1);
INSERT INTO `subjects` VALUES (3,'CHP','master',1);
INSERT INTO `subjects` VALUES (4,'Machine Learning','master',1);
INSERT INTO `subjects` VALUES (5,'PHD thesis','phd',1);
CREATE TABLE IF NOT EXISTS `person` (
	`id`	INTEGER,
	`username`	TEXT,
	`password`	TEXT,
	`full_name`	TEXT,
	`is_professor`	INTEGER,
	PRIMARY KEY(`id`)
);
INSERT INTO `person` VALUES (1,'lamija','lamija','lamija',0);
CREATE TABLE IF NOT EXISTS `notifications` (
	`id`	INTEGER,
	`subject`	INTEGER,
	`text`	TEXT,
	PRIMARY KEY(`id`)
);
INSERT INTO `notifications` VALUES (1,'math','No classes today!!!');
INSERT INTO `notifications` VALUES (2,'math','Quiz on monday!');
INSERT INTO `notifications` VALUES (3,'physics','Midterm dates updated.');
CREATE TABLE IF NOT EXISTS `materials` (
	`id`	INTEGER,
	`name_material`	TEXT,
	`subject`	TEXT,
	`type`	TEXT,
	PRIMARY KEY(`id`)
);
INSERT INTO `materials` VALUES (1,'lab1','math','lab');
INSERT INTO `materials` VALUES (2,'lab2','math','lab');
INSERT INTO `materials` VALUES (3,'lecture1','math','lecture');
INSERT INTO `materials` VALUES (4,'group1','math','group');
COMMIT;
