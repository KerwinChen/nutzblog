var ioc = {
    currentTime: {
        type: "org.nutz.plugins.view.freemarker.directive.CurrentTimeDirective"
    },
    configuration: {
        type: "freemarker.template.Configuration"
    },
    freeMarkerConfigurer: {
        type: "org.nutz.plugins.view.freemarker.FreeMarkerConfigurer",
        events: {
            create: 'init'
        },
        args: [{
            refer: "configuration"
        }, {
            app: '$servlet'
        }, "WEB-INF", ".ftl", {
            refer: "freemarkerDirectiveFactory"
        }]
    },
    freemarkerDirectiveFactory: {
        type: "org.nutz.plugins.view.freemarker.FreemarkerDirectiveFactory",
        fields: {
            freemarker: 'freemarker.properties',
        }
    },
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
        args: [{refer: "dataSource"}],
        fields: {
            executor: {refer: "cacheExecutor"}
        }

    }

    ,
    tmpFilePool: {
        type: 'org.nutz.filepool.NutFilePool',
        // 临时文件最大个数为 1000 个
        args: ["~/nutz/blog/upload/tmps", 10000000]
    },
    uploadFileContext: {
        type: 'org.nutz.mvc.upload.UploadingContext',
        singleton: false,
        args: [{refer: 'tmpFilePool'}],
        fields: {
            // 是否忽略空文件, 默认为 false
            ignoreNull: true,
            // 单个文件最大尺寸(大约的值，单位为字节，即 1048576 为 1M)
            maxFileSize: 1048576,
            // 正则表达式匹配可以支持的文件名
            nameFilter: '^(.+[.])(gif|jpg|png)$'
        }
    },
    myUpload: {
        type: 'org.nutz.mvc.upload.UploadAdaptor',
        singleton: false,
        args: [{refer: 'uploadFileContext'}]
    }
    ,
    cacheExecutor: {
        type: "org.nutz.plugins.cache.dao.CachedNutDaoExecutor",
        fields: {
            cacheProvider: {refer: "cacheProvider"},
            cachedTableNamePatten: "tb_*", // 需要缓存的表
            // cachedTableNames: ["tb_config"], // 需要缓存的表
            enableWhenTrans: false, // 事务作用域内是否启用,默认false
            cache4Null: true // 是否缓存空值,默认true
        }
    },
    cacheProvider: {
        type: "org.nutz.plugins.cache.dao.impl.provider.MemoryDaoCacheProvider",
        fields: {
            cacheSize: 10000
        },
        events: {
            create: "init"
        }
    }


};