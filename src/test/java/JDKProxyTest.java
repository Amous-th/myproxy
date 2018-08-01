import hong.tian.proxy.base.PeopleInterface;
import hong.tian.proxy.base.Rto;
import hong.tian.proxy.jdk.PeopleWithJDKProxy;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

public class JDKProxyTest {
    public static void main(String[] args) throws Exception {
        PeopleWithJDKProxy proxy = new PeopleWithJDKProxy(new Rto());
        PeopleInterface jdkProxy = (PeopleInterface) proxy.instanceProxy();
        System.out.println(jdkProxy.getClass());
        jdkProxy.talk();

/*        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{PeopleInterface.class});
        FileOutputStream outputStream = new FileOutputStream("$Proxy0.class");
        outputStream.write(bytes);
        outputStream.close();*/

    }

}
