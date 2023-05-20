package pageObjects;

import cucumber.TestContext;
import dataProviders.ConfigFileReader;
import managers.FileReaderManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

// page_url = https://ansymore.uantwerpen.be/courses
public class CoursesPage {

    private WebDriver driver;

    public CoursesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindAll(@FindBy(css = "div.views-field"))
    private List<WebElement> courseElements;

    public void navigateToCoursesPage() {
        // instead of making instance of configfile reader everytime -> use file reader manager with singleton pattern
        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationCoursesUrl());
    }

    public List<WebElement> getAllCourses() {
        // following gets the names of courses but also professors underneath it.
        // Make new list with only web-elements that are courses themselves
        List<WebElement> courseNameElements = new ArrayList<>();
        for (WebElement courseElement : courseElements) {
            if (!courseElement.getText().contains("Professor:")) {
                courseNameElements.add(courseElement); // get only the courses
            }
        }
        return courseNameElements;
    }

    public boolean areCoursesListed() {
        List<WebElement> courseNameElements = getAllCourses();
        return !courseNameElements.isEmpty();
    }

    public void selectEachCourseAndVerifyDetails(TestContext testContext) {
        // first, calculate number of courses if we are on the Courses Page the first time.
        List<WebElement> courseElements = getAllCourses();
        int index = 0;
        int numberOfCourses = courseElements.size();
        for (index = 0; index < numberOfCourses; index++) {
            // get web-element of this course with correct ID
            List<WebElement> courseElementsThisDom = getAllCourses();
            WebElement courseElement = courseElementsThisDom.get(index);

            // go to specific course page
            courseElement.click();
            CourseDetailsPage courseDetailsPage = testContext.getPageObjectManager().getNewCourseDetailsPage();
            System.out.println("For the course " + courseDetailsPage.getCourseTitle() + ":");

            // Verify course page load for each course
            Assert.assertTrue("Course Page is not loaded successfully", courseDetailsPage.isCoursePageLoaded());

            //Verify professor information for each course
            List<String> professorNames = courseDetailsPage.getProfessorName();
            System.out.println("The professor(s), if any, is :" + professorNames);
            Assert.assertFalse("Course does not have a professor", professorNames.isEmpty());

            System.out.println("Course passed all assertions");

            // Back to courses page
            // if we navigate back to other page, web-element shall not be found anymore.
            // This is because driver creates reference ID for the element and its place to find in the dom
            // shall not find the element in the dom of the new courses page since it does not have identical id
            navigateToCoursesPage();
        }
    }

}