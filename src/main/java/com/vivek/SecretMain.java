package com.vivek;
import java.util.UUID;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vivek.Service.SecretService;
import com.vivek.model.Secret;


public class SecretMain {
	
	public static HashMap<String, Secret> personalSecrets;
	public static HashMap<String, Secret> shareSecrets;

	public static void main(String[] args) {
		
		personalSecrets = new HashMap<String, Secret>();
		shareSecrets = new HashMap<String, Secret>();
		

		ApplicationContext ctx = new ClassPathXmlApplicationContext("aspect.xml");
		
		SecretService ss = (SecretService) ctx.getBean("secretServiceImpl");
		Secret s = (Secret) ctx.getBean("secret1");
		
		
	}
	
	
}
