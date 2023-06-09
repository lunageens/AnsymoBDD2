package pageObjects

import static org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


/**
 * Applying the page pattern, all the methods related to the page where the details of a particular course
 * are specified are implemented in this class.
 * This is a Selenium Page Object.
 */
class CourseDetailsPage {

    /**
     * WebDriver used to create page and execute methods.
     */
    private WebDriver driver

    /**
     * Constructor of CourseDetailsPage
     *
     * @param driver WebDriver used to create page and execute methods.
     */
    CourseDetailsPage(WebDriver driver) {
        this.driver = driver
        PageFactory.initElements(this.driver, this)
        assertNotNull(driver, "The webdriver in CourseDetailsPage constructor is null.")
    }

    /**
     * WebElement that is the title of the course, found by css operator.
     */
    @FindBy(css = "h1")
    WebElement titleElement

    /**
     * Get the title of the course that is specified on this page.
     *
     * @return String Title of the course.
     */
    String getCourseTitle() {
        return titleElement.getText()
    }

    /**
     * List of WebElements that are the names of the professors, found by css operator.
     */
    @FindAll([@FindBy(css = "#content > div.buildmode-full > div > div.nd-region-middle-wrapper.nd-no-sidebars > div > div.field.field-professor-linked > a")])
     List<WebElement> professorElements

    /**
     * Get the names of all professors teaching this course.
     *
     * @return List where each name of professor as string is a different item.
     */
    List<String> getProfessorName() {
        List<String> professorNames = new ArrayList<>()
        for (WebElement professorElement : professorElements) {
            professorNames.add(professorElement.text)
        }
        return professorNames
    }

    /**
     * Body WebElement, found by css operator.
     */
    @FindBy(css = "body")
     WebElement body

    /**
     * Checks if this course detail page is loaded after 10 seconds by checking if the body is displayed.
     *
     * @return boolean True is course detail page is loaded
     */
    boolean isCoursePageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)) // Maximum wait time of 10 seconds

        ExpectedCondition<Boolean> isPageLoadedCondition = { WebDriver driver ->
            // Define the expected condition for the page to be loaded
            // Implement the logic to check if the page is loaded successfully. For example, wait for a specific element to be present or visible on the page
            try {
                body.isDisplayed() // Check if a specific element is present on the page
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                false // Element not found or stale, return false
            }
        }
        wait.until(isPageLoadedCondition) // Wait until the page is loaded or the timeout is reached
    }
}
