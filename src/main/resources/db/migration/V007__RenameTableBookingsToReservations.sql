alter table bookings rename to reservations;
alter table bookings_rooms rename to reservations_rooms;
alter table reservations_rooms rename column booking_id to reservation_id;