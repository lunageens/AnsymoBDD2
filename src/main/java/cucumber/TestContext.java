package cucumber;

import managers.PageObjectManager;
import managers.WebDriverManager;

/**
 * Provides us the ability to have multiple Step classes, using Picocontainer (or another Dpeendency Injection Container)
 * Cucumber tells picocontainer to instantiate your step definition classes and wire them up correctly.
 * Handle all the information all step files are using at once, without creating new instances of PageObjects, webdriver, .. for every step file
 * In our case, step files are using:
 * 1) PageObjects: provided by PageObjectManager
 * 2) WebDriver: provided by WebDriverManager
 * 3) Properties: provided by FileReaderManager (already in Singleton Class, and to use we use getInstance() method so no need to add)
 */
public class TestContext {
    private WebDriverManager webDriverManager;
    private PageObjectManager pageObjectManager;

    public TestContext(){
        webDriverManager = new WebDriverManager();
        pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
    }

    public WebDriverManager getWebDriverManager() {
        return webDriverManager;
    }

    public PageObjectManager getPageObjectManager() {
        return pageObjectManager;
    }


}
