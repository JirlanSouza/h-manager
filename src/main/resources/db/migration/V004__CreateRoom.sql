create table rooms (
    id uuid primary key,
    number varchar(5) not null unique,
    double_beds int not null,
    single_beds int not null,
    daily_rate numeric(15,6) not null,
    available boolean not null
);