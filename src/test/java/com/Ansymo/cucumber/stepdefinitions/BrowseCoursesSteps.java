package com.Ansymo.cucumber.stepdefinitions;

// annotations
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
// driver set up and functions
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxOptions;

// this class is stepdefinitions - stepdefinionts written in java? with help selenium?
// location in project: src/test/java/com.Ansymo.cucumber.stepdefinitions package
// TODO -> do i still need to use Groovy as a scripting language, since these are juist plain java files. Or is my assignment okay already?
// dit kan dus ook in Groovy ipv java
// to enable to creat step definitions in Groovy, the Cucumber for groovy plugin must be installed and enabled. Also extra
// plug-in in pom.xml file
public class BrowseCoursesSteps {

    private WebDriver driver;

    @Before
    public void setUp() {
        // geckodriver is an application server implementing selenium protocol, translated the the selenium commands
        // and forwards them to the marionette driver
        // this marionette is het new driver that is included with firefox,
        // has own protocol that is not directly compatible with the selenium webdriver protocol
        //  for my specific project, might need to change paths in order for program to work
        // setting path is not really needed for firefox version > 79
        // TODO -> change paths to actual project directory you saved the project in!!
        System.setProperty("webdriver.gecko.driver", "C:\\ST\\AnsymoBDD2\\drivers\\geckodriver.exe");
        driver = new FirefoxDriver();

        // for debugging purposes - remove later
        String currentURL = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentURL);

    }

    // TODO - add pages pattern and change all methods in this class accordingly
    // real scenario exercise 5 in BrowseCourses.feature
    @Given("the user navigates to {string} homepage")
    public void navigateToHomepage(String url) {
        // Implementation for navigating to the homepage
        driver.get(url);
    }

    @When("the user clicks the {string} link in the menu section")
    public void clickCoursesLink(String linkText) {
        // Implementation for clicking the "Courses" link
        WebElement coursesLink = driver.findElement(By.linkText(linkText));
        coursesLink.click();
    }

    @Then("the user should see a page with all the courses listed")
    public void verifyCoursesPage() {
        // TODO implementation for verifying the courses page
    }

    @Then("for each course, the user should see the name of the course and the name of the professor teaching the course")
    public void verifyCourseAndProfessorNames() {
        // TODO implementation for verifying course and professor names, maybe in two methods?
    }

    // dummy scenario for exercise 5 in BrowseCourses.feature
    @Given("a dummy scenario")
    public void a_dummy_scenario() {
        // no actual implementation
    }

    @After
    public void tearDown() {
        // Clean up WebDriver
        if (driver != null) {
            driver.quit();
        }
    }

}
