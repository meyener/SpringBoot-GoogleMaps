# SpringBoot-GoogleMaps

This project provides to find and save restaurant which one in radius by SpringBoot

for create restaurant table postgre sql code :

CREATE TABLE restaurants (
  id serial PRIMARY KEY,
  lat double precision,
  lng double precision,
  restaurant_id varchar(255),
  name varchar(255),
  types varchar(255),
  vicinity varchar(255)
);
