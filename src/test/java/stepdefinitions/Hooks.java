package stepdefinitions;

import enums.DriverType;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import managers.FileReaderManager;
import managers.PageObjectManager;
import managers.WebDriverManager;
import org.junit.Assert;

/**
 * This class specifies some things that should happen before and after each scenario and test run.
 */
public class Hooks extends BaseClass {

    /**
     * Baseclass currently in use.
     */
    private BaseClass base;

    /**
     * Constructor of Hooks class
     *
     * @param base baseclass currently in use
     */
    public Hooks(BaseClass base) {
        this.base = base;
    }

    /**
     * Before all scenario's, give notification about old versions of Firefox.
     */
    @BeforeAll
    public static void beforeTesting() {
        if (FileReaderManager.getInstance().getConfigReader().getBrowser() == DriverType.FIREFOX) {
            System.out.println("When using an old version of firefox, specify path to geckodriver in Configuration file.");
            System.out.println("This means there is a need for an additional geckodriver (such as installed in for windows 64 in the drivers directory). ");
            System.out.println("Adjust the code in WebDriverManager.java as follows:");
            System.out.println("add line: System.set.Property(" + "webdriver.chrome.driver" + "; FileReaderManager.getInstance().getConfigReader.getDriverPath())" +
                    " before initializing new driver");
        }
    }

    /**
     * Before each scenario, initiate new WebDriverManager and PageObjectManager.
     */
    @Before
    public void beforeScenario() {
        base.webDriverManager = new WebDriverManager();
        Assert.assertNotNull("Webdrivermanager created is empty", base.webDriverManager);
        base.pageObjectManager = new PageObjectManager(base.webDriverManager.getDriver());
        Assert.assertNotNull("PageObjectManager created is emtpy", base.pageObjectManager);
        Assert.assertNotNull("driver is empty", base.webDriverManager.getDriver());
        System.out.println("------------------------------------------------------start of scenario------------------------------------------------------");
    }

    /**
     * After each scenario, tear down the driver.
     */
    @After()
    public void afterScenario() {
        base.webDriverManager.closeDriver();
        System.out.println("------------------------------------------------------end of scenario------------------------------------------------------");
    }

    /**
     * After all tests are ran, kill all processes related to that driver.
     */
    @AfterAll
    public static void tearDownAll(){ WebDriverManager.killBrowserProcesses();}
}
