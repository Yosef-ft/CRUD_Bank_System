create database Bank;
use Bank;

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
    sender_account_no INT REFERENCES Customer(account_no),
    receiver_account_no INT REFERENCES Customer (account_no),
    amount FLOAT,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
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

-- UPGRADED DATABASE FOR POSTGRES

-- create database Bank;

-- create sequence account_gen
--   START 100000;

-- create table Customer(
--     first_name varchar(30) not null,
--     last_name varchar(30) not null,
--     age int constraint Chk_age check(age > 18), 
--     gender varchar(7),
--     email varchar(50) constraint Chk_email check(email like '%_@_%._%'),
--     tell_no varchar(15) constraint Chk_tellNo check(LENGTH(tell_no) >= 10),
--     password varchar(50) constraint Chk_pass check(
--       Length(password) >= 8
--       and password like '%[A-Z]%'
--       and password like '%[a-z]%'
--       and password like '%[0-9]%'
--       and password like '%[!@#$%^&*(),.?":{}|<>]%'
--     ),
--     account_type varchar(50),
--     account_no int primary key default nextval('account_gen')
-- );

-- create table AccountBalance(
--     account_no int references customer(account_no),
--     balance float
-- );


-- CREATE TABLE Transactions (
--     transaction_id serial PRIMARY KEY,
--     sender_account_no INT REFERENCES Customer(account_no),
--     receiver_account_no INT REFERENCES Customer (account_no),
--     amount FLOAT,
--     transaction_date timestamp DEFAULT CURRENT_TIMESTAMP
-- );


-- create table DeletedAccount(
--     account_no int REFERENCES Customer(account_no),
--     date timestamp DEFAULT current_timestamp,
--     first_name varchar(30),
--     last_name varchar(30),
--     email varchar(50)
-- );

-- create table Adimstrator(
--     first_name varchar(30) not null,
--     last_name varchar(30) not null,
--     age int constraint Chk_age check(age > 18), 
--     gender varchar(7),
--     email varchar(50) constraint Chk_email check(email like '%_@_%._%'),
--     tell_no varchar(15) constraint Chk_tellNo check(LENGTH(tell_no) >= 10),
--     password varchar(50) constraint Chk_pass check(
--       Length(password) >= 8
--       and password like '%[A-Z]%'
--       and password like '%[a-z]%'
--       and password like '%[0-9]%'
--       and password like '%[!@#$%^&*(),.?":{}|<>]%'
--     )
-- );


