package pageObjects

import managers.FileReaderManager
import managers.PageObjectManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

import static org.junit.jupiter.api.Assertions.*

class CoursesPage {
    private WebDriver driver

    CoursesPage(WebDriver driver) {
        this.driver = driver
        PageFactory.initElements(this.driver, this)
        assertNotNull(driver, "The webdriver in CoursesPage constructor is null")
    }

    void navigateToCoursesPage() {
        def configReader = FileReaderManager.getInstance().getConfigReader()
        assertEquals("https://ansymore.uantwerpen.be/courses", configReader.getApplicationCoursesUrl())
        driver.get(configReader.getApplicationCoursesUrl())
    }

    @FindAll([@FindBy(css = "div.views-field")])
    private List<WebElement> courseElements

    List<WebElement> getAllCourses() {
        List<WebElement> courseNameElements = []
        for (WebElement courseElement : courseElements) {
            if (!courseElement.getText().contains("Professor:")) {
                courseNameElements.add(courseElement)
            }
        }
        return courseNameElements
    }

    boolean verifyAllCoursesListed() {
        List<WebElement> courseNameElements = getAllCourses()
        return !courseNameElements.isEmpty()
    }

    void verifyCourseDetailsForAllCourses(PageObjectManager pageObjectManager) {
        List<WebElement> courseElements = getAllCourses()
        List<String> courseTitlesNotLoaded = []
        List<String> courseTitlesNoProfessor = []
        int numberOfCourses = courseElements.size()
        for (int index = 0; index < numberOfCourses; index++) {
            List<WebElement> courseElementsThisDom = getAllCourses()
            WebElement courseElement = courseElementsThisDom.get(index)
            courseElement.click()
            def courseDetailsPage = pageObjectManager.getNewCourseDetailsPage()
            if (!courseDetailsPage.isCoursePageLoaded()) {
                courseTitlesNotLoaded.add(courseDetailsPage.getCourseTitle())
            }
            List<String> professorNames = courseDetailsPage.getProfessorName()
            if (professorNames.isEmpty()) {
                courseTitlesNoProfessor.add(courseDetailsPage.getCourseTitle())
            }
            navigateToCoursesPage()
        }
        def assertionMessageNotLoaded = formatListToText(courseTitlesNotLoaded, "not loaded")
        assertTrue(courseTitlesNotLoaded.isEmpty(), assertionMessageNotLoaded)
        def assertionMessageNoProfessor = formatListToText(courseTitlesNoProfessor, "no professor")
        assertTrue(courseTitlesNoProfessor.isEmpty(), assertionMessageNoProfessor)
    }

    String formatListToText(List<String> stringList, String typeAssertion) {
        String assertionText
        assertTrue(typeAssertion == "not loaded" || typeAssertion == "no professor",
                "Formatting assertion texts for this type of assertion is not yet implemented.")
        if (stringList.isEmpty()) {
            assertionText = null
        } else if (stringList.size() == 1) {
            if (typeAssertion == "not loaded") {
                assertionText = "The page of the course " + stringList.get(0) + " has not loaded successfully."
            } else {
                assertionText = "The course " + stringList.get(0) + " has no professor."
            }
        } else {
            String lastTitle = stringList.remove(stringList.size() - 1)
            String otherTitlesJoined = stringList.join(", ")
            String allTitlesJoined = otherTitlesJoined + " and " + lastTitle
            if (typeAssertion == "not loaded") {
                assertionText = "The pages of the courses " + allTitlesJoined + " have not loaded successfully."
            } else {
                assertionText = "The courses " + allTitlesJoined + " have no professor."
            }
        }
        return assertionText
    }
}
