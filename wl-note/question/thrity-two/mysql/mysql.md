第一个只是点就是常用的sql语法。

第二个知识点，要知道mysql都提供哪些基本的数据类型，不同数据类型占用的空间大小可以按我给出的分类进行，具体的就不一一列举了

MyISAM是mysql的主要存储引擎，MYSQL官方提供的存储引擎，其特点是支持全文检索，查询效率比较高，缺点是不支持事务只能使用表级锁。InnoDb在5.5版本后成为MYSQL中的默认存储引擎，特点是支持ACID事务，支持外键支持行级锁，提高了并发效率。TokuDb是第三方开发的开源存储引擎，有非常快的写速度支持数据的压缩存储，可以在线添加索引而不影响读写操作，但是因为压缩的原因，ToKuDB非常适合访问频率不高的数据或者历史数据归档，不适合大量读取的场景

表锁开销小加锁快，不会出现死锁，但所得力度比较大，发生锁冲突的概率高，并发访问效率比较低。行级锁开销大加锁慢，有可能会出现死锁，不会因为锁定力度小，发生锁冲突的概率比较低，并发访问效率比较高。共享锁也就是读锁，其他事务可以读，但不能写。MYSQL可以通过lock in share mode语句来显示的使用共享锁。排它锁就是其它事务不能读取也不能写，对于update delete insert语句，InnoDB会自动给设计的数据集加排他锁，或者使用select update使用显示的排他锁。

第五个知识点，索引我们稍后讲解，

第六个知识点是MYSQL的存储过程与函数，存储过程与函数都可以避免开发人员重复编写相同的SQL语句。并且存储过程和函数都是在MYsql服务器中运行的，可以减少客户端与服务端的数据传输。存储过程实现了更复杂的功能，而函数一般用来实现针对性比较强的功能，比如按特殊策略来求值，和存储过程一样，函数可以执行包括修改表等一系列数据库操作，而用户定义函数不能用于执行修改全局数据库状态的操作。存储过程一般是作为一个独立的部分来执行的，函数可以作为查询语句中的一个部分来调用，最后语句中不能使用存储过程，但可以使用函数，不过存储过程一般与数据库的实现绑定，使用存储过程会降低程序的可移植性，应谨慎使用。

第七个知识点可以去了解一下MYSQL8.0的一些新特性，例如默认字符集改为了u tf8增加了隐式索引功能，隐藏后的索引不会被查询，优化器使用可以使用这个特性用于性能调试。支持了通用表表达式使复杂查询中的嵌入，语句表达更清晰，新增了窗口函数的概念，它可以用来实现新的查询方式，窗口函数与sum count等集合函数类似。但不会将多行查询结果合并，而是将结果放一多行中就窗口函数，不需要 group by 

最后一个知识点mysql 调优展开讲解。

现在看，mysql中的锁可以大幅增加数据库的查询性能，在实际业务场景中或多或少都会使用到。那锁也是有代价的，首先需要额外的磁盘空间来保存索引，其次对于插入，更新，删除等操作，由于更新索引会增加额外的开销，因此锁比较适合用在读多写少的场景。我们先学习SQL的所有类型的

图左边的模块。mysql中索引类型有唯一索引就是索引列中的值必须是唯一的，但是允许出现空值。这种锁一般用来保证数据的唯一性，比如保存账户信息的表，每个账户的ID必须要保证唯一，如果重复插入相同的账号ID，这个时候mysql会返回异常。第二种，主键索引是一种特殊的唯一索引，但他不允许出现空值。第三种是普通索引，唯一索引不同，允许索引列中存在相同的值，例如学生的成绩表，各个学科的分数是允许重复的，就可以使用普通索引，第四种是联合索引，就是由多个列共同组成的，所以一个表中含有多个单列的索引，并不是联合索引，联合索引是对多个列字段按顺序共同组成一个索引，最左原则是联合索引时需要注意的地方，

最左原则就是为了查询条件中的字段，必须与索引字段从左到右进行匹配，比如一个用户信息表用姓名和年龄组成了联合索引，如果查询条件是姓名等于张三，那么满足最左原则，如果查询条件是年龄大约20，由于索引中最早的字段是姓名不是年龄，所以就不能使用这个索引。最后一个类型是全文索引，前面提到了myISAM引擎中实现了这个，所以在5.6版本，以后InnoDB引擎也支持全文索引，并且在5.7.6版本后支持了中文索引。全文索引，只能在char，varchar,text类型上施工。你曾使用的是倒排索引，实现要注意对于大数据量的表生成，全文索引会非常消耗时间，也非常消耗磁盘空间，在看到图右面的模块儿买三号的索引，按实现来分由我们前面学习过的B+Tree,B+tree比较适合用作大于或者小于这样的范围查询，是Mysql中最常使用的一种索引实现。R-Tree是一种用于处理多维数据的数据结构，可以对地理数据进行空间索引，不过实际业务场景中的使用的比较少。Hash使用散列表来对数据进行索引，哈希方式，不需要像B-Tree那样需要查询多次才能定位到记录，因此呢，哈希索引的效率比必高，但是不支持范围，查找排序等功能，实际使用的也比较少，最后FUlltext就是我们前面提到的全文索引，是一种记录，关键字与对于文档关系的一种倒排索引

