-- CREATE DATABASE ProNet;
-- BEGIN TRANSACTION;

-- Drop tables in correct order (child tables first)
DROP TABLE IF EXISTS user_skills CASCADE;
DROP TABLE IF EXISTS projects CASCADE;
DROP TABLE IF EXISTS experience CASCADE;
DROP TABLE IF EXISTS education CASCADE;
DROP TABLE IF EXISTS user_profiles CASCADE;
DROP TABLE IF EXISTS contacts CASCADE;
DROP TABLE IF EXISTS professions CASCADE;
DROP TABLE IF EXISTS profession_titles CASCADE;
DROP TABLE IF EXISTS profession_categories CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS skills CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Users: basic user account info
CREATE TABLE users (
    user_id          SERIAL PRIMARY KEY,
    first_name       VARCHAR(100) NOT NULL,
    last_name        VARCHAR(100) NOT NULL,
    email            VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    profile_picture BYTEA,  -- store image as bytes
    profile_picture_content_type VARCHAR(100),  -- MIME type, e.g., image/png    role             VARCHAR(100),
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Locations table - now independent
CREATE TABLE locations (
    id               SERIAL PRIMARY KEY,
    country          VARCHAR(100) NOT NULL,
    state            VARCHAR(100),
    city             VARCHAR(100),
    user_id          INTEGER NOT NULL,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_location_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Contacts table
CREATE TABLE contacts (
    id               SERIAL PRIMARY KEY,
    phone            VARCHAR(20),
    email            VARCHAR(100) NOT NULL UNIQUE,
    linked_in        VARCHAR(200) UNIQUE,
    user_id          INTEGER NOT NULL,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_contact_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Profession categories
CREATE TABLE profession_categories (
    id               SERIAL PRIMARY KEY,
    category_name    VARCHAR(100) NOT NULL,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Profession titles
CREATE TABLE profession_titles (
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(100) NOT NULL,
    category_id      INTEGER,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_professiontitle_category FOREIGN KEY (category_id) REFERENCES profession_categories(id) ON DELETE SET NULL
);

-- Professions table
CREATE TABLE professions (
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(100),
    company_name     VARCHAR(100),
    years_of_experience INTEGER,
    user_id          INTEGER NOT NULL,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_profession_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- User profiles
CREATE TABLE user_profiles (
    profile_id       SERIAL PRIMARY KEY,
    user_id          INTEGER NOT NULL,
    bio              TEXT,                -- short biography
    location_id      INTEGER,             -- references locations table
    phone            VARCHAR(50),
    date_of_birth    DATE,
    website_url      VARCHAR(512),
    linkedin_url     VARCHAR(512),
    github_url       VARCHAR(512),
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_userprofile_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT FK_userprofile_location FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE SET NULL
);

-- Education
CREATE TABLE education (
    education_id     SERIAL PRIMARY KEY,
    user_id          INTEGER NOT NULL,
    institution      VARCHAR(255) NOT NULL,
    degree           VARCHAR(255),
    field_of_study   VARCHAR(255),
    start_date       DATE,
    end_date         DATE,
    grade            VARCHAR(50),
    description      TEXT,           -- optional: more details
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_education_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Experience
CREATE TABLE experience (
    experience_id    SERIAL PRIMARY KEY,
    user_id          INTEGER NOT NULL,
    company_name     VARCHAR(255) NOT NULL,
    title            VARCHAR(255) NOT NULL,
    start_date       DATE NOT NULL,
    end_date         DATE,           -- nullable if current job
    location         VARCHAR(255),
    is_current       BOOLEAN DEFAULT FALSE,
    description      TEXT,           -- optional: what you did, responsibilities
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_experience_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Skills
CREATE TABLE skills (
    skill_id         SERIAL PRIMARY KEY,
    name             VARCHAR(100) NOT NULL UNIQUE,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- User Skills (many-to-many)
CREATE TABLE user_skills (
    user_skill_id    SERIAL PRIMARY KEY,
    user_id          INTEGER NOT NULL,
    skill_id         INTEGER NOT NULL,
    level            VARCHAR(50),         -- e.g. Beginner / Intermediate / Advanced or numeric
    years_experience DECIMAL(4,2),        -- optional
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_userskill_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT FK_userskill_skill FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE,
    CONSTRAINT UQ_user_skill UNIQUE(user_id, skill_id)
);

-- Projects
CREATE TABLE projects (
    project_id       SERIAL PRIMARY KEY,
    user_id          INTEGER NOT NULL,
    title            VARCHAR(255) NOT NULL,
    description      TEXT,
    url              VARCHAR(512),         -- link if any
    start_date       DATE,
    end_date         DATE,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_project_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

