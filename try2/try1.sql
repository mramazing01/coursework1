use main;

DROP TABLE IF EXISTS project;
create table project(
project_id int not null primary key auto_increment,
project_name varchar(100),
category int,
budget int
);

DROP TABLE IF EXISTS contractor;
create table contractor(
contractor_id int not null primary key auto_increment,
contractor_name varchar(100),
contractor_type varchar(100),
email varchar(100),
telephone varchar(11)
);

DROP TABLE IF EXISTS project_contractor;
create table project_contractor(
project_id int,
contractor_id int,
foreign key (project_id) references project(project_id),
foreign key (contractor_id) references contractor(contractor_id)
);

DROP TABLE IF EXISTS place;
create table place(
project_id int,
address varchar(100),
telephone varchar(11),
email varchar(100),
client_name varchar(100),
foreign key (project_id) references project(project_id)
);

select * from project
inner join project_contractor on project.project_id = project_contractor.project_id;

select * from contractor
inner join project_contractor on contractor.contractor_id = project_contractor.contractor_id;

select * from project
inner join place on project.project_id = place.project_id;
