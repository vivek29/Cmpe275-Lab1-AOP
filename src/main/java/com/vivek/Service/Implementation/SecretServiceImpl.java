package com.vivek.Service.Implementation;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.vivek.Model.Secret;
import com.vivek.Service.DBService;
import com.vivek.Service.SecretService;

/**
 * @author Vivek
 Implementation class of the SecretService Interface
 */

@Component
public class SecretServiceImpl implements SecretService {

	/**
	 Store a new secret using storeSecret
	 @param userid is the user who wants to store the new secret
	 @param secret is the new secret which is to be added to db0
	 */
	public UUID storeSecret(String userId, Secret secret) {
		
		secret.generateUUID();							// generate the UUID first
		String key = userId+"&"+secret.getId();				// get the key(userid+uuid)
		DBService.ownerSecrets.put(key,secret);
		return secret.getId();							// return the generated UUID
	}

	/**
	 Reading a secret using readSecret
	 @param userid is the user who wants to read the secret
	 @param secretUUIS is the id of the secret which is to be read
	 */
	public Secret readSecret(String userId, UUID secretId) {
		
		String key = userId+"&"+secretId;						// get the key
		if(DBService.ownerSecrets.containsKey(key))
			return DBService.ownerSecrets.get(key);
		else
			return DBService.shareSecrets.get(key);		
	}

	/**
	 Sharing of a secret using shareSecret
	 @param userid is the user who wants to share the secret
	 @param UUID the secret key which is to be shared
	 @param targetId is the user id with whom the UUID is shared
	 */
	public void shareSecret(String userId, UUID secretId, String targetUserId) {
		
		String ownerKey = userId+"&"+secretId;				// get the owner key
		String key = targetUserId+secretId;					// get the key
		DBService.shareSecrets.put(key, DBService.ownerSecrets.get(ownerKey));
	}

	/**
	 Unsharing of a secret using unshareSecret
	 @param userid is the user who wants to unshare the secret
	 @param UUID the secret key which is to be unshared
	 @param targetId is the user id with whom the UUID is unshared
	 */
	public void unshareSecret(String userId, UUID secretId, String targetUserId) {
		
		String key = targetUserId+"&"+secretId;				// get the key
		DBService.shareSecrets.remove(key);			// remove it			
	}
}