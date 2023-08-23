create table customers (
    id uuid primary key,
    name varchar(60) not null,
    cpf varchar(15) unique not null,
    address_street varchar(100),
    address_number varchar(10),
    address_neighborhood varchar(100),
    address_zip_code varchar(9),
    address_city varchar(60),
    address_state varchar(60),
    address_country varchar(20)
)