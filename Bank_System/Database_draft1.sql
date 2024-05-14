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


create table Transactions(
    account_no int references customer(account_no),
    balance float,
);

create table AccountHistory(
    transaction_id int IDENTITY(1,1) primary key,
    account_no int REFERENCES Customer(account_no),
    value float,
    date DATETIME DEFAULT getdate()
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

