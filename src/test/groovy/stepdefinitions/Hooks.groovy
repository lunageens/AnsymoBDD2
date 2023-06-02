package stepdefinitions

import enums.DriverType

import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.AfterAll
import io.cucumber.java.BeforeAll
import managers.FileReaderManager
import managers.PageObjectManager
import managers.WebDriverManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.jupiter.api.Assertions.*

/**
 * This class specifies some things that should happen before and after each scenario and test run.
 */
class Hooks extends BaseClass {

    /**
     * Baseclass currently in use.
     */
    private BaseClass base

    /**
     * Logger used for this class. Enables the warnings we need to implement for some tests.
     */
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class)


    /**
     * Constructor of Hooks class
     *
     * @param base baseclass currently in use
     */
    Hooks(BaseClass base) {
        this.base = base
    }

    /**
     * Before all scenario's, give notification about old versions of Firefox.
     */
    @BeforeAll
    static void beforeTesting() {
        if (FileReaderManager.getInstance().getConfigReader().getBrowser() == DriverType.FIREFOX) {
            logger.info("When using an old version of firefox, specify path to geckodriver in Configuration file.")
            logger.info("This means there is a need for an additional geckodriver (such as installed for windows 64 in the drivers directory). ")
            logger.info("Adjust the code in WebDriverManager.java as follows:")
            String message = "add line: System.set.Property(" + "webdriver.chrome.driver" + ", FileReaderManager.getInstance().getConfigReader.getDriverPath())" + " before initializing new driver"
            logger.info(message)
        }
    }

    /**
     * Before each scenario, initiate new WebDriverManager and PageObjectManager.
     */
    @Before
    void beforeScenario() {
        base.webDriverManager = new WebDriverManager()
        assertNotNull(base.webDriverManager,"Webdrivermanager created is empty")
        base.pageObjectManager = new PageObjectManager(base.webDriverManager.getDriver())
        assertNotNull(base.pageObjectManager, "PageObjectManager created is emtpy", )
        assertNotNull(base.webDriverManager.getDriver(), "driver is empty", )
    }

    /**
     * After each scenario, tear down the driver.
     */
    @After()
    void afterScenario() {
        base.webDriverManager.closeDriver()
    }

    /**
     * After all tests are ran, kill all processes related to that driver.
     */
    @AfterAll
    static void tearDownAll() {
        WebDriverManager.killBrowserProcesses()
    }
}
