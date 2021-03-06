create database VACCINE_TRACKER;
USE VACCINE_TRACKER;
CREATE TABLE JOB_DETAILS(
	JOB_ID INT PRIMARY KEY auto_increment,
    STATE_ID int NOT NULL,
    DISTRICT_ID int NOT NULL,
    VACCINE_TYPE varchar(25),
    ISACTIVE BOOL
);



CREATE TABLE SUBSCRIPTION(
	SUBSCRIPTION_ID INT PRIMARY KEY auto_increment,
    EMAIL VARCHAR(80) NOT NULL,
    JOB_ID INT NOT NULL,
    FOREIGN KEY (JOB_ID) REFERENCES JOB_DETAILS(JOB_ID),
    ISACTIVE BOOL
);

CREATE TABLE STATE(
	STATE_ID INT PRIMARY KEY,
    STATE_NAME VARCHAR(35) NOT NULL
);

CREATE TABLE DISTRICT(
	DISTRICT_ID INT PRIMARY KEY ,
    DISTRICT_NAME VARCHAR(35) NOT NULL
);
DESC JOB_DETAILS;
desc SUBSCRIPTION;
######################
DROP TABLE SUBSCRIPTION;
DROP TABLE JOB_DETAILS;
