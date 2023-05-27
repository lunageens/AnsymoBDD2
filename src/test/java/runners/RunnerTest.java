package runners;

// When Extent reports are implemented: import com.vimalselvam.cucumber.listener.Reporter;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// TODO Implement Extent reports and alter this class.
// TODO Check out online reports of Cucumber themselves
// TODO BrowserStack? How with Config file?
// TODO How to suppress the warnings I get?
/**
 * JUnit class that specifies how Cucumber should run tests.
 */

@SuppressWarnings("unchecked")
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", //specify the .feature file
        glue = "stepdefinitions", // specify the package with step definitions
        monochrome = true, // more readable console output
        tags = "@CompleteTest", //tests in features files i want to run
        publish = true, //get online test results
        plugin = {"pretty", // these are generated by cucumber and used by third party plugins such as extent report, is from cucumber-junit
                "html:target/cucumber-reports/Cucumber.html", // generates html report from console output using cucumber reporting plugin, in this directory
                "json:target/cucumber-reports/Cucumber.json", // generates same information in JSon format, to be process by other tools
                "junit:target/cucumber-reports/Cucumber.xml" // generates junit reports
        })
// For Extent reports: plugin= {..., "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/AdvancedReport.html"}
public class RunnerTest {
    // this may be empty

    //For Extent Reports:
    // @AfterClass
    // public static void writeExtentReport(){
    // Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
    //}
}
