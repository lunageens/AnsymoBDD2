package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// this is junit class
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", //specify the .feature file
        glue = "stepdefinitions", // specify the package with step definitions
        monochrome = true, // more readable console output
        plugin = {"pretty", // these are generates by cucumber and used by third party plugins such as extent report, is from cucumber-junit
                "html: target/cucumber-reports/Cucumber.html", // generates html report from console output using cucumber reporting plugin, in this directory
                "json:target/cucumber-reports/Cucumber.json", // generates same information in JSon format, to be process by other tools
                "junit:target/cucumber-reports/Cucumber.xml"}) // generates junit reports
public class RunnerTest {
    // this may be empty
}
