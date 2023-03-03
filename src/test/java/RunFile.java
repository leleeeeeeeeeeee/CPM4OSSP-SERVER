import cn.hutool.core.text.StrSplitter;
import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * Created by jiangzeyin on 2019/2/26.
 */
public class RunFile {
    public static void main(String[] args) {
        List<String> list = StrSplitter.splitTrim(":::18000", StrUtil.COLON, true);
        System.out.println(list);
    }
}
