package hong.tian.proxy.jdk;

import hong.tian.proxy.base.PeopleInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PeopleWithJDKProxy {
 
    private PeopleInterface proxyInterface;
 
    public PeopleWithJDKProxy(PeopleInterface proxyInterface) {
        this.proxyInterface = proxyInterface;
    }
 
    public Object instanceProxy() {
        System.out.println("代理方法开始...");
        return Proxy.newProxyInstance(
                proxyInterface.getClass().getClassLoader(),
                proxyInterface.getClass().getInterfaces(),
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.invoke(proxyInterface, args);
                System.out.println("代理方法结束...");
                return null;
            }
        });
    }
 
}
