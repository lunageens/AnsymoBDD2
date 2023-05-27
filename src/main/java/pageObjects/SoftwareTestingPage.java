package pageObjects;

import managers.FileReaderManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Applying the page pattern, all the methods related to the page where the details of the software testing course
 * are specified are implemented in this class.
 * This is a Selenium Page Object.
 */
// page_url = https://ansymore.uantwerpen.be/courses/software-testing
public class SoftwareTestingPage extends CourseDetailsPage {

    /**
     * WebDriver used to create page and execute methods.
     */
    WebDriver driver;

    /**
     * Constructor of SoftwareTestingPage
     *
     * @param driver WebDriver used to create page and execute methods.
     */
    public SoftwareTestingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        Assert.assertNotNull("The webdriver in SoftwareTestingPage constructor is null", driver);
    }

    /**
     * Driver goes to Software Testing detailed course page.
     */
    public void navigateToSoftWareTesting() {
        driver.get(FileReaderManager.getInstance().getConfigReader().getSoftwareTestingUrl()); // instead of making instance of configfile reader everytime -> use file reader manager with singleton pattern
    }

    /**
     * List of WebElements of assignments and resources links.
     */
    @FindAll(@FindBy(css = "html > body > div > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > div > div:nth-of-type(3) > ul:nth-of-type(6)"))
    List<WebElement> AssignmentsElements;

    /**
     * Get all assignment links that are specified for the course.
     *
     * @return List A list with each WebElement the link to particular assignment on the Software Testing course page.
     */
    public List<WebElement> getAssignmentsLinkElements() {
        // TODO Check if this is the right list & alter description of the method accordingly.
        List<WebElement> assignmentsLinkElements = new ArrayList<>();
        for (WebElement assignmentElement : AssignmentsElements) {
            if (assignmentElement.getText().contains("Assignment")) {
                assignmentsLinkElements.add(assignmentElement); // get only the links
            }
        }
        return assignmentsLinkElements;
    }

    /**
     * Checks if there are any assignments listed at all on the Software Testing page
     *
     * @return boolean True if there are 1 or more assignments listed on the Software Testing page.
     */
    public boolean verifyAssignmentsLinkPresent() {
        return !getAssignmentsLinkElements().isEmpty();
    }

    public void verifyAssignmentsLinkFormat() {
        List<WebElement> Links = getAssignmentsLinkElements();
        // TODO actual implementation of this method
    }

    public void verifyAssignmentsLinkExistence() {
        List<WebElement> Links = getAssignmentsLinkElements();
        // TODO actual implementation of this method
    }

    public void userInput(String name, String group) {
        // TODO actual implementation of this method
    }

    public boolean inAnyGroup() {
        // TODO actual implementation of this method
        return true;
    }

    public void verifyStudentGroup() {
        // TODO actual implementation of this method
    }

    public void presencePresenter() {
        // TODO actual implementation of this method
    }

    public void presenceOpponent() {
        // TODO actual implementation of this method
    }
}