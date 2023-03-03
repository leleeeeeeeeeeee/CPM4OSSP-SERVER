package git;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.PemUtil;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.util.FS;
import org.junit.Test;

import java.io.*;
import java.security.PrivateKey;

/**
 * https://qa.1r1g.com/sf/ask/875171671/
 * https://www.jianshu.com/p/036072b45a2d
 *
 *

 **/
public class TestSshGit {
	public static void main(String[] args) throws GitAPIException, IOException {
		File directory = new File("～/test/gitssh");
		FileUtil.del(directory);
		Git.cloneRepository()
				.setURI("git@gitee.com:keepbx/Jpom-demo-case.git").setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
				.setDirectory(directory)
				.setTransportConfigCallback(new TransportConfigCallback() {
					@Override
					public void configure(Transport transport) {
						SshTransport sshTransport = (SshTransport) transport;
						sshTransport.setSshSessionFactory(new JschConfigSessionFactory() {
							@Override
							protected void configure(OpenSshConfig.Host hc, Session session) {
								session.setConfig("StrictHostKeyChecking", "no");
							}

							@Override
							protected JSch createDefaultJSch(FS fs) throws JSchException {
								JSch jSch = super.createDefaultJSch(fs);
								return jSch;
							}
						});
					}
				})
				.call();
	}

    @Test
	public void test1() throws GitAPIException {

	}

	@Test
	public void test2() {

	}

	@Test
	public void test() throws FileNotFoundException {
		File keyFile = new File("/Users/user/.ssh/id_rsa");
		PrivateKey privateKey = PemUtil.readPemPrivateKey(new FileInputStream(keyFile));

	}
}
