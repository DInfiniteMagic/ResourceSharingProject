use d_infinite_magic;
truncate  t_resourceinfor;

select * from t_resourceInfor ;
select id,count(1),resourceValue from t_resourceInfor group by id;

insert into t_resourceinfor(resourceName, resourceClass, resourceValue, resourceDescription, uploadResourceName, resourceUrl, resourceStatue, uploadTime, uploadAccountId, resourceSize, resourceDownloadTimes)
values ()


select  count(r.id)
        from t_resourceinfor as r join t_account as a on a.id=r.uploadAccountId
        join t_personinfor as p on a.id=p.account_id
        left join
            (select ff.id as fid,resource_id as rid,aa.username as ausername from t_account_favorites as ff  join t_account as aa on ff.account_id=aa.id where aa.username="") as f on r.id = f.rid




select r.id as id,r.uploadResourceName as downloadResourceName,r.resourceClass as resourceClass,r.resourceValue as resourceValue,
        p.name as downloadAccountName, resourceSize as resourceSize,r.resourceDownloadTimes as  resourceDownloadTimes,
        r.uploadTime as uploadTime,r.resourceDescription as resourceDetails,f.fid as favorites
        from t_resourceinfor as r join t_account as a on a.id=r.uploadAccountId
        join t_personinfor as p on a.id=p.account_id
        left join
            (select ff.id as fid,resource_id as rid,aa.username as ausername from t_account_favorites as ff  join t_account as aa on ff.account_id=aa.id where aa.username="121") as f on r.id = f.rid










 select r.id as id,r.uploadResourceName as downloadResourceName,r.resourceClass as resourceClass,r.resourceValue as resourceValue,
        p.name as downloadAccountName, resourceSize as resourceSize,r.resourceDownloadTimes as  resourceDownloadTimes,
        r.uploadTime as uploadTime,r.resourceDescription as resourceDetails,f.fid as favorites
        from t_resourceinfor as r join t_account as a on a.id=r.uploadAccountId
        join t_personinfor as p on a.id=p.account_id
        left join
            (select ff.id as fid,resource_id as rid,aa.username as ausername from t_account_favorites as ff  join t_account as aa on ff.account_id=aa.id where aa.username="adminManager") as f on r.id = f.rid
        limit 0,6;


select * from t_account a left join t_account_favorites  f on a.id=f.account_id;

select id from t_account where username=#{param2}


delete from t_account_favorites where resource_id=#{param1} and account_id=(select id from t_account where username=#{param2})

;
insert into t_account_favorites(resource_id, account_id) values (1,(select id from t_account where username=#{param2}));

delete from t_account_favorites where resource_id=2 and account_id=(select id from t_account where username="adminManager");

select * from t_account_favorites;


select r.id as id,r.resourceName as downloadResourceName,r.resourceClass as resourceClass,r.resourceValue as resourceValue,
        p.name as downloadAccountName, resourceSize as resourceSize,r.resourceDownloadTimes as  resourceDownloadTimes,
        r.uploadTime as uploadTime,r.resourceDescription as resourceDetails,f.fid as favorites
        from t_resourceinfor as r join t_account as a on a.id=r.uploadAccountId
        join t_personinfor as p on a.id=p.account_id
        left join
            (select ff.id as fid,resource_id as rid,aa.username as ausername from t_account_favorites as ff  join t_account as aa on ff.account_id=aa.id where aa.username="adminManager") as f on r.id = f.rid
        where r.resourceStatue="未审核"
order by resourceValue desc
limit 0,6;



select r.id as id,r.resourceName as downloadResourceName,r.resourceClass as resourceClass,r.resourceValue as resourceValue,
        p.name as downloadAccountName, resourceSize as resourceSize,r.resourceDownloadTimes as  resourceDownloadTimes,
        r.uploadTime as uploadTime,r.resourceDescription as resourceDetails,f.fid as favorites
        from t_resourceinfor as r join t_account as a on a.id=r.uploadAccountId
        join t_personinfor as p on a.id=p.account_id
        left join
            (select ff.id as fid,resource_id as rid,aa.username as ausername from t_account_favorites as ff  join t_account as aa on ff.account_id=aa.id where aa.username="") as f on r.id = f.rid
        where r.resourceStatue="未审核" and (r.resourceName like "%叶%" or r.resourceClass like "%安全%" or p.name like "%张%" )
order by resourceValue desc


 select  count(r.id)
        from t_resourceinfor as r join t_account as a on a.id=r.uploadAccountId
        join t_personinfor as p on a.id=p.account_id
        left join
            (select ff.id as fid,resource_id as rid,aa.username as ausername from t_account_favorites as ff  join t_account as aa on ff.account_id=aa.id where aa.username="") as f on r.id = f.rid
        where r.resourceStatue="未审核" and (r.resourceName like "%说%" or r.resourceClass like "%说%" or p.name like "%说%" );

select uploadResourceName,resourceUrl from t_resourceinfor where id=#{param1};


update t_resourceinfor set resourceDownloadTimes=resourceDownloadTimes+1 where id=#{param1};

select * from t_resourcesClass;


select r.id as id,resourceName,resourceClass,resourceValue,uploadTime,resourceDescription from t_resourceinfor r join t_account a on r.uploadAccountId=a.id where username="adminManager" and resourceStatue="未审核";

select count(r.id) from t_account as a join t_resourceinfor as r on a.id=r.uploadAccountId where a.username="adminManager" and r.resourceStatue="未审核"


select r.id as id,resourceName,resourceClass,resourceValue,uploadTime,resourceDescription from t_resourceinfor r join t_account a on r.uploadAccountId=a.id
        where username="adminManager" and resourceStatue="未审核" order by id asc
        /*<if test="param3!='' and param4!=null">
            order by ${param3}  ${param4}
         </if>*/
        limit 0,6;


update t_resourceinfor set resourceName= ,resourceClass=,resourceValue=,resourceDescription= where id=;

select * from t_account_favorites;

delete from t_account_favorites where resource_id=#{id};

select resourceUrl from t_resourceinfor where id=#{id};

update t_resourceinfor set resourceStatue="已审核" where id=14;

select * from t_systemmonitor;



insert into t_systemmonitor(RAMPERCENTAGE, CPUPERCENTAGE, JVMPERCENTAGE, HARDDISKPERCENTAGE)
values
       ('0.4','0.5','0.9','0.55'),
       ('0.3','0.4','0.6','0.8'),
       ('0.2','0.55','0.8','0.2'),
       ('0.5','0.25','0.44','0.66'),
       ('0.1','0.35','0.45','0.88'),
       ('0.12','0.42','0.64','0.56');

select * from t_systemmonitor order by id desc limit 0 ,6;
