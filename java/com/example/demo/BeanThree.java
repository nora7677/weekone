package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class BeanThree implements Bean123 {

	@Value("${bname:World}")
	private String bname;

	public BeanThree() {
		System.out.println("--------------");
		System.out.println("BeanThree init");
		System.out.println("--------------");
	}

	@Override
	public String getMassage() {
		// TODO Auto-generated method stub
		return "Hello " + bname;
	}

}
