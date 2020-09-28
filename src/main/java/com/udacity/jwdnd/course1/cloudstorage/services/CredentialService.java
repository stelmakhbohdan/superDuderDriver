package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private UserMapper userMapper;

    private EncryptionService encryptionService;

    public CredentialService (CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllUserCredentials(int userid) {
        return credentialMapper.getCredentials(userid);
    }

    public void createCredential(CredentialForm credentialModalForm, String username) {
        String randomKey = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credentialModalForm.getPassword(), randomKey);
        Credential newCredential = new Credential(
                null,
                credentialModalForm.getUrl(),
                credentialModalForm.getUsername(),
                randomKey,
                encryptedPassword,
                userMapper.getUserIdByUsername(username)
        );

        credentialMapper.addCredentials(newCredential);
    }

    public void updateCredential(CredentialForm credentialModalForm, String username) {

        String randomKey = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credentialModalForm.getPassword(), randomKey);
        Credential selectedCredential = new Credential(
                Integer.parseInt(credentialModalForm.getCredentialid()),
                credentialModalForm.getUrl(),
                credentialModalForm.getUsername(),
                randomKey,
                encryptedPassword,
                userMapper.getUserIdByUsername(username)
        );

        credentialMapper.updateCredentials(selectedCredential);
    }

    public void deleteCredential(int credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

}