package managers;

import org.openqa.selenium.WebDriver;
import pageObjects.CoursesPage;
import pageObjects.HomePage;

/**
 * Makes new page instance or returns the one already made.
 * <p>
 * So far we have just one single Cucumber Step Definition file. But in the case of multiple-step definition files,
 * we will be creating an object of Pages again and again. Which is against the coding principle.
 * <p>
 * To avoid this situation, we can create a Page Object Manager.
 * The duty of the Page Object Manager is to create the page's object and also to make sure that the same object
 * should not be created again and again. But to use a single object for all the step definition files.
 */
public class PageObjectManager {

    private WebDriver driver;
    private CoursesPage coursesPage;
    private HomePage homePage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage getHomePage() {
        return (homePage == null) ? homePage = new HomePage(driver) : homePage;
    }

    public CoursesPage getCoursesPage() {
        return (coursesPage == null) ? coursesPage = new CoursesPage(driver) : coursesPage;
    }

    // not implemented for CourseDetailsPage because it is needed that we make an instance every time
}
