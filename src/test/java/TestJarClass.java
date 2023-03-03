import cn.hutool.Hutool;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.URLUtil;
import io.jpom.common.JpomManifest;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Created by jiangzeyin on 2019/4/13.
 */
public class TestJarClass {




    public static void main(String[] args) throws IOException {
        JarFile jarFile = new JarFile("D:\\SystemDocument\\Desktop\\springboot-test-jar-0.0.1-SNAPSHOT.jar");

        Manifest manifest = jarFile.getManifest();
        Attributes attributes = manifest.getMainAttributes();
        String mainClass = attributes.getValue("Main-Class");
        System.out.println(mainClass);
    }
}
