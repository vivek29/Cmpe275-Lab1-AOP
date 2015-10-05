package com.vivek.Aspect;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.vivek.Exception.UnauthorizedException;
import com.vivek.Model.Secret;
import com.vivek.Service.DBService;

/**
 * @author Vivek
 LoggingAspect to validate the requests and authorize them or unauthorize them (throw Unauthorized Exception)
 */
@Component
@Aspect
public class LoggingAspect {

	/**
	 Pointcut for storing a secret
	 */
	@Pointcut("execution(* com.vivek.Service.SecretService.storeSecret(..))")
	public void storeSecret() {}
	
	/**
	 Poincut for reading a secret
	 */
	@Pointcut("execution(* com.vivek.Service.SecretService.readSecret(..))")
	public void readSecret() {}

	/**
	 Pointcut for sharing a secret
	 */
	@Pointcut("execution(* com.vivek.Service.SecretService.shareSecret(..))")
	public void shareSecret() {}
	
	/**
	 Pointcut for unsharing a secret
	 */
	@Pointcut("execution(* com.vivek.Service.SecretService.unshareSecret(..))")
	public void unshareSecret() {}

	
	/**
	 @Before Advice validating the new secret to be stored
	 * @param joinPoint
	 * @throws UnauthorizedException
	 */
	@Before("storeSecret()")
	public void storeSecretBeforeAdvice(JoinPoint joinPoint) throws UnauthorizedException{
		Object args[] = joinPoint.getArgs();

		try{
			
			String userId = (String) args[0];			// get the userId
			Secret secret = (Secret) args[1];			// get the secret
			if(userId==null || userId=="" || secret==null)
				throw new NullPointerException();
			}
		
		catch(NullPointerException e){
			throw new UnauthorizedException("Invalid userId or Secret.. Cann't be null.."); 
		}
	}

	/**
	 Advice which runs after successfully storing a secret
	 * @param joinPoint
	 * @param secret
	 * @throws UnauthorizedException
	 */
	@AfterReturning(pointcut="storeSecret()",returning = "secret")
	public void storeSecretAfterReturningAdvice(JoinPoint joinPoint, Object secret) throws UnauthorizedException{
		
		Object args[] = joinPoint.getArgs();
		
		String userId = (String) args[0];			// get the userId
		UUID secretId = (UUID) secret;				// get the secretId
		System.out.println(userId+" creates a secret with ID "+secretId);		
	}
	
	/**
	 @Before Advice validating and authorizing of reading the secret 
	 * @param joinPoint
	 * @throws UnauthorizedException
	 */
	@Before("readSecret()")
	public void readSecretAdvice(JoinPoint joinPoint) throws UnauthorizedException{
		Object args[] = joinPoint.getArgs();

		try{
			String userId = (String) args[0];					// get the userId
			UUID secretId = (UUID) args[1];						// get the secretId
			
			if(userId==null || userId=="" || secretId==null)
				throw new NullPointerException();
			System.out.println(userId+" reads the secret of ID "+secretId);
			
			String key = userId+"&"+secretId;							// get the key
			
			if(!DBService.ownerSecrets.containsKey(key) && !DBService.shareSecrets.containsKey(key))
				throw new UnauthorizedException("Unauthorized exception");
		}
		
		catch(NullPointerException e){
			throw new UnauthorizedException("Invalid userId or secretId.. Cann't be null.."); 
		}
	}

	/**
	 @Before Advice validating and authorizing of sharing the secret
	 * @param joinPoint
	 * @throws UnauthorizedException
	 */
	@Before("shareSecret()")
	public void shareSecretAdvice(JoinPoint joinPoint) throws UnauthorizedException{
		Object args[] = joinPoint.getArgs();

		try{
			String userId = (String) args[0];						// get the userId
			UUID secretId = (UUID) args[1];							// get the secretId
			String targetUserId = (String) args[2];					// get the targetUserId
			
			if(userId==null || userId=="" || secretId==null || targetUserId==null || targetUserId=="")
				throw new NullPointerException();
			System.out.println(userId+" shares the secret of ID "+secretId+" with "+targetUserId);
			
			String key = userId+"&"+secretId;								// get the key
			
			if(!DBService.ownerSecrets.containsKey(key) && !DBService.shareSecrets.containsKey(key))
				throw new UnauthorizedException("Unauthorized exception");
		}
	
		catch(NullPointerException e){
			throw new UnauthorizedException("Invalid userId or secretId or targetUserId.. Cann't be null.."); 
		}
	}

	/**
	 @Around Advice validating and authorizing of unsharing the secret 
	 * @param joinPoint
	 * @throws UnauthorizedException
	 */
	@Around("unshareSecret()")
	public void unshareSecretAdvice(ProceedingJoinPoint pjp) throws UnauthorizedException{
		Object args[] = pjp.getArgs();

		try{
			String userId = (String) args[0];						// get the userId
			UUID secretId = (UUID) args[1];							// get the secretId
			String targetUserId = (String) args[2];					// get the targetUserId
			
			if(userId==null || userId=="" || secretId==null || targetUserId==null || targetUserId=="")
				throw new NullPointerException();
			System.out.println(userId+" unshares the secret of ID "+secretId+" with "+targetUserId);
			
			String key = userId+"&"+secretId;						// get the key
		
			if(DBService.ownerSecrets.containsKey(key))
				pjp.proceed();										// if authorized, than only proceed	
			else if(!DBService.shareSecrets.containsKey(key))
				throw new Throwable();
		}	
	
		catch(NullPointerException e){
			throw new UnauthorizedException("Invalid userId or secretId or targetUserId.. Cann't be null.."); 
		}
		catch (Throwable e) {
			throw new UnauthorizedException("Unauthorized Exception");
		}
	}	
}