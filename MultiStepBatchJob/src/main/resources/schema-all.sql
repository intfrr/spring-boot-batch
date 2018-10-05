-- Autogenerated: do not edit this file

CREATE TABLE BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;

CREATE TABLE BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	START_TIME TIMESTAMP DEFAULT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	TYPE_CD VARCHAR(6) NOT NULL ,
	KEY_NAME VARCHAR(100) NOT NULL ,
	STRING_VAL VARCHAR(250) ,
	DATE_VAL TIMESTAMP DEFAULT NULL ,
	LONG_VAL BIGINT ,
	DOUBLE_VAL DOUBLE PRECISION ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	START_TIME TIMESTAMP NOT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT LONGVARCHAR ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT LONGVARCHAR ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION_SEQ (
	ID BIGINT IDENTITY
);
CREATE TABLE BATCH_JOB_EXECUTION_SEQ (
	ID BIGINT IDENTITY
);
CREATE TABLE BATCH_JOB_SEQ (
	ID BIGINT IDENTITY
);


-- Autogenerated: do not edit this file
DROP TABLE  BATCH_STAGING_SEQ IF EXISTS;
DROP TABLE  TRADE_SEQ IF EXISTS;
DROP TABLE  CUSTOMER_SEQ IF EXISTS;
DROP TABLE  BATCH_STAGING IF EXISTS;
DROP TABLE  TRADE IF EXISTS;
DROP TABLE  CUSTOMER IF EXISTS;
DROP TABLE  PLAYERS IF EXISTS;
DROP TABLE  GAMES IF EXISTS;
DROP TABLE  PLAYER_SUMMARY IF EXISTS;
DROP TABLE  ERROR_LOG IF EXISTS;

-- Autogenerated: do not edit this file

CREATE TABLE CUSTOMER_SEQ (
	ID BIGINT IDENTITY
);
INSERT INTO CUSTOMER_SEQ (ID) values (5);
CREATE TABLE BATCH_STAGING_SEQ (
	ID BIGINT IDENTITY
);
INSERT INTO BATCH_STAGING_SEQ (ID) values (0);
CREATE TABLE TRADE_SEQ (
	ID BIGINT IDENTITY
);
INSERT INTO TRADE_SEQ (ID) values (0);

CREATE TABLE BATCH_STAGING  (
	ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,  
	JOB_ID BIGINT NOT NULL,
	VALUE LONGVARBINARY NOT NULL,
	PROCESSED CHAR(1) NOT NULL
) ;

CREATE TABLE TRADE  (
	ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,  
	VERSION BIGINT ,
	ISIN VARCHAR(45) NOT NULL, 
	QUANTITY BIGINT ,
	PRICE DECIMAL(8,2) , 
	CUSTOMER VARCHAR(45) 
) ;
 
CREATE TABLE CUSTOMER (
	ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,  
	VERSION BIGINT ,
	NAME VARCHAR(45) ,
	CREDIT DECIMAL(10,2) 
) ;
 
INSERT INTO CUSTOMER (ID, VERSION, NAME, CREDIT) VALUES (1, 0, 'customer1', 100000);
INSERT INTO CUSTOMER (ID, VERSION, NAME, CREDIT) VALUES (2, 0, 'customer2', 100000);
INSERT INTO CUSTOMER (ID, VERSION, NAME, CREDIT) VALUES (3, 0, 'customer3', 100000);
INSERT INTO CUSTOMER (ID, VERSION, NAME, CREDIT) VALUES (4, 0, 'customer4', 100000);

CREATE TABLE PLAYERS (
	PLAYER_ID CHAR(8) NOT NULL PRIMARY KEY,  
	LAST_NAME VARCHAR(35) NOT NULL,
	FIRST_NAME VARCHAR(25) NOT NULL,
	POS VARCHAR(10) ,
	YEAR_OF_BIRTH BIGINT NOT NULL,
	YEAR_DRAFTED BIGINT NOT NULL
) ;

CREATE TABLE GAMES (
   PLAYER_ID CHAR(8) NOT NULL,
   YEAR_NO      BIGINT NOT NULL,
   TEAM      CHAR(3) NOT NULL,
   WEEK      BIGINT NOT NULL,
   OPPONENT  CHAR(3) ,
   COMPLETES BIGINT ,
   ATTEMPTS  BIGINT ,
   PASSING_YARDS BIGINT ,
   PASSING_TD    BIGINT ,
   INTERCEPTIONS BIGINT ,
   RUSHES BIGINT ,
   RUSH_YARDS BIGINT ,
   RECEPTIONS BIGINT ,
   RECEPTIONS_YARDS BIGINT ,
   TOTAL_TD BIGINT 
) ;

CREATE TABLE PLAYER_SUMMARY  (
		  ID CHAR(8) NOT NULL, 
		  YEAR_NO BIGINT NOT NULL,
		  COMPLETES BIGINT NOT NULL , 
		  ATTEMPTS BIGINT NOT NULL , 
		  PASSING_YARDS BIGINT NOT NULL , 
		  PASSING_TD BIGINT NOT NULL , 
		  INTERCEPTIONS BIGINT NOT NULL , 
		  RUSHES BIGINT NOT NULL , 
		  RUSH_YARDS BIGINT NOT NULL , 
		  RECEPTIONS BIGINT NOT NULL , 
		  RECEPTIONS_YARDS BIGINT NOT NULL , 
		  TOTAL_TD BIGINT NOT NULL
) ;

CREATE TABLE ERROR_LOG  (
		JOB_NAME CHAR(20) ,
		STEP_NAME CHAR(20) ,
		MESSAGE VARCHAR(300) NOT NULL
) ;