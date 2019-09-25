SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
id SERIAL PRIMARY KEY,
name VARCHAR,
description VARCHAR,
size VARCHAR
);

CREATE TABLE IF NOT EXISTS users (
 id SERIAL PRIMARY KEY,
 name VARCHAR,
 title VARCHAR,
 role VARCHAR
);

CREATE TABLE IF NOT EXISTS depnews (
 id SERIAL PRIMARY KEY,
 content VARCHAR,
 writtenby VARCHAR,
 rating VARCHAR,
 departmentid INTEGER,
 createdat BIGINT
);

CREATE TABLE IF NOT EXISTS departments_users(
 id SERIAL PRIMARY KEY,
userid INTEGER,
departmentid INTEGER
);

