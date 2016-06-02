package hust.ws.myServer;

/**
 * 使用CXF框架，发布webservice服务，并使用客户端远程访问webservice。
 * 1，CXF介绍
 * 	CXF是Celtrix（ESB框架）和XFire（webservice）合并而成，并且捐给了Apache
 * 	CXF的核心是org.apache.cxf.Bus（总线），类似于spring的ApplicationContext
 * 	CXF默认是依赖于spring的
 * 	Apache CXF发行包中的jar，如果全部放到lib中，需要JDK1.6级以上，否则会报JAX-WS版本不兼容的错误
 * 	CXF内置了Jetty服务器，它是servlet容器，好比tomcat
 * 2，CXF特点
 * 	与Spring、Servlet做了无缝对接，陈晓峰框架里面继承了Servlet容器Jetty
 * 	支持注解的方式来发布webservice
 * 	能够显示一个webservice的服务列表
 * 	能够添加拦截器：输入拦截器、输出拦截器：输入日志信息拦截器’输出日志拦截器、用户权限认证的拦截器
 *
 */

public class cxf {

}
