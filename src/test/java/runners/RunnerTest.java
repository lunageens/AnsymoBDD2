package runners;

// import com.vimalselvam.cucumber.listener.Reporter;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import managers.FileReaderManager;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;

// this is junit class
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", //specify the .feature file
        glue = "stepdefinitions", // specify the package with step definitions
        monochrome = true, // more readable console output
        tags = "@CompleteTest",
        plugin = {"pretty", // these are generates by cucumber and used by third party plugins such as extent report, is from cucumber-junit
                "html:target/cucumber-reports/Cucumber.html", // generates html report from console output using cucumber reporting plugin, in this directory
                "json:target/cucumber-reports/Cucumber.json", // generates same information in JSon format, to be process by other tools
                "junit:target/cucumber-reports/Cucumber.xml" // generates junit reports
                }) // "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/AdvancedReport.html"
public class RunnerTest {
    // this may be empty

    // @AfterClass
    // public static void writeExtentReport(){
        // Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
    //}
}
