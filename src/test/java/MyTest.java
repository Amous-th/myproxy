import hong.tian.proxy.base.PeopleInterface;
import hong.tian.proxy.base.Rto;
import hong.tian.proxy.my.PeopleWithMyProxy;

public class MyTest {

    public static void main(String[] args) {
        PeopleWithMyProxy proxy = new PeopleWithMyProxy(new Rto());
        PeopleInterface jdkProxy = (PeopleInterface) proxy.instanceProxy();
        System.out.println(jdkProxy.getClass());
        jdkProxy.talk();
    }
}
