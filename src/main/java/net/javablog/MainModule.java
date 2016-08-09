package net.javablog;


import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.plugins.view.freemarker.FreemarkerViewMaker;


@Views({FreemarkerViewMaker.class})
@SetupBy(value = MainSetup.class)
@ChainBy(args = "mvc/nutzbook-mvc-chain.js")
@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = {"*js", "ioc/",
        "*anno", "net.javablog",
        "*tx",
        "*org.nutz.integration.quartz.QuartzIocLoader"})
public class MainModule {
}
