package com.vivek.Service;
import java.util.HashMap;
import com.vivek.Model.Secret;

/**
 * @author Vivek
 The data service, HashMaps used.
 */

public class DBService {
	
	public static HashMap<String, Secret> ownerSecrets;
	public static HashMap<String, Secret> shareSecrets;

	/**
	 Constrcutor to initialize the hashmaps
	 */
	public DBService(){
		ownerSecrets = new HashMap<String, Secret>();
		shareSecrets = new HashMap<String, Secret>();
	}
	
}
