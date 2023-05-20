package com.Ansymo.cucumber.stepdefinitions;

import com.Ansymo.cucumber.stepdefinitions.pages.*;
// annotations
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
// driver set up and functions
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

// this class is stepdefinitions - stepdefinionts written in java? with help selenium?
// location in project: src/test/java/com.Ansymo.cucumber.stepdefinitions package
// TODO -> do i still need to use Groovy as a scripting language, since these are juist plain java files. Or is my assignment okay already?
// dit kan dus ook in Groovy ipv java
// to enable to creat step definitions in Groovy, the Cucumber for groovy plugin must be installed and enabled. Also extra
// plug-in in pom.xml file
public class BrowseCoursesSteps {

    private WebDriver driver;
    private HomePage homePage;
    private CoursesPage coursesPage;
    private CourseDetailsPage courseDetailsPage;

    @Before
    public void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true); // Run firefox in headless mode (without GUI)
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
    }

    @Given("the user navigates to {string} homepage")
    public void navigateToHomepage(String url) {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage(url);
    }

    @When("the user clicks the {string} link in the menu section")
    public void clickCoursesLink(String linkText) {
        homePage.clickMenuItem(linkText);
    }

    @Then("the user should see a page with all the courses listed")
    public void verifyCoursesPage() {
        coursesPage = new CoursesPage(driver);
        Assert.assertTrue("There are no courses", coursesPage.areCoursesListed());
    }

    @Then("for each course, there should be a page that is loaded and there should be a professor")
    public void verifyCourseAndProfessorNames() {
        coursesPage.selectEachCourseAndVerifyDetails();
    }

    // dummy scenario for exercise 5 in BrowseCourses.feature
    @Given("a dummy scenario")
    public void a_dummy_scenario() {
        // no actual implementation
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
