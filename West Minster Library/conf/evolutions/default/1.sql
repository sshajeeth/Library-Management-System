# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table addbooks (
  isbn                          varchar(255) not null,
  title                         varchar(255),
  publisher                     varchar(255),
  published_date                varchar(255),
  no_of_pages                   integer not null,
  sector                        varchar(255),
  author                        varchar(255),
  reader                        varchar(255),
  constraint pk_addbooks primary key (isbn)
);

create table borroweditems (
  readername                    varchar(255) not null,
  isbn                          varchar(255),
  date                          varchar(255),
  time                          varchar(255),
  due                           varchar(255),
  reservation                   integer not null,
  constraint pk_borroweditems primary key (readername)
);

create table adddvd (
  isbn                          varchar(255) not null,
  title                         varchar(255),
  producer                      varchar(255),
  actor                         varchar(255),
  published_date                varchar(255),
  sector                        varchar(255),
  subtitles                     varchar(255),
  languages                     varchar(255),
  reader                        varchar(255),
  constraint pk_adddvd primary key (isbn)
);

create table reader (
  readername                    varchar(255) not null,
  mobile_number                 varchar(255),
  email                         varchar(255),
  constraint pk_reader primary key (readername)
);


# --- !Downs

drop table if exists addbooks;

drop table if exists borroweditems;

drop table if exists adddvd;

drop table if exists reader;

