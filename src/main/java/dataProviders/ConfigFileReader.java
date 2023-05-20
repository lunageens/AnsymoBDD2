package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

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
}
