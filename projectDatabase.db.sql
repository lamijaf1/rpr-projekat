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
INSERT INTO "materials" VALUES (1,'lab1','RPR','lab',1);
INSERT INTO "materials" VALUES (2,'lecture1','RPR','lecture',1);
INSERT INTO "materials" VALUES (3,'group1','RPR','group',1);
INSERT INTO "materials" VALUES (4,'lab1','OR','lab',1);
INSERT INTO "materials" VALUES (5,'lecture1','OR','lecture',1);
INSERT INTO "materials" VALUES (6,'group1','OR','group',1);
INSERT INTO "materials" VALUES (7,'lab2','OR','lab',0);
INSERT INTO "notifications" VALUES (1,49,'no class today','24.02.2019.');
INSERT INTO "notifications" VALUES (2,49,'quiz starts tomorrow','25.02.2019.');
INSERT INTO "subjects" VALUES (49,'RPR','undergraduate',49);
INSERT INTO "subjects" VALUES (152,'Database','master',6);
INSERT INTO "subjects" VALUES (182,'DOS','master',7);
INSERT INTO "subjects" VALUES (241,'Multivariable systems','phd',7);
INSERT INTO "person" VALUES (6,'professor','professor','professor',1);
INSERT INTO "person" VALUES (7,'mzukic','mediha','Mediha Zukic',1);
INSERT INTO "person" VALUES (8,'bdzanko1','benjamin','Benjamin Dzanko',0);
INSERT INTO "person" VALUES (9,'hsarajlic1','habib','Habib Sarajlic',0);
INSERT INTO "person" VALUES (49,'vljubovic','vedran','Vedran Ljubovic',1);
COMMIT;
