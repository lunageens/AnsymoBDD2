package stepdefinitions;

import managers.PageObjectManager;
import managers.WebDriverManager;

/**
 * This class allows us to share one pageObjectManager and webDriverManager between Step files and Hooks file.
 */
public class BaseClass {

    /**
     * Instance of PageObjectManager in use for this scenario.
     */
    public PageObjectManager pageObjectManager;

    /**
     * Instance of WebDriverManager in use for this scenario.
     */
    public WebDriverManager webDriverManager;
}
