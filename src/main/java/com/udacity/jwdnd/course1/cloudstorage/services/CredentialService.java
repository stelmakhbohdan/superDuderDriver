package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {


    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;
    private final AuthenticatedUserService authenticatedUser;


    public CredentialService(CredentialsMapper credentialMapper, EncryptionService encryptionService, AuthenticatedUserService authenticatedUser) {
        this.credentialsMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.authenticatedUser = authenticatedUser;
    }

    public int addCredential(CredentialForm credentialForm) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);


        return credentialsMapper.addCredential(new Credentials(0, credentialForm.getUrl(),
                credentialForm.getUserName(),

                encodedKey,
                encryptedPassword,
                authenticatedUser.getLoggedInUserId()));
    }


    public void updateCredential(CredentialForm credentialForm) {
        Credentials credential = credentialsMapper.getCredential(credentialForm.getCredentialId());
        credential.setUserName(credentialForm.getUserName());
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        credential.setUrl(credentialForm.getUrl());
        credentialsMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialsMapper.deleteCredential(credentialId);
    }

    public Credentials getCredentialsEncoded() {
        return credentialsMapper.getCredential(authenticatedUser.getLoggedInUserId());
    }


}