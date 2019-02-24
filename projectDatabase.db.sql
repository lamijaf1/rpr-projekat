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
INSERT INTO "materials" VALUES (2,'lecture1','math','lecture',0);
INSERT INTO "materials" VALUES (3,'group1','math','groups',1);
INSERT INTO "materials" VALUES (6,'Mis druga parc.pdf','Physics','labs',1);
INSERT INTO "materials" VALUES (7,'group2','Physics','group',1);
INSERT INTO "materials" VALUES (8,'lab1','Physics','lab',1);
INSERT INTO "materials" VALUES (9,'124408359-Impulsna-Elektronika-Zbirka-Zadataka-z-Pasic-1979.pdf','math','groups',1);
INSERT INTO "notifications" VALUES (1,2,'No class today','24.2.2019.');
INSERT INTO "notifications" VALUES (2,2,'On monday quiz','24.2.2019.');
INSERT INTO "notifications" VALUES (3,1,'Dobrodošli, nema novih obavijesti','02.02.2019.');
INSERT INTO "subjects" VALUES (1,'Physics','undergraduate',1);
INSERT INTO "subjects" VALUES (2,'math','undergraduate',1);
INSERT INTO "subjects" VALUES (3,'CHP','master',1);
INSERT INTO "subjects" VALUES (4,'Machine Learning','master',1);
INSERT INTO "subjects" VALUES (5,'PHD thesis','phd',1);
INSERT INTO "person" VALUES (1,'lamija','lamija','lamija',0);
INSERT INTO "person" VALUES (15,'benjo','benjo','benjo',0);
COMMIT;
