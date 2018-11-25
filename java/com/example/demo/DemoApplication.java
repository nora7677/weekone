package com.example.demo;

import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import lombok.Data;

// 读取配置文件中前缀为“my”的属性值，并注入到类的属性中
// 配置文件有两种：properties和yaml两种，当两种同时存在时，适用properties的配置
// 命令行执行jar文件时，可以通过再命令行后添加“--spring.profiles.active=${配置文件后缀}”来指定激活某个配置文件的设置
// 若需读取自己创建的配置文件，使用@PropertySource("${自建配置文件绝对路径}")注解,也可通过命令行参数进行配置
// lombok插件：
// 使用@Profile({})注解实现不同配置文件调用的情况
@Data
@ConfigurationProperties(prefix = "my")
@SpringBootApplication
@RestController
public class DemoApplication {

	DemoApplication() {
		System.out.println("--------------");
		System.out.println("DemoApplication init");
		System.out.println("--------------");
	}

	private String name;
	private int age;
	private int sex;

	@Autowired
	private Bean123 b123;

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		System.out.println("service start begin");
		SpringApplication sapp = new SpringApplication(DemoApplication.class);
		sapp.run(args);
		System.out.println("service start finish");

		DemoApplication dapp = SpringUtil.getBean(DemoApplication.class);
		System.out.println(dapp);
		System.out.println(dapp.getB123().getMassage());
	}

	@PostConstruct
	public void logSomething() {
		LOGGER.debug("simple debug message");
		LOGGER.info("simple info message");
		LOGGER.trace("simple trace message");
		LOGGER.error("simple error message");
		LOGGER.warn("simple warn message");
	}

	@RequestMapping("/hello")
	public String testsay() {
		return "Hello " + name + ",age is " + age;
	}

	@Bean
	public static CommandLineRunner testA() {
		CommandLineRunner clr = new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				System.out.println("command line runner init");

			}

		};
		return clr;
	}

	@Bean
	public static ApplicationRunner testB() {
		return args -> {
			System.out.println("");
			System.out.println("Application Runner:");
			for (String opt : args.getOptionNames()) {
				System.out.println(opt + "->" + args.getOptionValues(opt).stream().collect(Collectors.joining()));
			}
		};
	}

	@Bean
	public ApplicationListener<ApplicationEvent> helloListener() {
		final String HELLO_URL = "/hello";
		return (ApplicationEvent ae) -> {
			if (ae instanceof ServletRequestHandledEvent) {
				ServletRequestHandledEvent arhe = (ServletRequestHandledEvent) ae;
				if (arhe.getRequestUrl().equals(HELLO_URL)) {
					System.out.println("visit hello");
				}
			}
		};
	}
}
