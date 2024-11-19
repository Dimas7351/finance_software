CREATE TABLE "BankIntegration" (
    "integrationID" SERIAL,
    "userID" INTEGER NOT NULL,
    "bankName" VARCHAR(100) NOT NULL,
    "keyAPI" VARCHAR(255) NOT NULL,
    "status" BOOLEAN DEFAULT true,
    PRIMARY KEY("integrationID"),
    FOREIGN KEY("userID") REFERENCES "User"("userID")
        ON UPDATE NO ACTION ON DELETE NO ACTION
);