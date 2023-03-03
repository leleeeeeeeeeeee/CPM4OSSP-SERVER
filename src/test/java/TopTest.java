import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.core.util.CharsetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class TopTest {


    private static String execCommand(String[] command) {
        System.out.println(Arrays.toString(command));
        String result = "error";
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStream is;
            int wait = process.waitFor();
            if (wait == 0) {
                is = process.getInputStream();
            } else {
                is = process.getErrorStream();
            }
            result = IoUtil.read(is, CharsetUtil.GBK);
            is.close();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
