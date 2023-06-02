package managers

import enums.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.safari.SafariDriver

import java.time.Duration



/**
 * Allows us to let the user specify different types of browsers and environments and run the test correctly.
 * On top of that, it makes more sense to handle the logic of creating and quitting the WebDriver in a separate
 * class instead of in the tests or Hooks. The test should not be worried about how to start the driver but just
 * need a driver to execute the script.
 */
class WebDriverManager {
    /**
     * WebDriver used in this scenario.
     */
    private WebDriver driver

    /**
     * Type of driver to use, specified in Configuration.properties.
     */
    private static DriverType driverType

    /**
     * Type of environment to use, specified in Configuration.properties.
     */
    private static EnvironmentType environmentType

    /**
     * Logger used for this class. Enables the warnings we need to implement for some tests.
     */
    private static final Logger logger = LoggerFactory.getLogger(WebDriverManager.class)

    /**
     * Constructor of the Webdriver manager, where driverType and EnvironmentType are read by calling FileReaderManager.
     */
    WebDriverManager() {
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser()
        environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment()
    }

    /**
     * Together with closeDriver(), the only public method.
     * getDriver() method will decide if the driver is already created or needs to be created.
     * getDriver() method further calls the createDriver() method if needed.
     *
     * @return driver from type and environment specified in config
     */
    WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver()
        }
        assertNotNull(this.driver, "driver is empty")
        return driver
    }

    /**
     * Will decide if the remote driver is needed or local driver for the execution.
     * Calls appropriate methods to create local or remote driver.
     *
     * @return driver from type and environment specified in config
     */
    private WebDriver createDriver() {
        switch (environmentType) {
            case EnvironmentType.LOCAL:
                driver = createLocalDriver()
                break
            case EnvironmentType.REMOTE:
                driver = createRemoteDriver()
                break
        }
        assertNotNull(this.driver, "driver is empty")
        return driver
    }

    /**
     * Throws an exception when remote driver is asked in Configuration.properties file,
     * since this is not yet implemented.
     *
     * @return WebDriver Remote Web Driver that was asked.
     */
    private WebDriver createRemoteDriver() {
        throw new RuntimeException("RemoteWebDriver is not yet implemented")
    }

    /**
     * Creates a local driver of the type specified in the Configuration.properties file.
     * Sets the driver headless when asked and possible with that type, gives a notification when not possible and creates a driver with the head.
     * Maximizes the window of the driver when asked.
     * Sets the implicitly wait (in seconds) of the driver that is also specified in the Configuration.properties file.
     *
     * @return WebDriver Local webdriver of the right type, with a headless option if asked and possible, with maximized windows when asked, and with the specified implicitly wait time.
     */
    private WebDriver createLocalDriver() {
        switch (driverType) {
            case DriverType.FIREFOX:
                FirefoxOptions optionsFirefox = new FirefoxOptions()
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) {
                    optionsFirefox.addArguments("--headless") // Run Firefox in headless mode (without GUI)
                }
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null") // disable the logs by sending them to /dev/null via a system property
                driver = new FirefoxDriver(optionsFirefox)
                break
            case DriverType.CHROME:
                ChromeOptions optionsChrome = new ChromeOptions()
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) {
                    optionsChrome.addArguments('--headless')
                }
                driver = new ChromeDriver(optionsChrome)
                break
            case DriverType.INTERNETEXPLORER:
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) {
                    logger.info('Headless mode not possible in Internet Explorer. Running with GUI.')
                }
                driver = new InternetExplorerDriver()
                break
            case DriverType.SAFARI:
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) {
                    logger.info('Headless mode not possible in Safari. Running with GUI.')
                }
                driver = new SafariDriver()
                break
            case DriverType.EDGE:
                EdgeOptions optionsEdge = new EdgeOptions()
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) {
                    optionsEdge.addArguments("--headless")
                }
                driver = new EdgeDriver(optionsEdge)
                break
            default:
                throw new IllegalStateException('Unexpected value: ' + driverType as String)
        }

        // extra settings - if maximizes needed to happen and implicitly wait
        if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize()) {
            driver.manage().window().maximize()
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait()))
        assertNotNull(driver, "The driver at the end of createLocalDriver() is null")
        return driver
    }

    /**
     * Closes the driver in use safely.
     */
    void closeDriver() {
        driver.quit()
        this.driver = null
    }

    /**
     * It's essential to ensure that you are closing the browser instances correctly to prevent unnecessary memory usage.
     * I encountered multiple Firefox applications or instances of the Windows Driver Foundation after running the tests,
     * which indicates that the browser processes are not being terminated properly.
     *
     * To address this, I modified WebDriverManager and Hooks class to explicitly kill any remaining browser processes
     * after each test.
     */
    static void killBrowserProcesses() {
        DriverType driverType = FileReaderManager.getInstance().getConfigReader().getBrowser() // Get the current driver type
        OperatingSystemType operatingSystemType = FileReaderManager.getInstance().getConfigReader().getOperatingSystem() // Get the operating system
        try {
            switch (driverType) {
                case driverType.FIREFOX:
                    switch (operatingSystemType) {
                        case operatingSystemType.WINDOWS:
                            Runtime.getRuntime().exec("taskkill /F /IM firefox.exe")
                            break
                        case (operatingSystemType.LINUX) || (operatingSystemType.UBUNTU) || (operatingSystemType.MACOS):
                            Runtime.getRuntime().exec("pkill firefox")
                            break
                    }
                    break
                case driverType.CHROME:
                    switch (operatingSystemType) {
                        case operatingSystemType.WINDOWS:
                            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe")
                            break
                        case (operatingSystemType.LINUX) || (operatingSystemType.UBUNTU) || (operatingSystemType.MACOS):
                            Runtime.getRuntime().exec("pkill chrome")
                            break
                    }
                    break
                case driverType.EDGE:
                    switch (operatingSystemType) {
                        case operatingSystemType.WINDOWS:
                            Runtime.getRuntime().exec("taskkill /F /IM msedge.exe")
                            break
                        case (operatingSystemType.LINUX) || (operatingSystemType.UBUNTU) || (operatingSystemType.MACOS):
                            logger.info("Handling Edge termination on this operating system is not yet implemented. Program could take up a lot of memory.")
                            break
                    }
                    break
                case driverType.SAFARI:
                    switch (operatingSystemType) {
                        case operatingSystemType.WINDOWS:
                            Runtime.getRuntime().exec("taskkill /F /IM Safari.exe")
                            break
                        case operatingSystemType.MACOS:
                            Runtime.getRuntime().exec("pkill Safari")
                            break
                        case (operatingSystemType.LINUX) || (operatingSystemType.UBUNTU):
                            logger.info("Handling Safari termination on this operating system is not yet implemented. Program could take up a lot of memory.")
                            break
                    }
                    break
                case DriverType.INTERNETEXPLORER:
                    switch (operatingSystemType) {
                        case OperatingSystemType.WINDOWS:
                            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe")
                            break
                        case OperatingSystemType.MACOS:
                            logger.info("Internet Explorer is not available on macOS")
                            break
                        case (operatingSystemType.LINUX) || (operatingSystemType.UBUNTU):
                            logger.info("Handling Internet Explorer termination on this operating system is not yet implemented. Program could take up a lot of memory.")
                            break
                    }
                    break
                default:
                    break
            }
        } catch (IOException e) {
            e.printStackTrace()
        }
    }
}
