insert into  t_account(username,password,salt) value ("董志坤","115106fd61f5cbb006e74ed805c870c3","VtIu");
update t_account set username="董志坤" where username="dzk";

insert into  t_account(username,password,salt) value ("1436921968","115106fd61f5cbb006e74ed805c870c3","VtIu");

delete from t_account where id=5;
DROP DATABASE d_infinite_magic;
DROP table t_personinfor;
use d_infinite_magic;
truncate  t_account;
truncate  t_personinfor;
truncate  t_role;
truncate  t_account_role;
truncate t_account_favorites;





select * from t_personInfor;

select * from t_account;

select * from t_role;

select * from t_account_role;

select * from t_resourceinfor;

select * from t_account_favorites;

update t_account_role set roleId=1 where id=1;

select r.name from t_account as a join t_account_role as ar on ar.accountId=a.id join t_role as r on r.id=ar.roleId where a.username="adminManager"

insert into  t_account( username, password, salt) values ("adminManager1","2cc032b9c5ab790246649683eac3802b","oKHc")
,("adminManager1","2cc032b9c5ab790246649683eac3802b","oKHc")


insert into t_personinfor(account_id, name, mail, phoneNum, registrationDate) value (1,"1111","1111","1111","2020-07-19");



ALTER TABLE t_account ADD unique(`username`);


update t_personinfor set avatarUrl="uploadFile/adminManager/avatar/1.jpg"  where id=1

update t_resourceinfor set uploadAccountId=2 where id=2

insert into t_account_favorites(resource_id, account_id)  values (2,1),(2,2),(3,1)

select sum(DATA_LENGTH)+sum(INDEX_LENGTH) from information_schema.tables
where table_schema='d_infinite_magic';

insert into  t_recordoperatelog(classPath, classMethod, ipHost, operateTime, userAccount, moduleType, functionName, operateContent)
values
(#{param1},#{param2},#{param3},#{param4},#{param5},#{param6},#{param7},#{param8});
;

select count(r.id) from  t_recordoperatelog as r
    left join t_account as a on a.username=r.userAccount
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as ro on ro.id=ar.roleId;



select r.id,ipHost,userAccount,name as useRole,operateTime,moduleType,operateContent from  t_recordoperatelog as r
    left join t_account as a on a.username=r.userAccount
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as ro on ro.id=ar.roleId limit 0 ,6;

 select count(r.id) from  t_recordoperatelog as r
    left join t_account as a on a.username=r.userAccount
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as ro on ro.id=ar.roleId;


select r.id,ipHost,userAccount,name as userRole,operateTime,moduleType,operateContent from  t_recordoperatelog as r
    left join t_account as a on a.username=r.userAccount
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as ro on ro.id=ar.roleId where name like "%admin%";


select * from t_account;


select a.id as id,username,status,r.name as roleName,registrationDate from t_account as a
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as r on r.id=ar.roleId
    left join t_personinfor tp on a.id=tp.account_id;

select count(id) from t_account ;

select count(a.id)
    from t_account as a
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as r on r.id=ar.roleId
    left join t_personinfor tp on a.id=tp.account_id


select name from t_role;


select a.id as id,username,status,r.name as roleName,registrationDate from t_account as a
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as r on r.id=ar.roleId
    left join t_personinfor tp on a.id=tp.account_id
     where username like "%a%" or status like "%a%" or r.name like "%a%"

        limit 0,6;


update  t_account as a
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as r on r.id=ar.roleId
    left join t_personinfor tp on a.id=tp.account_id  set a.status = '正常' , ar.roleId=(select id from t_role where name="admin") where a.id=2;

update t_account set status="正常" where id=2

select *
    from t_account as a
    left join t_account_role as ar on ar.accountId=a.id
    left join t_role as r on r.id=ar.roleId
    left join t_personinfor tp on a.id=tp.account_id;

