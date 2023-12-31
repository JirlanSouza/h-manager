create table users (
    id uuid primary key,
    first_name varchar(50) not null,
    last_name varchar(100) not null,
    email varchar(100) unique not null,
    password varchar(255) not null,
    user_type varchar(20) not null
)