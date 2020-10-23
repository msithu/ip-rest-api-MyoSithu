

-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------


CREATE TABLE "CIDR" ("ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "IP" VARCHAR(20), "STATUS" VARCHAR(20));

-- ----------------------------------------------
-- DDL Statements for keys
-- ----------------------------------------------

-- primary/unique
ALTER TABLE "CIDR" ADD CONSTRAINT "SQL120325130144010" PRIMARY KEY ("ID");


