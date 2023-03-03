import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.WatchUtil;
import cn.hutool.core.util.CharsetUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * Created by jiangzeyin on 2019/3/15.
 */
public class TestJavaTail {

    public static void test() throws IOException {

        File file = new File("D:/ssss/a/tset.log");
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);

        FileDescriptor descriptor = fileOutputStream.getFD();
        FileInputStream nfis = new FileInputStream(descriptor);
        String msg = IoUtil.read(nfis, CharsetUtil.CHARSET_UTF_8);
        System.out.println(msg);
        System.out.println("nfis>>>" + nfis.read());
        FileInputStream sfis = new FileInputStream(descriptor);
        System.out.println("sfis>>>" + sfis.read());
        System.out.println("nfis>>>" + nfis.read());
        nfis.close();
        try {
            System.out.println("sfis>>>" + sfis.read());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("nfis执行异常");
        }
        sfis.close();
    }

    public static boolean forceDelete(File file) {
        boolean result = file.delete();
        int tryCount = 0;
        while (!result && tryCount++ < 10) {
            System.gc();    //回收资源
            result = file.delete();
        }
        return result;
    }

}
