package com.udacity.jwdnd.course1.cloudstorage.services;

import mapper.CredentialMapper;
import model.Credential;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private AuthenticatedUserService authenticatedUserService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, AuthenticatedUserService authenticatedUserService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.authenticatedUserService = authenticatedUserService;
    }

    public int addCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptionPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);

        return credentialMapper.addCredentials(new Credential(0,credential.getUrl(),credential.getUsername()
                ,encodedSalt,encryptionPassword,authenticatedUserService.getLoggedInUserId()));
    }

    public List<Credential> getCredential(){
        return credentialMapper.getCredentials(authenticatedUserService.getLoggedInUserId());
    }

    public void updateCredential(Credential credentials){
        Credential credential = credentialMapper.getCredential(credentials.getCredentialid());

        credential.setUsername(credentials.getUsername());

        String encPassword = encryptionService.encryptValue(credentials.getPassword(),credential.getKey());

        credential.setPassword(encPassword);

        credential.setUrl(credentials.getUrl());

        credentialMapper.updateCredentials(credential);
    }

    public void deleteCredential(Integer credentialid){
        credentialMapper.deleteCredential(credentialid);
    }
}
