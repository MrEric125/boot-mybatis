  
```mysql
#新增partition
alter table `dd` add partition  (partition  p1 values less than (2002));

# 如果表都没有创建分区则
alter table `dd` partition by range (year_col)(
    partition p1 values less than (1992)
);


```