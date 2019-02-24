BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "materials" (
	"id"	INTEGER,
	"name_material"	TEXT,
	"subject"	TEXT,
	"type"	TEXT,
	"visible"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "notifications" (
	"id"	INTEGER,
	"subject"	INTEGER,
	"text"	INTEGER,
	"date"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("subject") REFERENCES "subjects"("id")
);
CREATE TABLE IF NOT EXISTS "subjects" (
	"id"	INTEGER,
	"name"	TEXT,
	"program"	TEXT,
	"professor"	INTEGER,
	FOREIGN KEY("professor") REFERENCES "person"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "person" (
	"id"	INTEGER,
	"username"	TEXT,
	"password"	TEXT,
	"full_name"	TEXT,
	"is_professor"	INTEGER,
	PRIMARY KEY("id")
);
INSERT INTO "materials" VALUES (2,'lecture1','math','lecture',1);
INSERT INTO "materials" VALUES (3,'group1','math','groups',1);
INSERT INTO "materials" VALUES (4,'lab1','math','lab',1);
INSERT INTO "materials" VALUES (5,'lab2','math','lab',1);
INSERT INTO "notifications" VALUES (1,2,'No class today','24.2.2019.');
INSERT INTO "notifications" VALUES (2,2,'On monday quiz','24.2.2019.');
INSERT INTO "subjects" VALUES (1,'Physics','undergraduate',1);
INSERT INTO "subjects" VALUES (2,'math','undergraduate',1);
INSERT INTO "subjects" VALUES (3,'CHP','master',1);
INSERT INTO "subjects" VALUES (4,'Machine Learning','master',1);
INSERT INTO "subjects" VALUES (5,'PHD thesis','phd',1);
INSERT INTO "person" VALUES (1,'lamija','lamija','lamija',0);
COMMIT;
