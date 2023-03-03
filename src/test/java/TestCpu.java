import com.sun.management.OperatingSystemMXBean;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;

/**
 * Created by jiangzeyin on 2019/3/15.
 */
public class TestCpu {
    public static void main(String[] args) {
        while (true) {
            System.out.println(getMemery());
            System.out.println(getCpuRatio());
        }


    }

    public static String getMemery() {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存+虚拟内存
        long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();

        Double compare = (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
        return "内存已使用:" + compare.intValue() + "%";
    }





}
