package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//该类是个大宝贝
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	public SpringUtil() {
	}

	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		if (applicationContext == null) {
			applicationContext = arg0;
		}

	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setAppCtx(ApplicationContext webAppCtx) {
		if (webAppCtx != null) {
			applicationContext = webAppCtx;
		}
	}

	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) throws ClassNotFoundException {
		return getApplicationContext().getBean(name, clazz);
	}

	public static final Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	@SuppressWarnings("rawtypes")
	public static final Object getBean(String beanName, String className) throws ClassNotFoundException {
		Class clz = Class.forName(className);
		return getApplicationContext().getBean(beanName, clz.getClass());
	}
}
