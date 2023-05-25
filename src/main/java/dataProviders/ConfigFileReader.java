package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import enums.*;

// class avoids hardcoding urls in code
public class ConfigFileReader {

    private Properties properties;
    private final String propertyFilePath= "configs//Configuration.properties";


    public ConfigFileReader(){
        BufferedReader reader;
        try {     // try loading property file
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
    }

    public String getApplicationHomeUrl() {
        String url = properties.getProperty("urlHome");
        if(url != null) return url;
        else throw new RuntimeException("urlHome not specified in the Configuration.properties file.");
    }

    public String getApplicationCoursesUrl() {
        String url = properties.getProperty("urlCourses");
        if(url != null) return url;
        else throw new RuntimeException("urlCourses not specified in the Configuration.properties file.");
    }

    public String getSoftwareTestingUrl(){
        String url = properties.getProperty("urlSoftwareTesting");
        if(url != null) return url;
        else throw new RuntimeException("urlSoftwareTesting is not specified in the Configuration.properties file.");
    }

    public DriverType getBrowser() {
        System.out.println("Type of browser is getting read.");
        String browserName = properties.getProperty("browser");
        if(browserName == null || browserName.equals("chrome")) return DriverType.CHROME;
        else if(browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
        else if(browserName.equals("iexplorer")) return DriverType.INTERNETEXPLORER;
        else if(browserName.equals("edge") )return DriverType.EDGE;
        else if(browserName.equals("safari")) return DriverType.SAFARI;
        else throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
    }

    public EnvironmentType getEnvironment() {
        System.out.println("Type of enviroment is getting read.");
        String environmentName = properties.getProperty("environment");
        if(environmentName == null || environmentName.equalsIgnoreCase("local")) return EnvironmentType.LOCAL;
        else if(environmentName.equals("remote")) return EnvironmentType.REMOTE;
        else throw new RuntimeException("Environment Type Key value in Configuration.properties is not matched : " + environmentName);
    }

    public Boolean getBrowserWindowSize() {
        String windowSize = properties.getProperty("windowMaximize");
        if(windowSize != null) return Boolean.valueOf(windowSize);
        return true;
    }

    public String getDriverPath(){
        String driverPath = properties.getProperty("driverPath");
        if(driverPath!= null) return driverPath;
        else throw new RuntimeException("Driver Path not specified in the Configuration.properties file for the Key:driverPath");
    }

    public Boolean getHeadLess(){
        String headLess = properties.getProperty("headless");
        if(headLess!= null) return Boolean.valueOf(headLess);
        return true;
    }

    public String getReportConfigPath(){
        // TODO use this
        String reportConfigPath = properties.getProperty("reportConfigPath");
        if(reportConfigPath!= null) return reportConfigPath;
        else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }

}
