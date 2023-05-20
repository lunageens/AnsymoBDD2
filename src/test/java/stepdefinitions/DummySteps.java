package stepdefinitions;

import cucumber.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;

import pageObjects.HomePage;

public class DummySteps {

    private HomePage homePage;
    private TestContext testContext;

    public DummySteps(TestContext context){
        testContext = context;
        homePage = testContext.getPageObjectManager().getHomePage();
    }

    @Given("a dummy scenario") // dummy scenario for exercise 5 in BrowseCourses.feature
    public void a_dummy_scenario() {
        // no actual implementation
        // lets just try to navigate to homepage for the sake of debugging TestContext class
        homePage.navigateToHomePage();
    }

}
