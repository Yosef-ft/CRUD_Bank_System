create database Bank;

create table Customer(
    first_name varchar(30),
    last_name varchar(30),
    age int, 
    gender varchar(7),
    email varchar(50),
    tell_no int,
    password varchar(50),
    account_no int primary key
);

alter table Customer
add account_type VARCHAR(10);


create table AccountBalance(
    account_no int references customer(account_no),
    balance float,
);



CREATE TABLE Transactions (
    transaction_id INT IDENTITY(1,1) PRIMARY KEY,
    sender_account_no INT,
    receiver_account_no INT,
    amount FLOAT,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_sender_account_no FOREIGN KEY (sender_account_no) REFERENCES Customer(account_no),
    CONSTRAINT FK_receiver_account_no FOREIGN KEY (receiver_account_no) REFERENCES Customer(account_no)
);


create table DeletedAccount(
    account_no int REFERENCES Customer(account_no),
    date DATETIME DEFAULT GETDATE(),
    first_name varchar(30),
    last_name varchar(30),
    email varchar(50)
);


create table Adimstrator(
    first_name varchar(30),
    last_name varchar(30),
    age int, 
    gender varchar(7),
    email varchar(50),
    tell_no int,
    password varchar(50),
);

