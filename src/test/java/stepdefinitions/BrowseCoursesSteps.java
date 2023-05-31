package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.PageObjectManager;
import managers.WebDriverManager;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.CoursesPage;
import pageObjects.HomePage;
import pageObjects.SoftwareTestingPage;

import java.util.List;

// TODO Do i still need to use Groovy as a scripting language, since these are just plain java files. Or is my assignment okay already?
// Dit kan dus ook in Groovy ipv java
// To enable to creat step definitions in Groovy, the Cucumber for groovy plugin must be installed and enabled. Also extra
// plug-in in pom.xml file

/**
 * Cucumber step file for specifying methods that are needed for steps in .feature files
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
     * Selenium page instance of the Software Testing page, following the pages pattern.
     */
    private SoftwareTestingPage softwareTestingPage;

    /**
     * Logger used for this class. Enables the warnings we need to implement for some tests.
     */
    private static final Logger logger = LoggerFactory.getLogger(BrowseCoursesSteps.class);

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

    /**
     * Scenario: 'Verify Assignment Links in Software Testing Course'
     * Scenario: 'Verify Student Groups in Software Testing Course'
     * Scenario: 'Verify Mandatory Presence of a student in Software Testing Course'
     * Step: 'Given the user is on the Software Testing course page.'
     */
    @Given("the user is on the Software Testing course page")
    public void navigateToSoftwareTestingPage(){
        softwareTestingPage = pageObjectManager.getSoftwareTestingPage();
        softwareTestingPage.navigateToSoftWareTestingPage();
    }

    /**
     * Scenario: 'Verify Assignment Links in Software Testing Course'
     * Step: 'Then the user sees the links for each assignment'.
     * Checks if there are any assignments links listed at all.
     * Gives assertion if there are no assignments listed.
     */
    @When("the user sees the links for each assignment")
    public void verifyAssignmentLinksPresent(){
        Assert.assertTrue("There are no assignment links.", softwareTestingPage.verifyAssignmentsLinkPresent());
    }

    /**
     * Scenario: 'Verify Assignment Links in Software Testing Course'
     * Step: 'Then the user receive a warning when an assignment link doesn't exist.'.
     * For each assignment listed, verify that the document (link) exists on the server.
     * Gives warning when it does not exist.
     */
    @Then("the user should receive a warning when an assignment link doesn't exist")
    public void verifyAssignmentsLinkExistenceWarning(){
        List<Integer> nonExistentLinks = softwareTestingPage.verifyAssignmentsLinkExistence();
        if (!nonExistentLinks.isEmpty()) { // only give warning when there is a link that does not exist
            String warningText = softwareTestingPage.formatLinkExistenceWarning(nonExistentLinks);
            logger.warn(warningText);
        }
    }

    /**
     * Scenario:'Verify Assignment Links in Software Testing Course'
     * Step: 'Then the user should verify the link format of each assignment link'.
     * For each assignment listed, checks that the link is of the format  '/system/files/uploads/courses/Testing/assignment[NR].pdf'
     * Gives assertion if the layout differs for one of the assignments.
     */
    @Then("the user should verify the link format of each assignment link")
    public void verifyAssignmentsLinkFormat(){
        List<Integer> nonFormattedLinks = softwareTestingPage.verifyAssignmentsLinkFormat();
        Assert.assertNotNull(softwareTestingPage.formatLinkFormatAssertion(nonFormattedLinks), nonFormattedLinks);
    }

    /**
     * Scenario: 'Verify Student Groups in Software Testing Course'
     * Step 'When the user says that a student {} belongs to student group {}'.
     * @param name Full Name with capital letters like one should from the student.
     * @param group Number of One (of the) student group(s) the student believes he is in.
     */
    @When("the user says that a student {} belongs to student group {}")
    public void provideStudentGroup(String name, String group) {
        softwareTestingPage.provideStudentGroup(name, group);
    }

    /**
     * Scenario: 'Verify Student Groups in Software Testing Course'
     * Step: 'Then the user should be in a student group'.
     * Gives an assertion when the student cannot be found in any group.
     */
    @Then("the user should be in a student group")
    public void verifyStudentGroupMembership() {
        Assert.assertTrue("The student is not in any group.", softwareTestingPage.inAnyGroup());
    }

    /**
     * Scenario: 'Verify Student Groups in Software Testing Course'
     * Step: 'And the user should receive a warning when he does not belong to that student group number.'
     * Gives warning when the student is not in that group.
     */
    @And("the user should receive a warning when he does not belong to that student group number")
    public void verifyStudentGroupWarning() {
        boolean isInThatStudentGroup = softwareTestingPage.verifyStudentGroupWarning();
        if (!isInThatStudentGroup) {
            logger.warn("Student is not in a group with that student number.");}
    }

    /**
     * Scenario: 'Verify Mandatory Presence of a student in Software Testing Course'
     * Step: 'When the user says student {} '.
     * @param name Full Name with capital letters like one should from the student.
     */
    @When("the user says a student {}")
    public void provideStudent(String name) {
        softwareTestingPage.provideStudentGroup(name, "0");
    }

    /**
     * Scenario: 'Verify Mandatory Presence of a student in Software Testing Course'
     * Step: 'Then the user should see his mandatory presence as presenter'.
     * Gives assertion if the student is not in any group.
     * Prints the date that the student needs to present a lecture.
     */
    @Then("the user should see his mandatory presence as presenter")
    public void presencePresenter() {
        Assert.assertTrue("The student is not in any group", softwareTestingPage.inAnyGroup());
        logger.info(softwareTestingPage.presencePresenter());
    }

    /**
     * Scenario: 'Verify Mandatory Presence of a student in Software Testing Course'
     * Step: 'Then the user should see his mandatory presence as opponent'.
     * Gives assertion if the student is not in any group.
     * Prints the date that the student is an opponent.
     */
    @And("the user should see his mandatory presence as opponent")
    public void presenceOpponent() {
        Assert.assertTrue("The student is not in any group", softwareTestingPage.inAnyGroup());
        logger.info(softwareTestingPage.presenceOpponent());
    }
}
