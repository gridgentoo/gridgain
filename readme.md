![Image alt](hhttp://www.gridgain.com/images/logo/logo_mid.png)

# Архитектура [ignite] & Реверс инжениринг 
https://drive.google.com/drive/folders/1dFyHn3-PqFOXjY6ivSgpwP00WFZ_K3Dc

# Реверс инжениринг в Enterprise Architect (EA) в "Лаборатории Касперского"
https://www.youtube.com/watch?v=pCHQZwvgxNM

![Image alt](https://roem.ru/wp-content/uploads/2018/04/kitajkasperskayachubajs.jpg)

"Президент Сбербанка Герман Греф назвал неконкурентоспособной IT-инфраструктуру крупнейшего банка страны. 
Сбербанк с помощью компании GridGain полностью поменяет платформу, пообещал Греф
GridGain развивает (http://www.gridgain.com/) одноимённую СУБД для обработки больших объёмов данных в оперативной памяти 
(In-Memory Data Grid), написанную на языке Java.
СУБД доступна в виде коммерческого продукта и свободнораспространяемой Community версии, 
код которой опубликован (https://github.com/gridgain/gridgain) пол лицензией Apache 2.0. 
Продукты GridGain  базируются на наработках свободного проекта Apache Ignite (http://ignite.apache.org/), 
который был инициирован на основе переданных Фонду Аpache исходных текстов GridGain.

## GridGain In-Memory Data Fabric
<blockquote>In-Memory Computing uses high-performance, integrated, distributed memory systems to compute and transact on large-scale data sets in real-time, orders of magnitude faster than possible with traditional disk-based or flash technologies.
</blockquote>

GridGain’s In-Memory Data Fabric is designed to deliver uncompromised performance for a widest set of in-memory computing use cases from high performance computing, to the industry most advanced data grid, to streaming and plug-n-play Hadoop accelerator:

### In-Memory Data Grid
Natively distributed, ACID transactional, MVCC-based, SQL+NoSQL, in-memory object key-value store. The only in-memory data grid proven to scale to billions of transactions per second on commodity hardware.

### In-Memory Streaming
Massively distributed processing meets Complex Event Processing (CEP) and Streaming Processing with advanced workflow support, windowing, user-defined indexes and more.

### In-Memory Accelerator for Hadoop
Combination of In-Memory File System 100% compatible with Hadoop HDFS and In-Memory MapReduce delivering 100x performance increase. Minimal integration, plug-n-play acceleration with any Hadoop distro.

## Maven Install
The easiest way to get started with GridGain in your project is to use Maven dependency management:

### Fabric Edition
`Fabric` edition includes all GridGain functionality except for `hadoop accelerator`.

```xml
<dependency>
    <groupId>org.gridgain</groupId>
    <artifactId>gridgain-fabric</artifactId>
    <version>${gridgain.version}</version>
    <type>pom</type>
</dependency>
```

### Legacy Editions
Following editions are supported for legacy downloads and projects started before `Fabric` edition has been introduced. It is strongly recommended to use `Fabric` edition.

#### HPC Edition
`HPC` edition includes all GridGain functionality except for `data grid`, `streaming` and `hadoop accelerator`.

#### Data Grid Edition
`Data Grid` edition includes all GridGain functionality except for `streaming` and `hadoop accelerator`.

#### Streaming Edition
`Streaming` edition includes all GridGain functionality except for `data grid` and `hadoop accelerator`.

You can copy and paste this snippet into your Maven POM file. Make sure to replace version with the one you need.

## Binary Downloads & Documentation
Grab the latest binary release and current documentation at [www.gridgain.com](http://www.gridgain.com)

## Issues
Use GitHub [issues](https://github.com/gridgain/gridgain/issues) to file bugs.

## License
GridGain is available under [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) license.

## Copyright
Copyright (C) 2007-2014, GridGain Systems, Inc. All Rights Reserved.
