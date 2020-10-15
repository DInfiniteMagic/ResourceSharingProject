create database D_Infinite_Magic;

use D_Infinite_Magic;

create table t_account(
    id int not null  primary key  auto_increment,
    username varchar(25) not null unique,
    password varchar(32) not null ,
    salt varchar(10) not null,
    status varchar(10) default null
);/*账号*/



create table t_personInfor(
    id int not null  primary key  auto_increment,
    account_id int not null,
    name varchar(15) not null,
    mail varchar(35) not null,
    phoneNum varchar(20) not null,
    registrationDate date not null ,
    avatarUrl varchar(100) default null ,
    gender varchar(5) default  null,
    address varchar(50) default null,
    birthday date default null
);/*账号关联信息*/

create table t_role(
    id int not null primary key auto_increment,
    name varchar(30) not null
);/*用户角色表*/

create table t_account_role(
    id int not null primary key auto_increment,
    accountId int not null,
    roleId int not null
);/*用户角色对应表*/


create  table  t_resourceInfor(
    id int not null primary key auto_increment,
    resourceName varchar(55) not null,
    resourceClass varchar(50) not null,
    resourceValue int ,
    resourceDescription varchar(900) not null,
    uploadResourceName varchar(100) not null,
    resourceUrl varchar(100) not null,
    resourceStatue varchar(10) not null,
    uploadTime  varchar(35) not null,
    uploadAccountId int not null,
    resourceSize bigint not null,
    resourceDownloadTimes int
);/*资源表信息*/

create table t_account_favorites(
    id int not null primary key auto_increment,
    resource_id int not null,
    account_id int not null
);/*用户资源收藏表*/


create table t_resourcesClass(
     id int not null primary key auto_increment,
     resourceClass varchar(50) not null
);/*资源类别表*/

create table t_systemMonitor(
    id int not null primary key auto_increment,
    time varchar(50) default null ,
    RAMPercentage varchar(10) not null,
    CPUPercentage varchar(10) not null,
    JVMPercentage varchar(10) not null,
    HardDiskPercentage varchar(10) not null
);/*系统监控*/

create table  t_recordOperateLog(
    id int not null primary key auto_increment,
    classPath varchar(100) not null,
    classMethod varchar(50) not null,
    ipHost varchar(50) not null,
    operateTime varchar(50) not null,
    userAccount varchar(30) not null,
    moduleType varchar(50) not null,
    functionName  varchar(50) not null,
    operateContent  varchar(50) not null
);/*用户操作日志*/