MYSQL的调优也是研发人员需要掌握的一项技能，一般MYSQL调优就是图中的四个维度，第一个是针对数据库设计，表结构设计以及索引设置维度进行优化，第二个是对我们业务中使用SQL语句进行优化，例如调整where查询条件。第三个维度是对MYSQL服务的配置进行优化，例如对连接数的管理，对所有缓存 查询缓存 排序缓存等各种缓存大小进行优化，第四个维度是对硬件设备和操作系统设置进行优化，例如调整操作系统参数。增加内存，升级固态硬盘等等. 这四个维度，从优化成本的角度讲，从左到右优化成本逐渐升高，从优化效果角度来看，从右到左优化效果更高

对于研发人员来讲前面两个维度与业务相关,因此，需要重点掌握后两个维度，更适合dba进行深入学习，简单了解就好。我们重点来看刚才说的前两个维度。先看到左边的模块，关于表结构和所有的优化，应掌握如下原则。 

第一个：
要在设计表结构时考虑数据库的水平和垂直扩展能力，提前规划好未来一年的数据量的读写量的增长，规划好分库分表方案，比如涉及用户信息，表示预计一年后用户数据10亿条，写QPS5000,读QPS3万可以设计按UUID的维度进行散列，分为四个库，每个库32张表，这样可以保证单表数据量控制在千万级别。第二个要为字段选择合适的数据类型，在保留扩展能力的前提下，优先选用较小的数据结构。例如是年龄的字段，要使用tinyInt而不要使用Int。第三个可以将字段多的表分解成多个表，必要时增加中间表进行关联，假如一张表有四五十个字段，显然不是一个好的设计。第四个，一般来说，我们设计关系数据库时，需要满足第三范式，但为了满足第三范式，我们可能会拆分出多张表。而且查询时需要对多张表进行关联查询，也是为了提高查询效率，会降低范式要求，在表中保存一定的冗余信息，也叫做反范式。但要注意，反范式一定要适度，第五个要善用索引，比如为经常作为查询条件的字段，创建索引，创建联合索引时，要根据最左原则考虑所有的复用能力，不要重复创建索引。要为保证数据不能重复的字段创建唯一索引等等。不过要注意索引对插入，更新等写操作是有代价的，不要滥用索引，比如像性别，这样唯一性很差的字段就不适合建立索引，第六个。列字段尽量设置not null, Mysql难以对使用null的列进行查询优化，允许null会使索引索引统计和值更加复杂，允许null的列，需要更多的存储空间，还需要Mysql内部进行特殊处理。

再看到右面的模块对sql语句进行优化的原则。第一个要找到最需要优化的sql语句。要么是使用频率最高的，要么是优化后提高最明显的语句.可以通过查询MYSQL慢查询日志来发现需要进行优化的SQL语句。

第二个要学会利用SQL提供的分析工具，例如使用explain来分析语句的执行计划，看看是否使用了索引，使用哪个索引，扫描了多少记录，是否使用了文件排序等等，或者使用profile命令来分析某个语句执行过程中的各分部耗时。

第三个要注意使用，查询语句时要避免使用select *而应当指定需要获取的字段，原因，一是可以避免查询出不需要使用的字段，二是可以避免查询列字段的原信息。

第四个是尽量使用prepared statement，一是性能更好，另一个是可以防止sql注入。第五个是尽量使用索引扫描来进行排序，也就是尽量在有索引的字段上进行排序操作。学习完知识点后，我们来看这一课的面试考察点。

首先，你必须了解消息队列数据库的基本原理，使用场景以及常用的队列数据库的特点。消息队列适用于异步处理和削峰填谷的场景，Kafka在提供高可用性的前提下，实现了零消息丢失的高性能分布式队列服务。MYSQL提供了多种引擎，可以支持事务型与非事务性的关系对象或服务等等。

第二点，要了解kafka的架构和消息处理流程，明白kafka是如何通过partition来保证并发能力与冗余灾备的，要了解消费组是如何保证每个consumer实例不会获取到重复的消息，
第三点，需要深刻理解数据库事务的ACID特性，了解并发事务可能导致的并发问题和不足，数据库隔离级别如何解决这些并发问题。第四点，要牢牢掌握常用的SQL语句，比如where条件，查询语句，join关联语句，Order by，排序语句等等，还有熟悉常用的自带函数，例如 sum。、count等等，

最后，要了解MYSQL数据库中不同引擎的特点以及不同类型的索引实现，比如最常使用的innoDB，非常擅长事务处理，MyISAM比较适合非事物的简单查询场景，比如要知道MYSQL的唯一索引，联合索引，全文索引等索引的不同点，索引类型以及最常使用的B+Tree,索引实现等等。如果想要在面试中获得更好的表现，你还应该了解下面的加分项。第一个要了解新特性，不论是kakfa还是Mysql都要了解一下新版本的特性，例如MYSQL8钟提供了窗口函数来支持新的查询方式，支持通用表达式来使复杂查询中的嵌入表语句更加清晰等等，
第二点，你要知道数据库表设计原则，如果有个线上业务数据库的设计经验就更好了，你能够知道如何对容量进行评估，也知道适当分库分表来保证未来服务的可扩展性，这会对面是起到积极的影响。
第三点，你最好有个数据库的调优经验，例如。明明见你的时候语句，但是查询效率还很慢，后来通过explain分析发现表中有多个索引优化器，选用了错误的，所以导致查询效率偏低，然后通过SQL语句中使用index来指定索引来解决。第四点有kafka等主流消息队列的使用经验，并且知道应该如何在业务场景下进行调优，例如日志推送场景，对小概率消息丢失，可以容忍，可以设置为异步发送消息，而对于金融类业务需要设置同步发送消息，并且设置最高的消息可靠性