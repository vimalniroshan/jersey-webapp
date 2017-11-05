-- drop table if exists hibernate_sequence;

drop table if exists gender_xref;
create table gender_xref (
  gender varchar(20) primary key
);

insert into gender_xref values ('MALE'), ('FEMALE');

drop table if exists project;
create table project (
  id int not null,
  name varchar(200) not null,
  
  primary key (id)
);


drop table if exists employee;
create table employee (
   id int not null,
   name varchar(200) not null,
   dob timestamp not null,
   email varchar(255),
   gender varchar(20) not null,
   project_id int not null,
   
   primary key (id),
   foreign key (gender) references gender_xref(gender),
   foreign key (project_id) references project(id)
);
