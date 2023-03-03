import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.CharsetUtil;
import io.jpom.JpomApplication;

import java.io.*;

public class TestRun {
    public static void main(String[] args) throws IOException, InterruptedException {
        testProcessBuilder("D:\\jpom\\agent\\script\\test\\script.bat");

    }


}
