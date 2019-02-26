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
INSERT INTO "materials" VALUES (1,'izvjestaj','RPR','group',1);
INSERT INTO "materials" VALUES (2,'main.pdf','RPR','lecture',1);
INSERT INTO "materials" VALUES (3,'otk.pdf','RPR','lab',1);
INSERT INTO "materials" VALUES (4,'group1.pdf','RPR','group',1);
INSERT INTO "materials" VALUES (5,'IOt.pdf','RPR','lecture',1);
INSERT INTO "notifications" VALUES (1,49,'No class today','12.02.2018.');
INSERT INTO "notifications" VALUES (2,49,'Tomorrow starts quiz1','21.02.2018.');
INSERT INTO "notifications" VALUES (3,49,'Tomorrow starts quiz2','27.03.2018.');
INSERT INTO "notifications" VALUES (4,49,'Results of exam are on courseware','30.04.2018.');
INSERT INTO "notifications" VALUES (5,152,'No class today','17.12.2018.');
INSERT INTO "notifications" VALUES (6,182,'No class today','23.08.2018.');
INSERT INTO "notifications" VALUES (7,152,'Happy New Year','31.12.2018.');
INSERT INTO "subjects" VALUES (49,'RPR','undergraduate',49);
INSERT INTO "subjects" VALUES (152,'Database','master',6);
INSERT INTO "subjects" VALUES (182,'DOS','master',7);
INSERT INTO "subjects" VALUES (241,'Multivariable systems','phd',7);
INSERT INTO "person" VALUES (1,'student','student','student',0);
INSERT INTO "person" VALUES (2,'lfazlija1','lamija','Lamija Fazlija',0);
INSERT INTO "person" VALUES (6,'professor','professor','professor',1);
INSERT INTO "person" VALUES (7,'mzukic','mediha','Mediha Zukic',1);
INSERT INTO "person" VALUES (49,'vljubovic','vedran','Vedran Ljubovic',1);
COMMIT;
