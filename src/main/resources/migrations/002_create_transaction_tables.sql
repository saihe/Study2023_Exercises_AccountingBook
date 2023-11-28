create table if not exists accounting (
  id uuid not null default random_uuid() primary key,
  years char(4) not null,
  months char(2) not null,
  dates char(2) not null,
  accountingTypeId uuid not null references accountingType(id),
  accountingSubjectId uuid not null references accountingSubject(id),
  amount numeric not null default 0,
  createdAt timestamp with time zone not null default current_timestamp(),
  updatedAt timestamp with time zone not null default current_timestamp() on update current_timestamp()
);