create table bookings (
    id uuid primary key,
    check_in_date timestamp not null,
    check_out_date timestamp not null,
    status varchar(20) not null,
    total_price numeric(10, 2) not null
);

create table bookings_rooms (
    id uuid primary key,
    number varchar(5) not null,
    daily_rate numeric(10, 2) not null,
    booking_id uuid not null,
    foreign key (booking_id) references bookings (id) on delete cascade
)