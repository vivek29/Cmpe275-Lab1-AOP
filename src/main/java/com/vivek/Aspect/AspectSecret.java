package com.vivek.Aspect;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.vivek.SecretMain;
import com.vivek.CustomException.SecretException;
import com.vivek.Model.Secret;

@Component
@Aspect
public class AspectSecret {

	@Pointcut("execution(* com.vivek.Service.SecretService.storeSecret(..))")
	public void storeSecret() {}
	
	@Pointcut("execution(* com.vivek.Service.SecretService.readSecret(..))")
	public void readSecret() {}

	@Pointcut("execution(* com.vivek.Service.SecretService.shareSecret(..))")
	public void shareSecret() {}
	
	@Pointcut("execution(* com.vivek.Service.SecretService.unshareSecret(..))")
	public void unshareSecret() {}
		
	
	/**
	 @Before Advice validating the new secret to be stored
	 * @param joinPoint
	 * @throws SecretException
	 */
	@Before("storeSecret()")
	public void storeSecretAdvice(JoinPoint joinPoint) throws SecretException{
		Object args[] = joinPoint.getArgs();

		try{
			
			String userId = (String) args[0];			// get the userId
			if(userId==null || userId=="")
				throw new NullPointerException();
			
			Secret s = (Secret) args[1];				// get the secret
			String key = userId+s.getId();				// get the key
			
			if(SecretMain.ownerSecrets.containsKey(key))
				throw new SecretException("Key already exists..");
		}
		
		catch(NullPointerException e){
			throw new SecretException("UserId cann't be null.."); 
		}
	}
	
	/**
	 @Before Advice validating and authorizing for reading the secret 
	 * @param joinPoint
	 * @throws SecretException
	 */
	@Before("readSecret()")
	public void readSecretAdvice(JoinPoint joinPoint) throws SecretException{
		Object args[] = joinPoint.getArgs();

		try{
			String userId = (String) args[0];					// get the userId
			UUID uuid = (UUID) args[1];							// get the UUID
			
			if(userId==null || userId=="" || uuid==null)
				throw new NullPointerException();
			
			String key = userId+uuid;							// get the key
			
			if(!SecretMain.ownerSecrets.containsKey(key) || !SecretMain.shareSecrets.containsKey(key))
				throw new SecretException("Unauthorized exception");
		}
		
		catch(NullPointerException e){
			throw new SecretException("UserId cann't be null.."); 
		}
	}

	/**
	 @Before Advice validating and authorizing for sharing and unsharing of secret
	 * @param joinPoint
	 * @throws SecretException
	 */
	@Before("shareSecret() || unshareSecret()")
	public void shareSecretAdvice(JoinPoint joinPoint) throws SecretException{
		Object args[] = joinPoint.getArgs();

		try{
			String userId = (String) args[0];						// get the userId
			UUID uuid = (UUID) args[1];								// get the UUID
			String targetId = (String) args[2];
			
			if(userId==null || userId=="" || uuid==null || targetId==null || targetId=="")
				throw new NullPointerException();
			
			String key = userId+uuid;								// get the key
			
			if(!SecretMain.ownerSecrets.containsKey(key) || !SecretMain.shareSecrets.containsKey(key))
				throw new SecretException("Unauthorized exception");
		}
	
		catch(NullPointerException e){
			throw new SecretException("UserId cann't be null.."); 
		}
	}

}
