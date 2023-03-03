//package git;
//
//import cn.hutool.core.io.FileUtil;
//import io.jpom.model.enums.GitProtocolEnum;
//import io.jpom.model.data.RepositoryModel;
//import io.jpom.util.GitUtil;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.ListBranchCommand;
//import org.eclipse.jgit.api.errors.GitAPIException;
//import org.eclipse.jgit.lib.Constants;
//import org.eclipse.jgit.lib.Ref;
//import org.eclipse.jgit.transport.RemoteConfig;
//import org.eclipse.jgit.transport.URIish;
//import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.URISyntaxException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class TestGit {
//
//	@Test
//	public void test() {
//
//	}
//
//	@Test
//	public void test2() throws IOException {
//		String fileOrDirPath = "D:\\jpom\\server\\data\\temp\\";
//		List<File> strings = FileUtil.loopFiles(fileOrDirPath);
//		System.out.println(strings.size());
//		for (File string : strings) {
//			string.setWritable(true);
//			try {
//				Path path = Paths.get(string.getAbsolutePath());
//				Files.delete(path);
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
//		}
//		FileUtil.clean(fileOrDirPath);
//		Path path = Paths.get(fileOrDirPath);
//		Files.delete(path);
//	}
//
//}
