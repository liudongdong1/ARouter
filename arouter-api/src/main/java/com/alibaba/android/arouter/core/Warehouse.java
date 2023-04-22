package com.alibaba.android.arouter.core;

import com.alibaba.android.arouter.base.UniqueKeyTreeMap;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.facade.template.IRouteGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Storage of route meta and other data.
 *
 * @author zhilong <a href="mailto:zhilong.lzl@alibaba-inc.com">Contact me.</a>
 * @version 1.0
 * @since 2017/2/23 下午1:39
 */
class Warehouse {
    // Cache route and metas
  //所有IRouteGroup实现类的class对象，是在ARouter初始化中赋值，key是path第一级
    //（IRouteGroup实现类是编译时生成，代表一个组，即path第一级相同的所有路由，包括Activity和Provider服务）
    static Map<String, Class<? extends IRouteGroup>> groupsIndex = new HashMap<>();
    //所有路由元信息，是在completion中赋值，key是path
    //首次进行某个路由时就会加载整个group的路由，即IRouteGroup实现类中所有路由信息。包括Activity和Provider服务
    static Map<String, RouteMeta> routes = new HashMap<>();

    // Cache provider
    //所有服务provider实例，在completion中赋值，key是IProvider实现类的class
    static Map<Class, IProvider> providers = new HashMap<>();
    //所有provider服务的元信息(实现类的class对象)，是在ARouter初始化中赋值，key是IProvider实现类的全类名。
    //主要用于使用IProvider实现类的class发起的获取服务的路由，例如ARouter.getInstance().navigation(HelloService.class)
    static Map<String, RouteMeta> providersIndex = new HashMap<>();

    // Cache interceptor
    //所有拦截器实现类的class对象，是在ARouter初始化时收集到，key是优先级
    static Map<Integer, Class<? extends IInterceptor>> interceptorsIndex = new UniqueKeyTreeMap<>("More than one interceptors use same priority [%s]");
    //所有拦截器实例，是在ARouter初始化完成后立即创建 
    static List<IInterceptor> interceptors = new ArrayList<>();

    static void clear() {
        routes.clear();
        groupsIndex.clear();
        providers.clear();
        providersIndex.clear();
        interceptors.clear();
        interceptorsIndex.clear();
    }
}
