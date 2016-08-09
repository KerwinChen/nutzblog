var ioc = {
    conf: {
        type: "org.nutz.ioc.impl.PropertiesProxy",
        fields: {
            paths: ["custom/"]
        }
    },
    dataSource: {
        type: "com.alibaba.druid.pool.DruidDataSource",
        events: {
            create: "init",
            depose: 'close'
        },
        fields: {
            url: {java: "$conf.get('db.url')"},
            username: {java: "$conf.get('db.username')"},
            password: {java: "$conf.get('db.password')"},
            testWhileIdle: true,
            validationQuery: {java: "$conf.get('db.validationQuery')"},
            maxActive: {java: "$conf.get('db.maxActive')"},
            filters: "mergeStat",
            connectionProperties: "druid.stat.slowSqlMillis=2000"
        }
    },
    dao: {
        //type: "org.nutz.dao.impl.NutDaoExt", // 1.b.53或以上版本使用原版NutDao.
        type: "org.nutz.dao.impl.NutDao", // 1.b.53或以上版本使用原版NutDao.
        args: [{refer: "dataSource"}]

    }

    // ,
    // 基于Ehcache的DaoCacheProvider
    //cacheProvider: {
    //    type: "org.nutz.plugins.cache.dao.impl.provider.EhcacheDaoCacheProvider",
    //    fields: {
    //        cacheManager: {refer: "cacheManager"} // 引用ehcache.js中定义的CacheManager
    //    },
    //    events: {
    //        create: "init"
    //    }
    //}


};