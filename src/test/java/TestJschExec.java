import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.SyncFinisher;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.ChannelType;
import cn.hutool.extra.ssh.JschUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class TestJschExec {

	private static final String cmd = "ps -ef | grep jpom-test-jar | awk '{print $2}' | xargs kill -9";

	@Test
	public void testShell() {
		Session session = JschUtil.createSession("192.168.1.8", 22, "root", "123456+");
		System.out.println(JschUtil.execByShell(session, "source /etc/profile && source ~/.bash_profile && source ~/.bashrc && nohup java -Dappliction=jpom-test-jar -jar /home/data/test/springboot-test-jar-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &", CharsetUtil.CHARSET_UTF_8));
	}







}
