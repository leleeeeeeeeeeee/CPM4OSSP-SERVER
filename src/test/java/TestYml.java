import cn.hutool.core.io.resource.ResourceUtil;
import io.jpom.system.ConfigBean;
import org.junit.Test;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TestYml {

	@Test
	public void test() throws IOException {
		String path = "D:\\Idea\\Jpom\\modules\\agent\\src\\main\\resources\\bin\\extConfig.yml";
		YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
		URL resource = ResourceUtil.getResource(path);
		List<PropertySource<?>> test = yamlPropertySourceLoader.load("test", new FileUrlResource(path));
		PropertySource<?> propertySource = test.get(0);
		System.out.println(propertySource);

		Object user = propertySource.getProperty(ConfigBean.AUTHORIZE_USER_KEY);
		System.out.println(user);
	}
}
