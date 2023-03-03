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

public class TestSshGit {


    @Test
	public void test1() throws GitAPIException {

	}

	@Test
	public void test2() {

	}

}
