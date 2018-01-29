package com.goldmsg.gmvcs.syn;

import com.goldmsg.gmvcs.syn.core.config.LogConfig;
import com.goldmsg.gmvcs.syn.core.config.SystemConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@MapperScan(basePackages = {"com.goldmsg.gmvcs.syn.mapper"})
@EnableWebMvc
/**
 * cmd 启动 后面加 --spring.config.location=%dist_etc%\application.properties
 *
 * */
@EnableConfigurationProperties({SystemConfig.class})
public class Application{


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
