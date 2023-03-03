import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.VersionComparator;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class TestPath {

	public static void main(String[] args) {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		System.out.println(antPathMatcher.match("/s/**/sss.html", "//s/s/s/sss.html"));
		System.out.println(antPathMatcher.match("/s/*.html", "/s/sss.html"));
		System.out.println(antPathMatcher.match("2.*", "2.5"));
	}

	@Test
	public void testSort() {
		ArrayList<String> list = CollUtil.newArrayList("dev", "master", "v1.1", "v0.4", "v.1", "v3.5.2", "v3.6", "v3.5.3");
		list.sort((o1, o2) -> VersionComparator.INSTANCE.compare(o2, o1));
		list.forEach(System.out::println);
	}

	@Test
	public void testFilePath() {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		List<String> paths = new ArrayList<>();
		File rootFile = FileUtil.file("~/jpom");
		String matchStr = "/agent/**/log";
		matchStr = FileUtil.normalize(StrUtil.SLASH + matchStr);
		String finalMatchStr = matchStr;
		FileUtil.walkFiles(rootFile.toPath(), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				return this.test(file);
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes exc) throws IOException {
				return this.test(dir);
			}

			private FileVisitResult test(Path path) {
				String subPath = FileUtil.subPath(FileUtil.getAbsolutePath(rootFile), path.toFile());
				subPath = FileUtil.normalize(StrUtil.SLASH + subPath);
				if (antPathMatcher.match(finalMatchStr, subPath)) {
					paths.add(subPath);
					return FileVisitResult.TERMINATE;
				}
				return FileVisitResult.CONTINUE;
			}
		});
		paths.forEach(System.out::println);

	}


}
