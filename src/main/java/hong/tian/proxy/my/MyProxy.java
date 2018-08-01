package hong.tian.proxy.my;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyProxy {

    private static final String ENTER = "\r\n";

    public static Object newProxyInstance(MyClassLoader loader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler h) {
        try {
            // 动态生成源代码
            String srcClass = generateSrc(interfaces);
            // 输出Java文件
            String filePath = MyProxy.class.getResource("").getPath() + "$ProxyO.java";
            System.out.println(filePath);
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(srcClass);
            fileWriter.flush();
            fileWriter.close();
            // 编译Java文件为class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = fileManager.getJavaFileObjects(filePath);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, iterable);
            task.call();
            fileManager.close();
            // 加载编译生成的class文件到JVM
            Class<?> proxyClass = loader.findClass("$ProxyO");
            Constructor<?> constructor = proxyClass.getConstructor(MyInvocationHandler.class);
            // 删掉虚拟代理类
            File file = new File(filePath);
            file.delete();
            // 返回字节码重组以后的代理对象
            return constructor.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {

        StringBuilder stringBuilder = new StringBuilder();

        StringBuilder interfaceNames = new StringBuilder();
        stringBuilder.append("package ").append(MyProxy.class.getPackage().getName()) .append(";").append(ENTER);;
        for (Class clazz : interfaces) {
            stringBuilder.append("import ").append(clazz.getName())
                    .append(";").append(ENTER);
            interfaceNames.append(clazz.getName()).append(",");
        }

        stringBuilder.append("import hong.tian.proxy.my.MyInvocationHandler;" + ENTER);
        stringBuilder.append("import java.lang.reflect.Method;" + ENTER);
        stringBuilder.append("public class $ProxyO implements ")
                .append(interfaceNames.substring(0, interfaceNames.length() - 1))
                .append("{")
                .append(ENTER);

        stringBuilder.append("MyInvocationHandler h;" + ENTER);
        stringBuilder.append("public $ProxyO(MyInvocationHandler h) {" + ENTER);
        stringBuilder.append("this.h = h;" + ENTER);
        stringBuilder.append("}" + ENTER);

        for (Method method : interfaces[0].getMethods()) {
            stringBuilder.append("public ").append(method.getReturnType().getName()).append(" ")
                    .append(method.getName()).append("() {").append(ENTER);

            stringBuilder.append("try {" + ENTER);
            stringBuilder.append("Method m = ").append(interfaces[0].getName()).append(".class.getMethod(\"")
                    .append(method.getName()).append("\", new Class[]{});").append(ENTER);
            stringBuilder.append("this.h.invoke(this, m, null);" + ENTER);
            stringBuilder.append("} catch(Throwable able) {" + ENTER);
            stringBuilder.append("able.getMessage();" + ENTER);
            stringBuilder.append("}" + ENTER);
            stringBuilder.append("}" + ENTER + ENTER);
        }

        stringBuilder.append("}" + ENTER);
        return stringBuilder.toString();
    }

}
