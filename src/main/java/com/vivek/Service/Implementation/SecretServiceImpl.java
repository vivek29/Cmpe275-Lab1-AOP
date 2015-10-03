package com.vivek.Service.Implementation;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.vivek.SecretMain;
import com.vivek.Service.SecretService;
import com.vivek.model.Secret;

@Component
public class SecretServiceImpl implements SecretService {

	/**
	 Store a new secret using storeSecret
	 @param userid is the user who wants to store the new secret
	 @param secret is the new secret which is to be added to db0
	 */
	public UUID storeSecret(String userId, Secret secret) {

		String key = userId+"#"+secret.getId();
		SecretMain.personalSecrets.put(key,secret);
		return secret.getId();
	}

	/**
	 Reading a secret using readSecret
	 @param userid is the user who wants to read the secret
	 @param secretUUIS is the id of the secret which is to be read
	 */
	public Secret readSecret(String userId, UUID secretUUID) {
		
		String key = userId+"#"+secretUUID;
		if(SecretMain.personalSecrets.containsKey(key))
			return SecretMain.personalSecrets.get(key);
		else
			return SecretMain.shareSecrets.get(key);		
	}

	/**
	 Sharing of a secret using shareSecret
	 @param userid is the user who wants to share the secret
	 @param UUID the secret key which is to be shared
	 @param targetId is the user id with whom the UUID is shared
	 */
	public void shareSecret(String userId, UUID secretUUID, String targetId) {
		
		if(!userId.equals(targetId)){
			String ownerKey = userId+"#"+secretUUID;
			String key = targetId+"#"+secretUUID;
			SecretMain.shareSecrets.put(key, SecretMain.personalSecrets.get(ownerKey));
		}
		
	}

	/**
	 Unsharing of a secret using unshareSecret
	 @param userid is the user who wants to unshare the secret
	 @param UUID the secret key which is to be unshared
	 @param targetId is the user id with whom the UUID is unshared
	 */
	public void unshareSecret(String userId, UUID secretUUID, String targetId) {
		
		if(!userId.equals(targetId)){
			String ownerKey = userId+"#"+secretUUID;
			if(SecretMain.personalSecrets.containsKey(ownerKey))
			{
				String key = targetId+"#"+secretUUID; 
				SecretMain.shareSecrets.remove(key);
			}
		}
	}
	
	
}
