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

	@Test
	public void testShell2() throws Exception {

		String[] command = new String[]{cmd};
		Session session = null;
		Channel channel = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			session = JschUtil.createSession("192.168.1.8", 22, "root", "123456+");
			channel = JschUtil.createChannel(session, ChannelType.SHELL);
			channel.connect();
			inputStream = channel.getInputStream();
			outputStream = channel.getOutputStream();
			SyncFinisher syncFinisher = new SyncFinisher(2);
			PrintWriter printWriter = new PrintWriter(outputStream);
			InputStream finalInputStream = inputStream;
			StringBuffer stringBuilder = new StringBuffer();
			syncFinisher.addRepeatWorker(() -> {
				for (String s : command) {
					try {
						printWriter.println(s);
						printWriter.println("exit");
						//把缓冲区的数据强行输出
						printWriter.flush();
						//System.out.println(s + "  " + command.length);
					} catch (Exception e) {
						e.printStackTrace();
						DefaultSystemLog.getLog().error("写入错误", e);
					}
				}
			}).addRepeatWorker(() -> {
				try {
					byte[] buffer = new byte[1024];
					int i;
					//如果没有数据来，线程会一直阻塞在这个地方等待数据。
					while ((i = finalInputStream.read(buffer)) != -1) {
						//System.out.println(i);
						stringBuilder.append(new String(Arrays.copyOfRange(buffer, 0, i), CharsetUtil.CHARSET_UTF_8));
					}
				} catch (Exception e) {
					DefaultSystemLog.getLog().error("读取错误", e);
				}
			}).start();
			System.out.println(stringBuilder);
		} finally {
			IoUtil.close(inputStream);
			IoUtil.close(outputStream);
			JschUtil.close(channel);
			JschUtil.close(session);
		}
	}






}
