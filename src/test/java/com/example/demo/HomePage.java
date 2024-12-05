package com.example.demo;

import com.example.demo.model.Credential;
import com.example.demo.model.Notes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {@FindBy(name = "logoutBtn")
private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(name = "addNote")
    private WebElement addNewNoteBtn;

    @FindBy(id = "addCredentialBtn")
    private WebElement addCredentialBtn;

    @FindBy(id = "note-title")
    private WebElement txtNoteTitle;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "note-description")
    private WebElement txtNoteDescription;

    @FindBy(name = "saveChanges")
    private WebElement btnSaveChanges;

    @FindBy(name = "noteTableTitle")
    private WebElement tableNoteTitle;

    @FindBy(name = "noteTableDesc")
    private WebElement tableNoteDescription;

    @FindBy(id = "noteEditBtn")
    private WebElement noteEditBtn;

    @FindBy(id = "editCredential")
    private WebElement btnEditCredential;

    @FindBy(id = "note-description")
    private WebElement txtModifyNoteDescription;

    @FindBy(name = "noteDelBtn")
    private WebElement deleteNoteBtn;

    @FindBy(id = "deleteCredential")
    private WebElement aDeleteCredential;

    @FindBy(id = "credential-url")
    private WebElement txtCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement txtCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement txtCredentialPassword;

    @FindBy(id = "saveCredential")
    private WebElement btnCredentialSaveChanges;

    @FindBy(id = "tblCredentialUrl")
    private WebElement tblCredentialUrl;

    @FindBy(id = "tblCredentialUsername")
    private WebElement tblCredentialUsername;

    @FindBy(id = "tblCredentialPassword")
    private WebElement tblCredentialPassword;

    private final JavascriptExecutor js;

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void logout() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public void editNote() {
        js.executeScript("arguments[0].click();", noteEditBtn);
    }

    public void editCredential() {
        js.executeScript("arguments[0].click();", btnEditCredential);
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click();", deleteNoteBtn);
    }

    public void deleteCredential() {
        js.executeScript("arguments[0].click();", aDeleteCredential);
    }

    public void uploadFile() {
        js.executeScript("arguments[0].click();", fileUpload);
    }

    public void addNewNote() {
        js.executeScript("arguments[0].click();", addNewNoteBtn);
    }

    public void addNewCredential() {
        js.executeScript("arguments[0].click();", addCredentialBtn);
    }

    public void setNoteTitle(String noteTitle) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", txtNoteTitle);
    }

    public void setCredentialUrl(String url) {
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
    }

    public void setCredentialUsername(String username) {
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
    }

    public void setCredentialPassword(String password) {
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
    }

    public void modifyNoteTitle(String newNoteTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).sendKeys(newNoteTitle);
    }

    public void modifyNoteDescription(String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).sendKeys(newNoteDescription);
    }

    public void openNotesTab() {
        js.executeScript("arguments[0].click();", navNotesTab);
    }

    public void navToCredentialsTab() {
        js.executeScript("arguments[0].click();", navCredentialsTab);
    }

    public void setNoteDescription(String noteDescription) {
        js.executeScript("arguments[0].value='"+ noteDescription +"';", txtNoteDescription);
    }

    public void saveNoteChanges() {
        js.executeScript("arguments[0].click();", btnSaveChanges);
    }

    public void saveCredentialChanges() {
        js.executeScript("arguments[0].click();", btnCredentialSaveChanges);
    }

    public boolean noNotes(WebDriver driver) {
        return !isElementPresent(By.name("noteTableTitle"), driver) && !isElementPresent(By.name("noteTableDesc"), driver);
    }

    public boolean noCredentials(WebDriver driver) {
        return !isElementPresent(By.id("tblCredentialUrl"), driver) &&
            !isElementPresent(By.id("tblCredentialUsername"), driver) &&
            !isElementPresent(By.id("tblCredentialPassword"), driver);
    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public Notes getFirstNote() {
        String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = tableNoteDescription.getText();

        return new Notes(title, description);
    }

    public Credential getFirstCredential() {
        String url = wait.until(ExpectedConditions.elementToBeClickable(tblCredentialUrl)).getText();
        String username = tblCredentialUsername.getText();
        String password = tblCredentialPassword.getText();

        return new Credential(url, username, password);
    }
}
