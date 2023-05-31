package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// TODO Extra reporting?

/**
 * JUnit class that specifies how Cucumber should run tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", //specify the .feature file
        glue = "stepdefinitions", // specify the package with step definitions
        monochrome = true, // more readable console output
        tags = "@CompleteTest", //tests in features files i want to run
        publish = true, //get online test results
        plugin = {"pretty", // these are generated by cucumber and used by third party plugins such as extent report, is from cucumber-junit
                "html:target/cucumber-reports/cucumber-html-reports/Cucumber.html", // generates html report from console output using cucumber reporting plugin, in this directory
                // extra directory for cleaner integration with deploying settings netlify
                "json:target/cucumber-reports/Cucumber.json", // generates same information in JSon format, to be process by other tools
                "junit:target/cucumber-reports/Cucumber.xml", // generates junit reports
        })
public class RunnerTest {
    // this may be empty
}
