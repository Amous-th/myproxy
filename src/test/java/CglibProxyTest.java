import hong.tian.proxy.base.Rto;
import hong.tian.proxy.cglib.CglibProxy;
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyTest {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();

        Enhancer enhancer = new Enhancer();  //主要的增强类
        enhancer.setSuperclass(Rto.class);  //设置父类，被增强的类
        enhancer.setCallback(cglibProxy);  //回调对象

        Rto o = (Rto) enhancer.create();//用cglibProxy来增强
        o.talk();
    }
}
