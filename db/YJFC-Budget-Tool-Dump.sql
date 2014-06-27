PRAGMA foreign_keys=ON;
BEGIN TRANSACTION;
CREATE TABLE Users (
    GTID        INT         PRIMARY KEY,
    Password    CHAR(20)    NOT NULL,
    UserType    CHAR(5)     NOT NULL
);
INSERT INTO "Users" VALUES(555,'555','ADMIN');
CREATE TABLE Category (
    CatName     CHAR(25)    NOT NULL,
    PRIMARY KEY (CatName)
);
INSERT INTO "Category" VALUES('Public Relations Committee');
INSERT INTO "Category" VALUES('Armory Committee');
INSERT INTO "Category" VALUES('Executive Board');
INSERT INTO "Category" VALUES('Social Committee');
INSERT INTO "Category" VALUES('Tournament Committee');
CREATE TABLE BudgetItem (
    Name        CHAR(25)    NOT NULL,
    Category    CHAR(25)    NOT NULL,
    Year        INT         NOT NULL,
    EstCost     REAL,
    EstIncome   REAL,
    Priority    INT         NOT NULL,
    Explanation TEXT,
    PRIMARY KEY (Name, Category, Year),
    FOREIGN KEY (Category) REFERENCES Category(CatName)
);
INSERT INTO "BudgetItem" VALUES('FASET Table Fee','Public Relations Committee',14,50.0,0.0,3,'Necessary for the lifeblood of the club.');
INSERT INTO "BudgetItem" VALUES('FASET Flyers','Public Relations Committee',14,0.0,0.0,2,'');
INSERT INTO "BudgetItem" VALUES('Foils','Armory Committee',14,50.0,0.0,3,'');
INSERT INTO "BudgetItem" VALUES('FASET Table Fee','Public Relations Committee',13,50.0,0.0,3,'Necessary for the lifeblood of the club.');
INSERT INTO "BudgetItem" VALUES('FASET Flyers','Public Relations Committee',13,0.0,0.0,2,'');
INSERT INTO "BudgetItem" VALUES('Foils','Armory Committee',13,50.0,0.0,3,'');
CREATE TABLE reimbursement (
    "Name" CHAR(25) NOT NULL,
    "Category" CHAR(25) NOT NULL,
    "Year" INT NOT NULL,
    "Amt" REAL NOT NULL,
    "DateReq" TEXT NOT NULL,
    "DateRec" TEXT
);
INSERT INTO "reimbursement" VALUES('Foils','Armory Committee',14,50.0,'2014-06-27',NULL);
CREATE TABLE transactions (
    "Date" TEXT NOT NULL,
    "Name" CHAR(25) NOT NULL,
    "Category" CHAR(25) NOT NULL,
    "Year" INT NOT NULL,
    "GTID" INT NOT NULL,
    "Cost" REAL,
    "Earned" REAL,
    "Explanation" TEXT
);
INSERT INTO "transactions" VALUES('2014-06-27','FASET Table Fee','Public Relations Committee',14,555,50.0,0.0,'');
INSERT INTO "transactions" VALUES('2014-06-27','Foils','Armory Committee',14,555,50.0,0.0,'');
CREATE TABLE trueaccountstatus (
    "Date" TEXT,
    "ActualTotal" REAL NOT NULL
);
INSERT INTO "trueaccountstatus" VALUES('2014-06-27',1000.0);
CREATE TABLE sgaallocation (
    "Name" CHAR(25) NOT NULL,
    "Category" CHAR(25) NOT NULL,
    "Year" INT NOT NULL,
    "Requested" REAL NOT NULL DEFAULT (0),
    "Approved" REAL NOT NULL DEFAULT (0)
);
INSERT INTO "sgaallocation" VALUES('Foils','Armory Committee',14,100.0,50.0);
INSERT INTO "sgaallocation" VALUES('Foils','Armory Committee',13,150.0,100.0);
INSERT INTO "sgaallocation" VALUES('Temple','Tournament Committee',13,200.0,200.0);
INSERT INTO "sgaallocation" VALUES('Volunteer Open','Tournament Committee',14,200.0,200.0);
CREATE VIEW CurrentYear AS SELECT * FROM SgaAllocation WHERE Year=14;
CREATE VIEW PreviousYear AS SELECT * FROM SgaAllocation WHERE Year=13;
CREATE VIEW ProactiveYears AS
    SELECT CY.Name, CY.Category,
            PY.Requested AS PrevYearReq, PY.Approved AS PrevYearApp,
            CY.Requested AS CurrYearReq, CY.Approved AS CurrYearApp
        FROM CurrentYear AS CY LEFT OUTER JOIN PreviousYear AS PY
            ON CY.Name=PY.Name AND CY.Category=PY.Category;
CREATE VIEW "retroactiveyears" AS
SELECT PY.Name, PY.Category,
        PY.Requested AS PrevYearReq, PY.Approved AS PrevYearApp,
        CY.Requested AS CurrYearReq, CY.Approved AS CurrYearApp
    FROM PreviousYear AS PY LEFT OUTER JOIN CurrentYear AS CY
        ON CY.Name=PY.Name AND CY.Category=PY.Category;
COMMIT;
