create table if not exists accountingType (
  id uuid not null primary key,
  code int not null unique,
  name varchar(20) not null,
  createdAt timestamp with time zone not null default current_timestamp,
  updatedAt timestamp with time zone not null default current_timestamp
);

create table if not exists accountingSubject (
  id uuid not null primary key,
  code int not null unique,
  name varchar(20) not null,
  createdAt timestamp with time zone not null default current_timestamp,
  updatedAt timestamp with time zone not null default current_timestamp
);
