package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("one")
public class BeanOne implements Bean123 {

	@Value("${bname:World}")
	private String bname;

	public BeanOne() {
		System.out.println("--------------");
		System.out.println("BeanOne init");
		System.out.println("--------------");
	}

	@Override
	public String getMassage() {
		// TODO Auto-generated method stub
		return "Hello " + bname;
	}

}
