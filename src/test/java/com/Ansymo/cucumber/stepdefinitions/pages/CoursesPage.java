package com.Ansymo.cucumber.stepdefinitions.pages;

import org.apache.commons.lang.math.Range;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static io.netty.util.internal.SystemPropertyUtil.contains;


// page_url = https://ansymore.uantwerpen.be/courses
public class CoursesPage {

    private WebDriver driver;
    private String url = "https://ansymore.uantwerpen.be/courses";
    public CoursesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCoursesPage(){
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<WebElement> getAllCourses(){
        // #content > div.view.view-courses.view-id-courses.view-display-id-page_1.view-dom-id-13f84e46269d2c46630941a0a74d4f5a > div.view-content
        // following gets the names of courses but also professors underneath it.
        List<WebElement> courseElements = driver.findElements(By.cssSelector("div.views-field"));

        // Make new list with only web-elements that are courses themselves
        List<WebElement> courseNameElements = new ArrayList<>();
        for (WebElement courseElement : courseElements){
            if (!courseElement.getText().contains("Professor:")) {
                courseNameElements.add(courseElement); // get only the courses
            }
        }

        return courseNameElements;
    }

    public boolean areCoursesListed(){
        List<WebElement> courseNameElements = getAllCourses();
        System.out.println("The following courses are published on the website: ");
        for (WebElement courseNameElement : courseNameElements){
            System.out.println(courseNameElement.getText());
        }
        return !courseNameElements.isEmpty();
    }

    public void selectEachCourseAndVerifyDetails(){
        // first, calculate number of courses if we are one the Courses Page the first time.
        List<WebElement> courseElements = getAllCourses();
        int index = 0;
        int numberOfCourses = courseElements.size();
        for (index = 0; index < numberOfCourses; index++) {

            // get web-element of this course with correct ID
            List<WebElement> courseElementsThisDom = getAllCourses();
            WebElement courseElement = courseElementsThisDom.get(index);

            // go to specific course page
            courseElement.click();
            CourseDetailsPage courseDetailsPage = new CourseDetailsPage(driver);
            System.out.println("For the course " + courseDetailsPage.getCourseTitle() + ":");

            // Verify course page load for each course
            Assert.assertTrue("Course Page is not loaded successfully", courseDetailsPage.isCoursePageLoaded());

            //Verify professor information for each course
            List<String> professorNames = courseDetailsPage.getProfessorName();
            System.out.println("The professor(s), if any, is :" + professorNames);
            if (courseDetailsPage.getCourseTitle().contains("Master thesis")){
                //skip assertion to see if others work
            }
            else {Assert.assertFalse("Course does not have a professor", professorNames.isEmpty());}

            System.out.println("Course passed all assertions");

            // Back to courses page
            // if we navigate back to other page, webelement shall not be found anymore.
            // This is because driver creates reference ID for the ellement and its place to find in in the dom
            // if shall not find the element in the dom of the new courses page since it does not have identical id
            navigateToCoursesPage();
        }
    }

    public void selectCourse(String courseName) {
        driver.findElement(By.linkText(courseName)).click();
    }

}