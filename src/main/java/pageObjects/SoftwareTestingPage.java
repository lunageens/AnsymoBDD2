package pageObjects;

import managers.FileReaderManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
    @FindAll(@FindBy(css = "html > body > div > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > div > div:nth-of-type(3) > ul:nth-of-type(6) > li > a"))
    List<WebElement> AssignmentsElements;

    /**
     * Get all assignment links that are specified for the course. Do not include resource links.
     *
     * @return List A list with each WebElement the link to particular assignment on the Software Testing course page.
     */
    public List<WebElement> getAssignmentsLinkElements() {
        List<WebElement> assignmentsLinkElements = new ArrayList<>();
        for (WebElement assignmentElement : AssignmentsElements) {
            if (assignmentElement.getText().contains("Assignment")) {
                assignmentsLinkElements.add(assignmentElement); // get only the links, not resource links
            }
        }
        return assignmentsLinkElements;
    }

    /**
     * Calculates the number of assignment links.
     * @return int The number of assignment links on the Software Testing course.
     */
    public int getNumberOfAssignmentLinks(){
        return getAssignmentsLinkElements().size(); //size starts at 1
    }

    /**
     * Checks if there are any assignments listed at all on the Software Testing page
     *
     * @return boolean True if there are 1 or more assignments listed on the Software Testing page.
     */
    public boolean verifyAssignmentsLinkPresent() {
        return !getAssignmentsLinkElements().isEmpty();
    }

    /**
     * Check for each assignment link that the link exists.
     * Give warning when link does not exist in Step
     * @return List where each item is a integer, that is the number of the assignments with non-existent links
     */
    public List<Integer> verifyAssignmentsLinkExistence() {
        List<WebElement> Links = getAssignmentsLinkElements();
        int numberOfLinks = getNumberOfAssignmentLinks();
        List<Integer> nonExistentLinks = new ArrayList<>();
        for (int i = 0; i < numberOfLinks; i++) {
            WebElement link = Links.get(i);
            String urlText = link.getAttribute("href"); // retrieve url associated with link
            if (!doesLinkExists(urlText)){ nonExistentLinks.add(i+1); }
        }
        return nonExistentLinks;
    }

    /**
     * The linkExists() method takes a URL as a parameter and performs an HTTP HEAD request to check
     * the response code. The HTTP HEAD request is a type of HTTP request method that is used to
     * retrieve only the header information for a given resource, without fetching the actual content
     * of the resource. It is useful when you are interested in obtaining metadata or checking the
     * status of a resource without the need to download its entire contents.
     * <p>
     * If the response code is HTTP_OK (which is the constant for 200), it means that the link exists
     * on the server. The method will return true. Otherwise, it returns false.
     * </p>
     * @param urlText URL of the link in String format.
     * @return boolean True if the link exists on the server.
     */
    public boolean doesLinkExists(String urlText){
        try {
            URL linkURL = new URL(urlText); // make new URL object
            HttpURLConnection connection = (HttpURLConnection) linkURL.openConnection(); // make new connection
            connection.setRequestMethod("GET"); // try connecting
            int responseCode = connection.getResponseCode(); // get response code
            return responseCode != HttpURLConnection.HTTP_NOT_FOUND; // if no 200, return false
        } catch (IOException e){ // if not even response code and error, also false
            return false;
        }
    }

    /**
     * Precondition: there is at least one non-existent link.
     * Formats the warning text of the non-existent links
     * @param nonExistentLinks A list of Integers tht are the number of the assignments with non-existent links
     * @return String WarningText with formatted numbers (comma's, and, ...)
     */
    public String formatLinkExistenceWarning(List<Integer> nonExistentLinks){
        Assert.assertNotNull("There should be at least one non-existent link to format a warning message", nonExistentLinks);
        String warningText;
        if (nonExistentLinks.size() == 1){
            warningText = "Assignment " + nonExistentLinks.get(0).toString() + " has a link that does not exists on the server.";
        } else {
            warningText = "Assignments " + formatNumListToText(nonExistentLinks) + " have a link that does not exists on the server.";
        }
        return warningText;
    }

    /**
     * Precondition: at least two numbers to format
     * Formats a list of integers to a string of the format 1, 2 and 3
     * @param numList List of integers
     * @return String Formatted integers in text form
     */
    public String formatNumListToText(List<Integer> numList) {
        Assert.assertTrue("There should be at least two numbers to format", numList.size() > 1);
        // from integer list to string list
        List<String> formattedList = new ArrayList<>();
        for (Object num : numList) {
            formattedList.add(num.toString());
        }
        // last name with and, others with comma: e.g., Assignments 1, 2, and 3 ...
        String lastNum = formattedList.remove(formattedList.size() - 1);
        String otherNumJoined = String.join(", ", formattedList);
        return otherNumJoined + " and " + lastNum;
    }

    /**
     * Implementation for the step 'Then the user should verify the link format of each assignment link'.
     * For each assignment listed, checks that the link is of the format  '/system/files/uploads/courses/Testing/assignment[NR].pdf'
     * Gives assertion if the layout differs for one of the assignments.
     */
    public List<Integer> verifyAssignmentsLinkFormat() {
        List<WebElement> Links = getAssignmentsLinkElements();
        int numberOfLinks = getNumberOfAssignmentLinks();
        List<Integer> nonFormattedLinks = new ArrayList<>();
        for (int i = 0; i < numberOfLinks; i++) {
            WebElement link = Links.get(i);
            String urlText = link.getAttribute("href"); // retrieve url associated with link
            String correctText = "/system/files/uploads/courses/Testing/assignment" + i+1 + ".pdf";
            if (!urlText.equals(correctText)){ // not the right format
                nonFormattedLinks.add(i+1);
            }
        }
       return nonFormattedLinks;
    }

    /**
     * Formats the assertion for the links that are not formatted correctly
     * @param nonFormattedLinks A list of Integers tht are the number of the assignments with non-existent links
     * @return String AssertionText with formatted numbers
     */
    public String formatLinkFormatAssertion(List<Integer> nonFormattedLinks) {
        String assertionText;
        if (nonFormattedLinks.isEmpty()) {
            assertionText = null;
        } else if (nonFormattedLinks.size() == 1) {
            assertionText = "Assignment " + nonFormattedLinks.get(0).toString() + " has a link with an incorrect format.";
        } else {
            assertionText = "Assignments " + formatNumListToText(nonFormattedLinks) + " have a link with an incorrect format.";
        }
        return assertionText;
    }

    public void userInput(String name, String group) {
        // TODO actual implementation of this method
    }

    public boolean inAnyGroup() {
        // TODO actual implementation of this method
        return true;
    }

    public boolean verifyStudentGroup() {
        // TODO actual implementation of this method
        return false;
    }

    public void presencePresenter() {
        // TODO actual implementation of this method
    }

    public void presenceOpponent() {
        // TODO actual implementation of this method
    }
}