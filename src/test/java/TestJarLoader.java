import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class TestJarLoader {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        JarFile jarFile1 = new JarFile(new File("D:\\Idea\\Jpom\\modules\\agent\\target\\agent-2.4.2.jar"));
        ZipEntry zipEntry = jarFile1.getEntry("BOOT-INF/classes/cn/keepbx/jpom/common/interceptor/NotAuthorize.class");
        System.out.println(zipEntry);
        String path = "D:\\Idea\\Jpom\\modules\\agent\\target\\agent-2.4.2.jar";//外部jar包的路径
       
}
