package stepdefinitions;

import cucumber.TestContext;
import dataProviders.ConfigFileReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.FileReaderManager;
import managers.PageObjectManager;
import managers.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pageObjects.CoursesPage;
import pageObjects.HomePage;
import pageObjects.SoftwareTestingPage;

import java.util.concurrent.TimeUnit;

// TODO -> do i still need to use Groovy as a scripting language, since these are just plain java files. Or is my assignment okay already?
// dit kan dus ook in Groovy ipv java
// to enable to creat step definitions in Groovy, the Cucumber for groovy plugin must be installed and enabled. Also extra
// plug-in in pom.xml file

/**
 * Steps for exercise 4.
 */
public class BrowseCoursesSteps {

    private HomePage homePage;
    private CoursesPage coursesPage;
    private TestContext testContext;
    private SoftwareTestingPage softwareTestingPage;

    public BrowseCoursesSteps(TestContext context){
        testContext = context;
        homePage = testContext.getPageObjectManager().getHomePage();
    }

    @Given("the user is on the Ansymo homepage")
    public void navigateToHomepage() {
        homePage.navigateToHomePage();
    }

    @When("the user clicks the {string} link in the menu section")
    public void clickCoursesLink(String linkText) {
        homePage.clickMenuItem(linkText);
    }

    @Then("the user should see a page with all the courses listed")
    public void verifyCoursesPage() {
        coursesPage = testContext.getPageObjectManager().getCoursesPage(); // instead of creating new courses page every single time
        Assert.assertTrue("There are no courses", coursesPage.areCoursesListed());
    }

    @Then("for each course, there should be a page that is loaded and there should be a professor")
    public void verifyCourseAndProfessorNames() {
        coursesPage.selectEachCourseAndVerifyDetails(testContext);
    }

    @Given("the user is on the Software Testing course page")
    public void navigateToSoftwareTestingPage(){
        softwareTestingPage = testContext.getPageObjectManager().getSoftwareTestingPage();
        softwareTestingPage.navigateToSoftWareTesting();
    }

    @When("the user sees the links for each assignment")
    public void areThereAssignmentsLinksPresent(){
        Assert.assertTrue("There are no assignment links.", softwareTestingPage.verifyAssignmentsLinkPresent());
    }

    @Then("the user should verify the link format of each assignment link")
    public void verifyAssignmentsLinkFormat(){
        softwareTestingPage.verifyAssignmentsLinkFormat();
    }

    @Then("the user should verify the document existence of each assignment link")
    public void verifyAssignmentsLinkExistence(){
        softwareTestingPage.verifyAssignmentsLinkExistence();
    }

    @Given("dummy")
    public void endOfAllTests(){

    }

    @When("the user says that a student {} belongs to student group {}")
    public void userInput(String name, String group) {
        softwareTestingPage.userInput(name, group);
    }

    @Then("the user should be in a student group")
    public void theUserShouldBeInAStudentGroup() {
        Assert.assertTrue("The student is not in any group", !softwareTestingPage.inAnyGroup());
    }

    @And("the user should receive a warning when he does not belong to that student group number.")
    public void theUserIsInThatStudentGroup() {
        softwareTestingPage.verifyStudentGroup();
    }

    @Then("the user should see his mandatory presence as presenter")
    public void PresenceAsPresenter() {
        softwareTestingPage.presencePresenter();
    }

    @And("the user should see his mandatory presence as opponent")
    public void PresenceAsOpponent() {
        softwareTestingPage.presenceOpponent();
    }
}
