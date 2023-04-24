DROP DATABASE IF EXISTS nekodb;
CREATE DATABASE nekodb;
USE nekodb;

CREATE TABLE users (
    username varchar(16) not null,
    email varchar(128) not null,
    password varchar(60) not null,
    -- password must be 60 because of bcrypt encryption
    primary key (email)
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE savedCats (
    cat_id int(32) not null primary key,
    email varchar(128) not null,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_email
        foreign key(email)
        references users(email)
);

CREATE TABLE posts (
    post_id int auto_increment not null primary key,
    email varchar(128) not null,
    title varchar(128) not null,
    caption varchar(254) not null,
    cat_id varchar(32) not null,
    ratings float not null,
    likes int not null,
    date varchar(16) not null,
    imageUUID varchar(8) not null,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_email
        foreign key(email)
        references users(email)
        on delete cascade
);

CREATE TABLE likedPosts (
    email varchar(128) not null,
    post_id int not null,
    primary key(email, post_id)
)

CREATE TABLE vet_clinics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    latitude DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL,
    phone VARCHAR(20),
    website VARCHAR(255)
);
