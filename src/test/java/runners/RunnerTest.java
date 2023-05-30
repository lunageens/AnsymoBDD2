package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// TODO Change name feature file, README file, in runner class, and where else is needed

// TODO Menu items should be loaded, not all courses. Check that in Exercise 6 with script!!!
// TODO Maybe remove the output For each course and professor? How can we see output in the reports?
// TODO Warning when the assignment link does not exists in ex 7, no error. Check this
// TODO Explanation input and output Exercise 7 in README?
// TODO Change given step in @Exercise8 - only need name and no group number, need two dates as well (?)

// TODO Make different step file for Exercise 6, 7 and 8. Give it appropriate name and adjust this here. Also in runner.
// TODO TestContext for different step files?


// TODO Implement Allure reports and alter Runner class. Add explanation in README. Deploy to Netlify.
// TODO BrowserStack testing? How with Config file?

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
                "junit:target/cucumber-reports/Cucumber.xml" // generates junit reports
        })

public class RunnerTest {

    // this may be empty

}
