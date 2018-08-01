package hong.tian.proxy.my;

import hong.tian.proxy.base.PeopleInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PeopleWithMyProxy {

    private PeopleInterface proxyInterface;

    public PeopleWithMyProxy(PeopleInterface proxyInterface) {
        this.proxyInterface = proxyInterface;
    }

    public Object instanceProxy() {
        System.out.println("myProxy--->代理方法开始...");
        return MyProxy.newProxyInstance(
                new MyClassLoader(),
                proxyInterface.getClass().getInterfaces(),
                new MyInvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                        method.invoke(proxyInterface, args);
                        System.out.println("myProxy--->代理方法结束...");
                        return null;
                    }
                });
    }

}
