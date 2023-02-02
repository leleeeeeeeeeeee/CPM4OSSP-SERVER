package mpms;

import cn.jiangzeyin.common.EnableCommonBoot;
import cn.jiangzeyin.common.spring.event.ApplicationEventLoad;
import mpms.common.Type;
import mpms.common.interceptor.IpInterceptor;
import mpms.common.interceptor.LoginInterceptor;
import mpmscommon.interceptor.OpenApiInterceptor;
import mpms.common.interceptor.PermissionInterceptor;
import mpms.permission.CacheControllerFeature;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * jpom 启动类
 *
 */
@SpringBootApplication
@ServletComponentScan
@EnableCommonBoot
//开启定时任务(也可以放在启动类上)
@EnableScheduling
//开启事物
@EnableTransactionManagement
public class JpomServerApplication implements ApplicationEventLoad {


	/**
	 * 启动执行
	 *
	 * @param args 参数
	 * @throws Exception 异常
	 */
	public static void main(String[] args) throws Exception {
		JpomApplication jpomApplication = new JpomApplication(Type.Server, JpomServerApplication.class, args);
		jpomApplication
				// 拦截器
				.addInterceptor(IpInterceptor.class)
				.addInterceptor(LoginInterceptor.class)
				.addInterceptor(OpenApiInterceptor.class)
				.addInterceptor(PermissionInterceptor.class)
				.run(args);
	}


	@Override
	public void applicationLoad() {
		CacheControllerFeature.init();
	}
}
