DROP TABLE IF EXISTS users CASCADE;

DROP TABLE IF EXISTS user_info CASCADE;

DROP TABLE IF EXISTS authorities CASCADE;

DROP TABLE IF EXISTS user_authority CASCADE;

DROP TABLE IF EXISTS product CASCADE;

CREATE TABLE authorities (
    id bigint generated by default as identity,
    username varchar(255) not null,
    authority varchar(20) not null
);

CREATE TABLE user_authority (
    user_id bigint not null,
    user_authorities varchar(255)
);

CREATE TABLE IF NOT EXISTS users (
    id bigint generated by default as identity,
    password varchar(255),
    username varchar(255),
    enabled INT not null,
    primary key (id)
);



create table user_info (
    id bigint generated by default as identity,
    algorithm varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

CREATE TABLE IF NOT EXISTS product (
     id bigint generated by default as identity,
     name varchar(255),
     price int,
     currency varchar(255),
     primary key (id)
);

CREATE TABLE user_token (
    id bigint generated by default as identity,
    identifier varchar(255) not null,
    token varchar(255) not null,
    primary key (id)
);
