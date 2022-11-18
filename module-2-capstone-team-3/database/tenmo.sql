
BEGIN TRANSACTION;
DROP TABLE IF EXISTS tenmo_user, account, transfer;
DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;
-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;
CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);
-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;
CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);
-- Sequence to start account_id values at 3001 instead of 1
CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;
CREATE TABLE transfer (
	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
	sending_user_id int NOT NULL,
	receiving_user_id int NOT NULL,
	transfer_amount decimal(13, 2) NOT NULL,
	status varchar(50) NOT NULL,
	CONSTRAINT FK_stransfer_tenmo_user FOREIGN KEY (sending_user_id) REFERENCES tenmo_user (user_id),
	CONSTRAINT FK_rtransfer_tenmo_user FOREIGN KEY (receiving_user_id) REFERENCES tenmo_user (user_id),
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id)
	);

INSERT INTO tenmo_user(username, password_hash) VALUES ('user01', 'pass');
INSERT INTO tenmo_user(username, password_hash) VALUES ('user02', 'pass');

INSERT INTO account(user_id, balance) VALUES (1001, 1000);
INSERT INTO account(user_id, balance) VALUES (1002, 0);

INSERT INTO transfer(sending_user_id, receiving_user_id, transfer_amount, status) VALUES (1001, 1002, 100, 'APPROVED');

COMMIT;