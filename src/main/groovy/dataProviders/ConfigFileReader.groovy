package dataProviders
import enums.*
import static org.junit.jupiter.api.Assertions.*
/**
 * This class avoids hardcoding URLs, BrowserTypes, and others to be hardcoded in the code.
 * It makes use of the Configuration.properties file to read certain things that are needed to run the code.
 */
class ConfigFileReader {
    /**
     * Properties object to read to
     */
    private Properties properties

    /**
     * Path in system to Configuration.properties file
     */
    private final String propertyFilePath = "configs/Configuration.properties"

    /**
     * Constructor of ConfigFileReader class.
     */
    ConfigFileReader() {
        BufferedReader reader
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath)) // try loading property file
            properties = new Properties()
            try {
                properties.load(reader) // try reading properties file
                reader.close()
            } catch (IOException e) {
                e.printStackTrace()
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace()
            throw new RuntimeException("Configuration.properties not found at $propertyFilePath")
        }
    }

    /**
     * Gets value of implicitly wait time of driver from the Configuration.properties file.
     * Gives exception when not specified in that file.
     *
     * @return long Time that driver should wait in seconds.
     */
    long getImplicitlyWait() {
        def implicitlyWait = properties.getProperty("implicitlyWait")
        if (implicitlyWait != null) {
            implicitlyWait as Integer
        } else {
            throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.")
        }
    }

    /**
     * Gets value of URL of the homepage of the application from the Configuration.properties file.
     * Gives exception when not specified in that file.
     *
     * @return String URL of the Homepage
     */
    String getApplicationHomeUrl() {
        def url = properties.getProperty("urlHome")
        if (url != null) {
            return url
        } else {
            throw new RuntimeException("urlHome not specified in the Configuration.properties file.")
        }
    }

    /**
     * Gets value of URL of the page where all the courses are listed from the Configuration.properties file.
     * Gives exception when not specified in that file.
     *
     * @return String URL of the CoursesPage
     */
    String getApplicationCoursesUrl() {
        def url = properties.getProperty("urlCourses")
        if (url != null) {
            return url
        } else {
            throw new RuntimeException("urlCourses not specified in the Configuration.properties file.")
        }
    }

    /**
     * Gets value of URL of the page where information about the Software Testing course is specified from the Configuration.properties file.
     * Gives exception when not specified in that file.
     *
     * @return String URL of the Software testing page
     */
    String getSoftwareTestingUrl() {
        def url = properties.getProperty("urlSoftwareTesting")
        if (url != null) {
            return url
        } else {
            throw new RuntimeException("urlSoftwareTesting is not specified in the Configuration.properties file.")
        }
    }

    /**
     * Gets type of browser you want to run the test on from the Configuration.properties file.
     * The different driver types are specified in enums.DriverType.
     * Gives exception when not specified in that file.
     *
     * @return DriverType type of driver to use
     */
    DriverType getBrowser() {
        def browserName = properties.getProperty("browser")
        if (browserName == null || browserName == "chrome") {
            return DriverType.CHROME
        } else if (browserName.equalsIgnoreCase("firefox")) {
            return DriverType.FIREFOX
        } else if (browserName == "iexplorer") {
            return DriverType.INTERNETEXPLORER
        } else if (browserName == "edge") {
            return DriverType.EDGE
        } else if (browserName == "safari") {
            return DriverType.SAFARI
        } else {
            throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : $browserName")
        }
    }

    /**
     * Gets type of environment you want to run the test on from the Configuration.properties file.
     * The different driver types are specified in enums.EnvironmentType.
     * Gives exception when not specified in that file.
     *
     * @return EnvironmentType type of environment to use
     */
    EnvironmentType getEnvironment() {
        def environmentName = properties.getProperty("environment")
        if (environmentName == null || environmentName.equalsIgnoreCase("local")) {
            return EnvironmentType.LOCAL
        } else if (environmentName == "remote") {
            return EnvironmentType.REMOTE
        } else {
            throw new RuntimeException("Environment Type Key value in Configuration.properties is not matched : $environmentName")
        }
    }

    /**
     * Gets value of windowMaximize from the Configuration.properties file.
     * Gives exception when not specified in that file.
     *
     * @return Boolean True if u want the browser to maximize the window while running the test
     */
    Boolean getBrowserWindowSize() {
        def windowSize = properties.getProperty("windowMaximize")
        if (windowSize != null) {
            return windowSize as boolean
        }
        return true
    }

    /**
     * Gets value of headless from the Configuration.properties file.
     * Gives exception when not specified in that file.
     * Note that running without head is not possible for all browsers, as specified in WebDriverManager.
     *
     * @return Boolean True when u want to run the tests without browser GUI
     */
    Boolean getHeadLess() {
        def headLess = properties.getProperty("headless")
        if (headLess != null) {
            return headLess as boolean
        }
        return true
    }

    /**
     * Specifies the operating system on which the program should run.
     *
     * @return OperatingSystemType Type of operating system to run test on
     */
    OperatingSystemType getOperatingSystem() {
        def operatingSystemName = properties.getProperty("operatingSystem")
        if (operatingSystemName == null || operatingSystemName == "windows") {
            return OperatingSystemType.WINDOWS
        } else if (operatingSystemName.equalsIgnoreCase("ubuntu")) {
            return OperatingSystemType.UBUNTU
        } else if (operatingSystemName.equalsIgnoreCase("linux")) {
            return OperatingSystemType.LINUX
        } else if (operatingSystemName.equalsIgnoreCase("macos")) {
            return OperatingSystemType.MACOS
        } else {
            throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : $operatingSystemName")
        }
    }
}
