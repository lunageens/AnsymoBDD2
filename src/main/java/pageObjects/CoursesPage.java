package pageObjects;

import managers.FileReaderManager;
import managers.PageObjectManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Applying the page pattern, all the methods related to the page where all the courses are listed
 * are specified are implemented in this class.
 * This is a Selenium Page Object.
 */
// page_url = https://ansymore.uantwerpen.be/courses
public class CoursesPage {

    /**
     * WebDriver used to create page and execute methods.
     */
    private WebDriver driver;

    /**
     * Constructor of CoursesPage
     *
     * @param driver WebDriver used to create page and execute methods.
     */
    public CoursesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        Assert.assertNotNull("The webdriver in CoursesPage constructor is null", driver);
    }

    /**
     * Driver goes to CoursesPage.
     */
    public void navigateToCoursesPage() {
        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationCoursesUrl());  // instead of making instance of configfile reader everytime -> use file reader manager with singleton pattern
    }

    /**
     * List of WebElements where each item is a text on the course page (either a professor or the link to the course itself), found by css operator.
     */
    @FindAll(@FindBy(css = "div.views-field"))
    private List<WebElement> courseElements;

    /**
     * Get all courses listed on the courses page.
     *
     * @return List<WebElement> List with as each item the WebElement that is a link to each course.
     */
    public List<WebElement> getAllCourses() {
        List<WebElement> courseNameElements = new ArrayList<>();
        for (WebElement courseElement : courseElements) {  // gets the names of courses but also professors underneath it.
            if (!courseElement.getText().contains("Professor:")) {
                courseNameElements.add(courseElement); // Make new list with only web-elements that are courses themselves
            }
        }
        return courseNameElements;
    }

    /**
     * Checks if there are any courses listed at all on the CoursesPage
     *
     * @return boolean True if there are 1 or more courses listed on the CoursesPage.
     */
    public boolean areCoursesListed() {
        List<WebElement> courseNameElements = getAllCourses();
        return !courseNameElements.isEmpty();
    }

    /**
     * Checks for each course on the coursesPage if the details page of that course loads. Gives assertion if that's not the case.
     * Checks for each course on that details page if there is 1 or more professor specified. Gives assertion if that's not the case.
     * Prints the names of the professor for each course.
     *
     * @param pageObjectManager Instance of pageObjectManager that is currently in use.
     */
    public void selectEachCourseAndVerifyDetails(PageObjectManager pageObjectManager) {
        // First, calculate number of courses if we are on the Courses Page the first time.
        List<WebElement> courseElements = getAllCourses();
        int index;
        int numberOfCourses = courseElements.size();
        for (index = 0; index < numberOfCourses; index++) {
            // Get web-element of this course with correct ID
            List<WebElement> courseElementsThisDom = getAllCourses();
            WebElement courseElement = courseElementsThisDom.get(index);

            // Go to specific course page
            courseElement.click();
            CourseDetailsPage courseDetailsPage = pageObjectManager.getNewCourseDetailsPage();
            System.out.println("For the course " + courseDetailsPage.getCourseTitle() + ":");

            // Verify course page load for each course
            Assert.assertTrue("Course Page is not loaded successfully", courseDetailsPage.isCoursePageLoaded());

            //Verify professor information for each course
            List<String> professorNames = courseDetailsPage.getProfessorName();
            // TODO Fix als het lege lijst is, dat er niet zo een haakjes rond komen in output
            System.out.println("The professor(s), if any, is :" + professorNames);
            String assertionText = "Course " + courseDetailsPage.getCourseTitle() + " does not have a professor.";
            Assert.assertFalse(assertionText, professorNames.isEmpty());

            // Back to courses page
            // if we navigate 'back' to other page, web-element shall not be found anymore.
            // This is because driver creates reference ID for the element and its place to find in the dom.
            // Shall not find the element in the dom of the new courses page since it does not have identical ID.
            navigateToCoursesPage();
        }
    }
}