package stepdefinitions;

import dataProviders.ConfigFileReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.PageObjectManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pageObjects.CoursesPage;
import pageObjects.HomePage;
import java.util.concurrent.TimeUnit;

// TODO -> do i still need to use Groovy as a scripting language, since these are just plain java files. Or is my assignment okay already?
// dit kan dus ook in Groovy ipv java
// to enable to creat step definitions in Groovy, the Cucumber for groovy plugin must be installed and enabled. Also extra
// plug-in in pom.xml file
public class BrowseCoursesSteps {

    private WebDriver driver;
    private HomePage homePage;
    private CoursesPage coursesPage;
    private PageObjectManager pageObjectManager;
    private ConfigFileReader configFileReader;

    @Before
    public void setUp() {

        // driver
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true); // Run firefox in headless mode (without GUI)
        driver = new FirefoxDriver(options);

        // for creating pages with this driver
        pageObjectManager = new PageObjectManager(driver);

        // for getting properties
        configFileReader = new ConfigFileReader();

        // config
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);

    }

    @Given("the user is on the Ansymo homepage")
    public void navigateToHomepage() {
        homePage = pageObjectManager.getHomePage(); // instead of creating new homePage every single time
        homePage.navigateToHomePage();
    }

    @When("the user clicks the {string} link in the menu section")
    public void clickCoursesLink(String linkText) {
        homePage.clickMenuItem(linkText);
    }

    @Then("the user should see a page with all the courses listed")
    public void verifyCoursesPage() {
        coursesPage = pageObjectManager.getCoursesPage(); // instead of creating new coursespage every single time
        Assert.assertTrue("There are no courses", coursesPage.areCoursesListed());
    }

    @Then("for each course, there should be a page that is loaded and there should be a professor")
    public void verifyCourseAndProfessorNames() {
        coursesPage.selectEachCourseAndVerifyDetails();
    }

    @Given("a dummy scenario") // dummy scenario for exercise 5 in BrowseCourses.feature
    public void a_dummy_scenario() {
        // no actual implementation
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
