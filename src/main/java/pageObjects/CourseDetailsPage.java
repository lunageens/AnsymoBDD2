package pageObjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailsPage {

    private WebDriver driver;

    public CourseDetailsPage(WebDriver driver) {
        System.out.println("A new coursedetails page is created");
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "h1")
    WebElement titleElement;

    public String getCourseTitle() {
        System.out.println(" method getcourse title of coursedetailspage is called");
        return titleElement.getText();
    }

    @FindAll(@FindBy(css = "#content > div.buildmode-full > div > div.nd-region-middle-wrapper.nd-no-sidebars > div > div.field.field-professor-linked > a"))
    List<WebElement> professorElements;

    public List<String> getProfessorName() {
        System.out.println(" method get professor name of coursedetailspage is called");
        List<String> professorNames = new ArrayList<>();
        for (WebElement professorElement : professorElements) {
            professorNames.add(professorElement.getText());
        }
        return professorNames;
    }

    @FindBy(css = "body")
    WebElement body;

    public boolean isCoursePageLoaded() {
        System.out.println("method is Course Page loaded is called");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Maximum wait time of 10 seconds

        // Define the expected condition for the page to be loaded
        ExpectedCondition<Boolean> isPageLoadedCondition = driver -> {
            // Implement the logic to check if the page is loaded successfully
            // For example, wait for a specific element to be present or visible on the page
            try {
                // Check if a specific element is present on the page
                return body.isDisplayed();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                // Element not found or stale, return false
                return false;
            }
        };
        // Wait until the page is loaded or the timeout is reached
        return wait.until(isPageLoadedCondition);
    }

}