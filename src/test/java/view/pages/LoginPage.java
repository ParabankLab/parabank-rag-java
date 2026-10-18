package view.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private Page page;

    //Locators
    private final String usernameInput = "input[name='username']";
    private final String passwordInput = "input[name='password']";
    private final String loginButton = "button[type='submit']";

}
