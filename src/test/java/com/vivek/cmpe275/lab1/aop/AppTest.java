package com.vivek.cmpe275.lab1.aop;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vivek.Exception.UnauthorizedException;
import com.vivek.Model.Secret;
import com.vivek.Service.DBService;
import com.vivek.Service.SecretService;


/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	ApplicationContext ctx;
	SecretService secretService;
	
	@Autowired
	DBService dBService;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     Set-up, get beans.
     */
    @Override
	protected void setUp() throws Exception {
		super.setUp();
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		secretService = (SecretService) ctx.getBean("secretServiceImpl");
	}
    
    /** TestA
    Bob cannot read Alice’s secret, which has not been shared with Bob. 
    "Unauthorized Exception"
     */
    @org.junit.Test
	public void testA(){
		System.out.println("testA");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId = secretService.storeSecret("Alice", secret);
		try{
			secretService.readSecret("Bob", secretId);
			assertTrue(false);
		}
		catch(UnauthorizedException e1){
			System.out.println(e1.getMessage());
			assertTrue(true);
		}
	}
    
    /** TestB
     * Alice shares a secret with Bob, and Bob can read it.
     */
    @org.junit.Test
    public void testB(){
		System.out.println("testB");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId = secretService.storeSecret("Alice", secret);
		try{
			secretService.shareSecret("Alice", secretId, "Bob");
			secretService.readSecret("Bob", secretId);
			assertTrue(true);
		}
		catch(UnauthorizedException e1){
			assertTrue(false);
		}
    }
    /** Test C
     * Alice shares a secret with Bob, and Bob shares Alice’s it with Carl, and Carl can read this secret.
     */
    @org.junit.Test
	public void testC(){
		System.out.println("testC");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId = secretService.storeSecret("Alice", secret);
		try{
			secretService.shareSecret("Alice", secretId, "Bob");
			secretService.shareSecret("Bob", secretId, "Carl");
			secretService.readSecret("Carl", secretId);
			assertTrue(true);
		}
		catch(UnauthorizedException e1){
			System.out.println(e1.getMessage());
			assertTrue(false);
		}
	}
    
    /** Test D
     * Alice shares her secret with Bob; Bob shares Carl’s secret with Alice and encounters UnauthorizedException
     */
    @org.junit.Test
	public void testD(){
		System.out.println("testD");
		Secret secret_Alice = (Secret) ctx.getBean("secret");
		UUID secretId_Alice = secretService.storeSecret("Alice", secret_Alice);
		Secret secret_Carl = (Secret) ctx.getBean("secret");
		UUID secretId_Carl = secretService.storeSecret("Carl", secret_Carl);
		try{
			secretService.shareSecret("Alice", secretId_Alice, "Bob");
			secretService.shareSecret("Bob", secretId_Carl, "Alice");
			assertTrue(false);
		}
		catch(UnauthorizedException e1){
			System.out.println(e1.getMessage());
			assertTrue(true);
		}
	}
    
    /** Test E
     * Alice shares a secret with Bob, Bob shares it with Carl, Alice unshares it with Carl,
     * and Carl cannot read this secret anymore.
     */
    @org.junit.Test
	public void testE(){
		System.out.println("testE");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId = secretService.storeSecret("Alice", secret);
		try{
			secretService.shareSecret("Alice", secretId, "Bob");
			secretService.shareSecret("Bob", secretId, "Carl");
			secretService.unshareSecret("Alice", secretId, "Carl");
			secretService.readSecret("Carl", secretId);
			assertTrue(false);
		}
		catch(UnauthorizedException e1){
			System.out.println(e1.getMessage());
			assertTrue(true);
		}
	}
    
    /** Test F
     * Alice shares a secret with Bob and Carl; Carl shares it with Bob, then Alice unshares it with Bob;
     * Bob cannot read this secret anymore.
     */
    @org.junit.Test
	public void testF(){
		System.out.println("testF");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId = secretService.storeSecret("Alice", secret);
		try{
			secretService.shareSecret("Alice", secretId, "Bob");
			secretService.shareSecret("Alice", secretId, "Carl");
			secretService.shareSecret("Carl", secretId, "Bob");
			secretService.unshareSecret("Alice", secretId, "Bob");
			secretService.readSecret("Bob", secretId);
			assertTrue(false);
		}
		catch(UnauthorizedException e1){
			System.out.println(e1.getMessage());
			assertTrue(true);
		}
	}
    
    /** Test G
     * Alice shares a secret with Bob; Bob shares it with Carl, and then unshares it with Carl.
     * Carl can still read this secret.
     */
    @org.junit.Test
	public void testG(){
		System.out.println("testG");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId = secretService.storeSecret("Alice", secret);
		try{
			secretService.shareSecret("Alice", secretId, "Bob");
			secretService.shareSecret("Bob", secretId, "Carl");
			secretService.unshareSecret("Bob", secretId, "Carl");
			secretService.readSecret("Carl", secretId);
			assertTrue(true);
		}
		catch(UnauthorizedException e1){
			assertTrue(false);
		}
	}
    
    /** Test H
     * Alice shares a secret with Bob; Carl unshares it with Bob, and encounters UnauthorizedException
     */
    @org.junit.Test
	public void testH(){
		System.out.println("testH");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId = secretService.storeSecret("Alice", secret);
		try{
			secretService.shareSecret("Alice", secretId, "Bob");
			secretService.unshareSecret("Carl", secretId, "Bob");
			assertTrue(false);
		}
		catch(UnauthorizedException e1){
			System.out.println(e1.getMessage());
			assertTrue(true);
		}
	}
    
    /** Test I
     * Alice shares a secret with Bob; Bob shares it with Carl; Alice unshares it with Bob;
     * Bob shares it with Carl with again, and encounters UnauthorizedException
     */
    @org.junit.Test
	public void testI(){
		System.out.println("testI");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId = secretService.storeSecret("Alice", secret);
		try{
			secretService.shareSecret("Alice", secretId, "Bob");
			secretService.shareSecret("Bob", secretId, "Carl");
			secretService.unshareSecret("Alice", secretId, "Bob");
			secretService.shareSecret("Bob", secretId, "Carl");
			assertTrue(false);
		}
		catch(UnauthorizedException e1){
			System.out.println(e1.getMessage());
			assertTrue(true);
		}
	}
    
    /** Test J
     * Alice stores the same secrete object twice, and get two different UUIDs
     */
    @org.junit.Test
	public void testJ(){
		System.out.println("testJ");
		Secret secret = (Secret) ctx.getBean("secret");
		UUID secretId1 = secretService.storeSecret("Alice", secret);
		UUID secretId2 = secretService.storeSecret("Alice", secret);
		
		boolean isSameSecretId = (secretId1==secretId2);
		assertEquals(false, isSameSecretId);
	}
}