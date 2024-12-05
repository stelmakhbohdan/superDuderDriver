package com.example.demo;

import com.example.demo.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CredentialCRUDTest extends ApplicationTests {

    public static final String URL = "http://www.dudosart.net/";
    public static final String USERNAME = "dukuze";
    public static final String PASSWORD = "orla";
    public static final String EDIT_URL = "http://www.dudosart.net/";
    public static final String EDIT_USERNAME = "dukuze";
    public static final String EDIT_PASSWORD = "orla";
    @Test
    public void createCredentialTest() throws InterruptedException {
        HomePage homePage = getHomePage();
        Thread.sleep(2000);
        createAndVerifyCredential(URL, USERNAME, PASSWORD, homePage);
        Thread.sleep(2000);
        homePage.deleteCredential();
        homePage.logout();
    }
    @Test
    public void readUpdateCredentialTest() throws InterruptedException {
        HomePage homePage = getHomePage();
        createAndVerifyCredential(URL, USERNAME, PASSWORD, homePage);
        Credential credential = homePage.getFirstCredential();
        String firstEncryptedPassword = credential.getPassword();
        homePage.editCredential();
        String newUrl = EDIT_URL;
        String newCredentialUsername = EDIT_USERNAME;
        String newPassword = EDIT_PASSWORD;
        setCredentialFields(newUrl, newCredentialUsername, newPassword, homePage);
        homePage.saveCredentialChanges();
        homePage.navToCredentialsTab();
        Credential modifiedCredential = homePage.getFirstCredential();
        Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
        Assertions.assertEquals(newCredentialUsername, modifiedCredential.getUsername());
        String modifiedCredentialPassword = modifiedCredential.getPassword();
        Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
        Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);
        homePage.deleteCredential();
        homePage.logout();
    }
    @Test
    public void deletionCredentialTest() throws InterruptedException {
        HomePage homePage = getHomePage();
        createCredential(URL, USERNAME, PASSWORD, homePage);
        createCredential(EDIT_URL, EDIT_USERNAME, EDIT_PASSWORD, homePage);
        createCredential("http://www.google.com/", "google", "imana", homePage);
        Assertions.assertFalse(homePage.noCredentials(driver));
        homePage.deleteCredential();
        homePage.navToCredentialsTab();
        homePage.deleteCredential();
        homePage.navToCredentialsTab();
        homePage.deleteCredential();
        homePage.navToCredentialsTab();
        Assertions.assertTrue(homePage.noCredentials(driver));
        homePage.logout();
    }
    private void createAndVerifyCredential(String url, String username, String password, HomePage homePage) throws InterruptedException {
        createCredential(url, username, password, homePage);
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUsername());
        Assertions.assertNotEquals(password, credential.getPassword());
    }

    private void createCredential(String url, String username, String password, HomePage homePage) throws InterruptedException {
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        setCredentialFields(url, username, password, homePage);
        Thread.sleep(2000);
        homePage.saveCredentialChanges();
        Thread.sleep(4000);

        homePage.navToCredentialsTab();
    }

    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }
}
