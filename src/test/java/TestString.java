import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.SortedMap;
import java.util.regex.Pattern;

/**
 * Created by jiangzeyin on 2019/3/1.
 */
public class TestString {
    public static void main(String[] args) {

        Pattern pattern = Pattern.compile("(https://|http://)?([\\w-]+\\.)+[\\w-]+(:\\d+|/)+([\\w- ./?%&=]*)?");
        String url = "http://192.168.1.111:2122/node/index.html?nodeId=dyc";
        System.out.println(ReUtil.isMatch(pattern, url));
        System.out.println(ReUtil.isMatch(PatternPool.URL_HTTP, url));

        System.out.println(Math.pow(1024, 2));

        System.out.println(Integer.MAX_VALUE);

        while (true) {
            SortedMap<String, Charset> x = Charset.availableCharsets();
            Collection<Charset> values = x.values();
            boolean find = false;
            for (Charset charset : values) {
                String name = charset.name();
                if ("utf-8".equalsIgnoreCase(name)) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                System.out.println("没有找utf-8");
            }
        }
    }
}
