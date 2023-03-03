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
//
//	public static void main(String[] args) throws GitAPIException, IOException, URISyntaxException {
//
//		Git git = Git.init().setDirectory(new File("D:\\tttt")).call();
//		RemoteConfig remoteConfig = Git.open(new File("D:\\tttt")).remoteAdd().setUri(new URIish("https://gitee.com/keepbx/Jpom.git")).call();
//		System.out.println(remoteConfig);
//
//		List<RemoteConfig> call = git.remoteList().call();
//		System.out.println(call);
//		git.pull().call();
//		List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
//		List<String> all = new ArrayList<>(list.size());
//		list.forEach(ref -> {
//			String name = ref.getName();
//			if (name.startsWith(Constants.R_REMOTES + Constants.DEFAULT_REMOTE_NAME)) {
//				all.add(name.substring((Constants.R_REMOTES + Constants.DEFAULT_REMOTE_NAME).length() + 1));
//			}
//		});
//		System.out.println(all);
//	}
//
//
//	@Test
//	public void testTag() throws Exception {
//		String uri = "https://gitee.com/dromara/Jpom.git";
//		File file = FileUtil.file("~/test/jpomgit");
//		String tagName = "v2.5.2";
//		String branchName = "stand-alone";
//
//		PrintWriter printWriter = new PrintWriter(System.out);
//		RepositoryModel repositoryModel = new RepositoryModel();
//		repositoryModel.setGitUrl(uri);
//		repositoryModel.setRepoType(RepositoryModel.RepoType.Git.getCode());
//		repositoryModel.setUserName("a");
//		repositoryModel.setPassword("a");
//		repositoryModel.setProtocol(GitProtocolEnum.HTTP.getCode());
//
//		GitUtil.checkoutPullTag(repositoryModel, file, branchName, tagName, printWriter);
//
//	}
//
//
//
//}
