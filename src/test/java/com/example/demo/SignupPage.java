package com.example.demo;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private final JavascriptExecutor js;
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;


    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }


    public void signUp(String firstName,String lastName,String userName,String password) {
        js.executeScript("arguments[0].value='"+ firstName +"';", inputFirstName);
        js.executeScript("arguments[0].value='"+ lastName +"';", inputLastName);
        js.executeScript("arguments[0].value='"+ userName +"';", inputUserName);
        js.executeScript("arguments[0].value='"+ password +"';", inputPassword);
        js.executeScript("arguments[0].click();", submitButton);
    }

}

