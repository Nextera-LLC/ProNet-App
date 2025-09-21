-- CREATE DATABASE ProNet;
-- BEGIN TRANSACTION;
 DROP TABLE IF EXISTS users, user_profiles, education, contacts, locations, professions, profession_categories, profession_titles CASCADE;

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

-- Users: basic user account info
CREATE TABLE users (
    user_id          SERIAL PRIMARY KEY,
    first_name       VARCHAR(100) NOT NULL,
    last_name        VARCHAR(100) NOT NULL,
    email            VARCHAR(255) NOT NULL UNIQUE,
    password_hash    VARCHAR(255) NOT NULL,
    profile_picture  VARCHAR(512),
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- About / Profile: additional info
CREATE TABLE user_profiles (
    profile_id       SERIAL PRIMARY KEY,
    user_id          BIGINT NOT NULL,
    bio              TEXT,                -- short biography
    location_id      INTEGER,             -- references locations table
    phone            VARCHAR(50),
    date_of_birth    DATE,
    website_url      VARCHAR(512),
    linkedin_url     VARCHAR(512),
    github_url       VARCHAR(512),
    -- any other "about" fields
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE SET NULL
);
-- Education: multiple entries per user
CREATE TABLE education (
    education_id     SERIAL PRIMARY KEY,
    user_id          BIGINT NOT NULL,
    institution      VARCHAR(255) NOT NULL,
    degree           VARCHAR(255),
    field_of_study   VARCHAR(255),
    start_date       DATE,
    end_date         DATE,
    grade            VARCHAR(50),
    description      TEXT,           -- optional: more details
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
-- Experience: multiple work-experience entries per user
CREATE TABLE experience (
    experience_id    SERIAL PRIMARY KEY,
    user_id          BIGINT NOT NULL,
    company_name     VARCHAR(255) NOT NULL,
    title            VARCHAR(255) NOT NULL,
    start_date       DATE NOT NULL,
    end_date         DATE,           -- nullable if current job
    location         VARCHAR(255),
    is_current       BOOLEAN DEFAULT FALSE,
    description      TEXT,           -- optional: what you did, responsibilities
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
-- Skills: master list of possible skills
CREATE TABLE skills (
    skill_id    SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE
);
-- UserSkills: many-to-many between users and skills (with proficiency maybe)
CREATE TABLE user_skills (
    user_skill_id    SERIAL PRIMARY KEY,
    user_id          BIGINT NOT NULL,
    skill_id         BIGINT NOT NULL,
    level            VARCHAR(50),         -- e.g. Beginner / Intermediate / Advanced or numeric
    years_experience DECIMAL(4,2),        -- optional (using DECIMAL instead of FLOAT for precision)
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE,
    UNIQUE(user_id, skill_id)
);
-- Projects: user's own projects
CREATE TABLE projects (
    project_id     SERIAL PRIMARY KEY,
    user_id        BIGINT NOT NULL,
    title          VARCHAR(255) NOT NULL,
    description    TEXT,
    url            VARCHAR(512),         -- link if any
    start_date     DATE,
    end_date       DATE,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);








-- COMMIT TRANSACTION;
-- ROLLBACK;

