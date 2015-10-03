package com.vivek;
import java.util.UUID;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vivek.Model.Secret;
import com.vivek.Service.SecretService;


public class SecretMain {
	
	public static HashMap<String, Secret> ownerSecrets;
	public static HashMap<String, Secret> shareSecrets;

	public static void main(String[] args) {
		
		ownerSecrets = new HashMap<String, Secret>();
		shareSecrets = new HashMap<String, Secret>();
		

		ApplicationContext ctx = new ClassPathXmlApplicationContext("aspect.xml");
		
		SecretService ss = (SecretService) ctx.getBean("secretServiceImpl");
		Secret s = (Secret) ctx.getBean("secret1");
		
		
	}
	
	
}
