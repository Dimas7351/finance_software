CREATE TABLE "User" (
	"userID" SERIAL,
	"name" VARCHAR(100) NOT NULL,
	"email" VARCHAR(100) NOT NULL UNIQUE,
	"password" VARCHAR(255) NOT NULL,
	"currency" VARCHAR(10) DEFAULT 'USD',
	PRIMARY KEY("userID")
);


CREATE TABLE "Category" (
	"categoryID" SERIAL,
	"userID" INTEGER NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"color" VARCHAR(20),
	PRIMARY KEY("categoryID")
);


CREATE TABLE "Transaction" (
	"transactionID" SERIAL,
	"userID" INTEGER NOT NULL,
	"categoryID" INTEGER,
	"amount" NUMERIC(12,2) NOT NULL,
	"type" VARCHAR(10) CHECK("[object Object]" IN income AND expense),
	"date" TIMESTAMP NOT NULL,
	PRIMARY KEY("transactionID")
);


CREATE TABLE "BankIntegration" (
	"integrationID" SERIAL,
	"userID" INTEGER NOT NULL,
	"bankName" VARCHAR(100) NOT NULL,
	"keyAPI" VARCHAR(255) NOT NULL,
	"status" BOOLEAN DEFAULT true,
	PRIMARY KEY("integrationID")
);


ALTER TABLE "Category"
ADD FOREIGN KEY("userID") REFERENCES "User"("userID")
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "Transaction"
ADD FOREIGN KEY("userID") REFERENCES "User"("userID")
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "Transaction"
ADD FOREIGN KEY("categoryID") REFERENCES "Category"("categoryID")
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "BankIntegration"
ADD FOREIGN KEY("userID") REFERENCES "User"("userID")
ON UPDATE NO ACTION ON DELETE NO ACTION;