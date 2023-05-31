package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.PageObjectManager;
import managers.WebDriverManager;
import org.junit.Assert;
import pageObjects.CoursesPage;
import pageObjects.HomePage;

// TODO Do i still need to use Groovy as a scripting language, since these are just plain java files. Or is my assignment okay already?
// Dit kan dus ook in Groovy ipv java
// To enable to creat step definitions in Groovy, the Cucumber for groovy plugin must be installed and enabled. Also extra
// plug-in in pom.xml file

/**
 * Cucumber step file for specifying methods that are needed for steps in .feature files of exercise 5
 */
public class BrowseCoursesSteps extends BaseClass {

    /**
     * WebDriverManager used for this scenario.
     */
    private WebDriverManager webDriverManager;

    /**
     * PageObjectManager used for this scenario.
     */
    private PageObjectManager pageObjectManager;

    /**
     * Selenium page instance of the homePage, following the pages pattern.
     */
    private HomePage homePage;

    /**
     * Selenium page instance of the coursesPage, following the pages pattern.
     */
    private CoursesPage coursesPage;

    /**
     * Constructor of the BrowseCoursesSteps
     * @param base Instance of Baseclass where webDriverManager and pageObjectManager are that must be used in this class.
     */
    public BrowseCoursesSteps(BaseClass base){
        webDriverManager = base.webDriverManager;
        Assert.assertNotNull("Webdrivermanager created is empty", webDriverManager);
        pageObjectManager = base.pageObjectManager;
        Assert.assertNotNull("PageObjectManager created is emtpy", pageObjectManager);
        Assert.assertNotNull("Driver is empty", webDriverManager.getDriver());
    }

    /**
     * Scenario: 'Verify all courses from homepages (names and professors)'
     * Step: 'Given the user is on the Ansymore homepage'
     */
    @Given("the user is on the Ansymo homepage")
    public void navigateToHomepage() {
        homePage = pageObjectManager.getHomePage();
        homePage.navigateToHomePage();
    }

    /**
     * Scenario: 'Verify all courses from homepages (names and professors)'
     * Step: 'When the user clicks the {string} link in the menu section'.
     * Only implemented for String = 'Courses'.
     * @param linkText The text in homepage menu that one wants to press.
     */
    @When("the user clicks the {string} link in the menu section")
    public void clickMenuLink(String linkText) {
        homePage.clickMenuLink(linkText);
    }

    /**
     * Scenario: 'Verify all courses from homepages (names and professors)'.
     * Step: 'Then the user should see a page with all the courses listed'.
     * Gives assertion if there are no courses listed on the over-all Courses page.
     */
    @Then("the user should see a page with all the courses listed")
    public void verifyAllCoursesListed() {
        coursesPage = pageObjectManager.getCoursesPage(); // instead of creating new courses page every single time
        Assert.assertTrue("There are no courses.", coursesPage.verifyAllCoursesListed());
    }

    /**
     * Scenario: 'Verify all courses from homepages (names and professors)'
     * Step: 'Then for each course, there should be a page that is loaded and there should be a professor'
     * Clicks link of all the courses on the Course pages. Give assertion if for one of these courses, the details page doesn't load.
     * Checks on that detailed page if there are any professors specified. Gives assertion if for one of these courses, there is no professor specified.
     */
    @Then("for each course, there should be a page that is loaded and there should be a professor")
    public void verifyCourseDetailsForAllCourses() {
        coursesPage.verifyCourseDetailsForAllCourses(pageObjectManager);
    }

}
