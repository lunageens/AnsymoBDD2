package managers;

import dataProviders.ConfigFileReader;

/**
 * To control object creation of all readers
 * Sometimes it's appropriate to have exactly one instance of a class.
 * These are accessed by disparate objects throughout a software system, and therefore require a global point of access.
 * In our case, we have ConfigReaderFile, which should be accessed globally.
 * But later on in this Selenium Cucumber Framework series, we will be having many more file readers.
 * So it is better to have a File Reader Manager above all the File Readers. And it is better to make the manager class as singleton.
 */
public class FileReaderManager {

    private static FileReaderManager fileReaderManager = new FileReaderManager(); // only instance of this class
    private static ConfigFileReader configFileReader;

    private FileReaderManager() { // private constructor to restrict intiation of the class from other claess
    }

    public static FileReaderManager getInstance( ) { // this is public, only global access point to get the instance
        return fileReaderManager;
    }

    public ConfigFileReader getConfigReader() { // if we dont have one, make one. Otherwise, use the one we have
        return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
    }
}