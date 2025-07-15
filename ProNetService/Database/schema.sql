-- CREATE DATABASE ProNet;
-- BEGIN TRANSACTION;
 DROP TABLE IF EXISTS users, contacts, locations, professions, profession_categories, profession_titles CASCADE;

CREATE TABLE IF NOT EXISTS users (
	id serial,
	first_name varchar(100) not null,
  	last_name varchar(100) not null,
	email varchar(100) not null unique,
	password varchar(100) not null,
	role varchar(100) not null,
	CONSTRAINT PK_users PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contacts (
	id serial,
	phone varchar(20),
	email varchar(100) not null unique,
	linked_in varchar(200) unique,
	user_id int not null,
	CONSTRAINT PK_contact PRIMARY KEY (id),
	CONSTRAINT FK_contact_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS locations (
	id serial,
	country varchar(100) not null,
	state varchar(100),
	city varchar(100),
	user_id int not null,
	CONSTRAINT PK_location PRIMARY KEY (id),
	CONSTRAINT FK_location_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS professions (
	id serial,
	title varchar(100),
	company_name varchar(100),
	years_of_experience int,
	user_id int not null,
	CONSTRAINT PK_profession PRIMARY KEY (id),
	CONSTRAINT FK_profession_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS profession_categories (
	id serial,
	category_name varchar(100),
	CONSTRAINT PK_profession_category PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS profession_titles (
	id serial,
	title varchar(100),
	category_id int,
	CONSTRAINT PK_profession_title PRIMARY KEY (id),
	CONSTRAINT FK_professiontitle_category FOREIGN KEY (category_id) REFERENCES profession_categories(id)
);

-- COMMIT TRANSACTION;
-- ROLLBACK;

