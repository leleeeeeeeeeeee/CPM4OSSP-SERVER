import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.WatchUtil;
import cn.hutool.core.io.watch.Watcher;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * Created by jiangzeyin on 2018/10/2.
 */
public class TestFileWatch {
    public static void main(String[] args) {
        File file = new File("D:\\SystemDocument\\Desktop\\top.txt");
        WatchMonitor watchMonitor = WatchUtil.create(file);
        
        });
    }
}
