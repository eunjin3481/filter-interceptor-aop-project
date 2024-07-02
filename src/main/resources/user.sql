use pnp;

create table user (
	id varchar(100) primary key,
    pw varchar(100) not null,
    name varchar(100) not null,
    admin boolean default false
)

