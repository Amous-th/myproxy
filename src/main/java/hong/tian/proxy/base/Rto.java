package hong.tian.proxy.base;

/**
 * @author hong.tian
 * @date 2018/8/1 14:33
 */
public class Rto implements PeopleInterface {

    @Override
    public void talk() {
        System.out.println(" i want  光");
    }

    @Override
    public void run() {
        System.out.println(" run ...");
    }

}
