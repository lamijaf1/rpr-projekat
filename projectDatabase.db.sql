BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "materials" (
	"id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"name_material"	TEXT,
	"subject"	TEXT,
	"type"	TEXT,
	"visible"	INTEGER
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
INSERT INTO "materials" VALUES (1,'lab1','math','lab',1);
INSERT INTO "materials" VALUES (2,'lab2','math','lab',1);
INSERT INTO "materials" VALUES (3,'lecture1','math','lecture',1);
INSERT INTO "materials" VALUES (4,'group1','math','group',1);
INSERT INTO "materials" VALUES (5,'otk.pdf','Physics','lectures',1);
INSERT INTO "materials" VALUES (6,'otk.pdf','Physics','labs',1);
INSERT INTO "notifications" VALUES (1,1,'no class today','22.10.2012.');
INSERT INTO "notifications" VALUES (3,1,'notification','11.1.2011.');
INSERT INTO "subjects" VALUES (1,'Physics','undergraduate',1);
INSERT INTO "subjects" VALUES (2,'math','undergraduate',1);
INSERT INTO "subjects" VALUES (3,'CHP','master',1);
INSERT INTO "subjects" VALUES (4,'Machine Learning','master',1);
INSERT INTO "subjects" VALUES (5,'PHD thesis','phd',1);
INSERT INTO "person" VALUES (1,'lamija','lamija','lamija',0);
COMMIT;
