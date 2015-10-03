package com.vivek.Service.Implementation;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.vivek.SecretMain;
import com.vivek.Model.Secret;
import com.vivek.Service.SecretService;

@Component
public class SecretServiceImpl implements SecretService {

	/**
	 Store a new secret using storeSecret
	 @param userid is the user who wants to store the new secret
	 @param secret is the new secret which is to be added to db0
	 */
	public UUID storeSecret(String userId, Secret secret) {

		String key = userId+secret.getId();				// get the key
		SecretMain.ownerSecrets.put(key,secret);
		return secret.getId();
	}

	/**
	 Reading a secret using readSecret
	 @param userid is the user who wants to read the secret
	 @param secretUUIS is the id of the secret which is to be read
	 */
	public Secret readSecret(String userId, UUID uuid) {
		
		String key = userId+uuid;						// get the key
		if(SecretMain.ownerSecrets.containsKey(key))
			return SecretMain.ownerSecrets.get(key);
		else
			return SecretMain.shareSecrets.get(key);		
	}

	/**
	 Sharing of a secret using shareSecret
	 @param userid is the user who wants to share the secret
	 @param UUID the secret key which is to be shared
	 @param targetId is the user id with whom the UUID is shared
	 */
	public void shareSecret(String userId, UUID uuid, String targetId) {
		
		if(!userId.equals(targetId)){
			String ownerKey = userId+uuid;				// get the owner key
			String key = targetId+uuid;					// get the key
			SecretMain.shareSecrets.put(key, SecretMain.ownerSecrets.get(ownerKey));
		}
		
	}

	/**
	 Unsharing of a secret using unshareSecret
	 @param userid is the user who wants to unshare the secret
	 @param UUID the secret key which is to be unshared
	 @param targetId is the user id with whom the UUID is unshared
	 */
	public void unshareSecret(String userId, UUID uuid, String targetId) {
		
		if(!userId.equals(targetId)){
			String ownerKey = userId+uuid;				// get the owner key
			if(SecretMain.ownerSecrets.containsKey(ownerKey))
			{
				String key = targetId+uuid;				// get the key
				SecretMain.shareSecrets.remove(key);
			}
		}
	}
	
	
}
