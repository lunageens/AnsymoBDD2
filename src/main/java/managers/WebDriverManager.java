package managers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.edge.EdgeDriver;
import enums.*;

public class WebDriverManager {

    private WebDriver driver;
    private static DriverType driverType;
    private static EnvironmentType environmentType;

    public WebDriverManager() { // read drivertype from config file, with singleton pattern of filereadermanager
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
    }

    /**
     * Together with closeDriver() only public method.
     * GetDriver method will decide if the driver is already created or needs to be created.
     * GetDriver method further call the method createDriver() if needed.
     * @return driver from type and environment specified in config
     */
    public WebDriver getDriver() {
        if(driver == null) driver = createDriver();
        return driver;
    }

    /**
     * Will decide that the remote driver is needed or local driver for the execution.
     * Calls appropriate methods to create local or remote driver
     * @return driver from type and environment specified in config
     */
    private WebDriver createDriver() {
        switch (environmentType) {
            case LOCAL : driver = createLocalDriver();
                break;
            case REMOTE : driver = createRemoteDriver();
                break;
        }
        return driver;
    }

    private WebDriver createRemoteDriver() {
        throw new RuntimeException("RemoteWebDriver is not yet implemented");
    }

    private WebDriver createLocalDriver() {
        switch (driverType) {
            case FIREFOX :
                System.out.println("When using an old version of firefox, specify path to geckodriver in Configuration file.");
                System.out.println("This means there is a need for an additional geckodriver (such as installed in for windows 64 in the drivers directory). ");
                System.out.println("Adjust the code in WebDriverManager.java as follows:");
                System.out.println("add line: System.set.Property(" + "webdriver.chrome.driver" + "; FileReaderManager.getInstance().getConfigReader.getDriverPath())" +
                        " before initializing new driver");
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) optionsFirefox.setHeadless(true); // Run firefox in headless mode (without GUI)
                driver = new FirefoxDriver(optionsFirefox);
                break;
            case CHROME :
                ChromeOptions optionsChrome = new ChromeOptions();
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) optionsChrome.setHeadless(true);
                driver = new ChromeDriver(optionsChrome);
                break;
            case INTERNETEXPLORER :
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) System.out.println("Headless mode not possible in Internet Explorer. Running with GUI.");
                driver = new InternetExplorerDriver();
                break;
            case SAFARI:
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) System.out.println("Headless mode not possible in Safari. Running with GUI.");
                driver = new SafariDriver();
            case EDGE:
                EdgeOptions optionsEdge = new EdgeOptions();
                if (FileReaderManager.getInstance().getConfigReader().getHeadLess()) optionsEdge.setHeadless(true);
                driver = new EdgeDriver(optionsEdge);
        }

        // extra settings -  if maximizes needed to happen and implicity wait
        if(FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize()) driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
        return driver;
    }

    public void closeDriver() {
        driver.close();
        driver.quit();
    }

}

