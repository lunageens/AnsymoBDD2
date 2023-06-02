package managers
import dataProviders.ConfigFileReader

/**
 * To control object creation of all readers, we use the Singleton Pattern.
 * It's appropriate to have exactly one instance of a class that has a global point of access in the system.
 * In our case, we have ConfigReaderFile, which should be accessed globally.
 * But later on in this Selenium Cucumber Framework series, we will have many more file readers.
 * So it is better to have a FileReaderManager above all the file readers, and it is better to make the manager class a singleton.
 */
class FileReaderManager {
    /**
     * One instance of FileReaderManager that is used in the whole system.
     */
    static FileReaderManager fileReaderManager = new FileReaderManager() // Only instance of this class

    /**
     * Instance of ConfigFileReader that is used.
     */
    static ConfigFileReader configFileReader

    /**
     * Private constructor of FileReaderManager
     */
    private FileReaderManager() {
        // Private constructor to restrict initiation of the class from other classes
    }

    /**
     * Get one instance of FileReaderManager in the whole system.
     * Will be used to globally access that one instance.
     *
     * @return FileReaderManager Instance of FileReaderManager that is in use
     */
    static FileReaderManager getInstance() {
        // This is public, the only global access point to get the instance
        fileReaderManager
    }

    /**
     * Get the one ConfigReader in the whole system.
     * If there is no ConfigFileReader created yet, make a new one.
     * Otherwise, use the one we have.
     * This allows us to not make many instances of ConfigFileReader.
     *
     * @return ConfigFileReader The one instance of this class that is used in the system.
     */
    ConfigFileReader getConfigReader() {
        // If we don't have one, make one. Otherwise, use the one we have.
        return (configFileReader == null) ? configFileReader = new ConfigFileReader(): configFileReader
    }
}
