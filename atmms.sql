/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/12/3 12:19:09                           */
/*==============================================================*/


drop table if exists Account;

drop table if exists History;

/*==============================================================*/
/* Table: Account                                               */
/*==============================================================*/
create table Account
(
   userid               varchar(30) not null,
   username             varchar(30),
   password             varchar(10),
   type                 varchar(10),
   balance              double,
   primary key (userid)
);

/*==============================================================*/
/* Table: History                                               */
/*==============================================================*/
create table History
(
   id                   varchar(255) not null,
   userid               varchar(30),
   datetime             varchar(255),
   address              varchar(255),
   expense              varchar(255),
   income               varchar(255),
   balance              varchar(255),
   sourceid             varchar(255),
   memo                 varchar(255),
   primary key (id)
);

alter table History add constraint FK_Reference_1 foreign key (userid)
      references Account (userid) on delete restrict on update restrict;

