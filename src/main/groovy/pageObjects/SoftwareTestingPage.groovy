package pageObjects

import managers.FileReaderManager

import static org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy

import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * Applying the page pattern, all the methods related to the page where the details of the software testing course
 * are specified are implemented in this class.
 * This is a Selenium Page Object.
 */
// page_url = https://ansymore.uantwerpen.be/courses/software-testing
class SoftwareTestingPage extends CourseDetailsPage {

    /**
     * WebDriver used to create page and execute methods.
     */
    WebDriver driver

    /**
     * The full name with capitalization of the student that needs to be verified.
     */
    String inputStudentName

    /**
     * The student group number that needs to be verified.
     */
    Integer inputGroupNumber

    /**
     * Constructor of SoftwareTestingPage
     *
     * @param driver WebDriver used to create page and execute methods.
     */
    SoftwareTestingPage(WebDriver driver) {
        super(driver)
        this.driver = driver
        assertNotNull(driver, "The webdriver in SoftwareTestingPage constructor is null.")
    }

    /**
     * Driver goes to Software Testing detailed course page.
     */
    void navigateToSoftWareTestingPage() {
        driver.get(FileReaderManager.getInstance().getConfigReader().getSoftwareTestingUrl())
        // instead of making instance of configfile reader everytime -> use file reader manager with singleton pattern
    }

    /**
     * List of WebElements of assignments and resources links.
     */
    @FindAll([@FindBy(css = "html > body > div > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > div > div:nth-of-type(3) > ul:nth-of-type(6) > li > a")])
    List<WebElement> AssignmentsElements

    /**
     * Get all assignment links that are specified for the course. Do not include resource links.
     *
     * @return List A list with each WebElement the link to particular assignment on the Software Testing course page.
     */
    List<WebElement> getAssignmentsLinkElements() {
        List<WebElement> assignmentsLinkElements = new ArrayList<>()
        // Get only the links, not including the resource links
        for (WebElement assignmentElement : AssignmentsElements) {
            if (assignmentElement.getText().contains("Assignment")) {
                assignmentsLinkElements.add(assignmentElement)
            }
        }
        return assignmentsLinkElements
    }

    /**
     * Calculates the number of assignment links.
     * @return int The number of assignment links on the Software Testing course.
     */
    int getNumberOfAssignmentLinks() {
        return getAssignmentsLinkElements().size() //size starts at 1
    }

    /**
     * Checks if there are any assignments listed at all on the Software Testing page
     *
     * @return boolean True if there are 1 or more assignments listed on the Software Testing page.
     */
    boolean verifyAssignmentsLinkPresent() {
        return !getAssignmentsLinkElements().isEmpty()
    }

    /**
     * Check for each assignment link that the link exists.
     * Give warning when link does not exist in Step
     * @return List where each item is an integer, that is the number of the assignments with non-existent links
     */
    List<Integer> verifyAssignmentsLinkExistence() {
        List<WebElement> links = getAssignmentsLinkElements()
        int numberOfLinks = getNumberOfAssignmentLinks()

        // Loop over all links
        List<Integer> nonExistentLinks = []
        for (int i = 0; i < numberOfLinks; i++) {
            WebElement link = links[i] // Get WebElement
            String urlText = link.getAttribute("href") // Retrieve url associated with link
            if (!doesLinkExists(urlText)) {
                nonExistentLinks.add(i + 1) // If that URL doesn't exist, add to result var
            }
        }
        return nonExistentLinks
    }

    /**
     * The linkExists() method takes a URL as a parameter and performs an HTTP HEAD request to check
     * the response code. The HTTP HEAD request is a type of HTTP request method that is used to
     * retrieve only the header information for a given resource, without fetching the actual content
     * of the resource. It is useful when you are interested in obtaining metadata or checking the
     * status of a resource without the need to download its entire contents.
     * <p>
     * If the response code is HTTP_NOT_FOUND (which is the constant for 404), it means that the link does not exists
     * on the server. The method will return true. Otherwise, it returns false.
     * </p>
     * @param urlText URL of the link in String format.
     * @return boolean True if the link exists on the server.
     */
    boolean doesLinkExists(String urlText) {
        try {
            URL linkURL = new URL(urlText) // Make new URL object
            HttpURLConnection connection = (HttpURLConnection) linkURL.openConnection() // Make new connection
            connection.setRequestMethod("GET") // Try connecting
            int responseCode = connection.getResponseCode() // Get response code
            return responseCode != HttpURLConnection.HTTP_NOT_FOUND // If no 200, return false
        } catch (IOException e) { // If not even response code and error, also false
            return false
        }
    }

    /**
     * Precondition: there is at least one non-existent link.
     * Formats the warning text of the non-existent links
     * @param nonExistentLinks A list of Integers tht are the number of the assignments with non-existent links
     * @return String WarningText with formatted numbers (comma's, and, ...)
     */
    String formatLinkExistenceWarning(List<Integer> nonExistentLinks) {
        assertNotNull(nonExistentLinks, "There should be at least one non-existent link to format a warning message.")
        String warningText
        if (nonExistentLinks.size() == 1) { // singular
            warningText = "Assignment " + nonExistentLinks[0].toString() + " has a link that does not exist on the server."
        } else { // plural. Use formatNumListToText for numbers
            warningText = "Assignments " + formatNumListToText(nonExistentLinks) + " have a link that does not exist on the server."
        }
        return warningText
    }

    /**
     * Formats a list of integers to a string of the format 1, 2 and 3
     * Used to make warnings and assertion texts.
     * Precondition: at least two numbers to format
     * @param numList List of integers
     * @return String Formatted integers in text form
     */
    String formatNumListToText(List<Integer> numList) {
        assert numList.size() > 1: "There should be at least two numbers to format."
        List<Integer> formattedList = numList() as List<Integer>
        String lastNum = formattedList.remove(formattedList.size() - 1)
        String otherNumJoined = formattedList.join(", ")
        return otherNumJoined + " and " + lastNum
    }

    /**
     * Implementation for the step 'Then the user should verify the link format of each assignment link'.
     * For each assignment listed, checks that the link is of the format '/system/files/uploads/courses/Testing/assignment[NR].pdf'
     * Gives assertion if the layout differs for one of the assignments.
     * @return List of integers where each item is the assignment number that does not have the right format.
     */
    List<Integer> verifyAssignmentsLinkFormat() {
        List<WebElement> links = getAssignmentsLinkElements()
        int numberOfLinks = getNumberOfAssignmentLinks()

        // Loop over all links
        List<Integer> nonFormattedLinks = []
        for (int i = 0; i < numberOfLinks; i++) {
            WebElement link = links[i] // Get WebElement
            String urlText = link.getAttribute("href") // Retrieve URL associated with link
            String correctText = "/system/files/uploads/courses/Testing/assignment${i + 1}.pdf" // What URL should be
            if (urlText != correctText) { // Not the right format
                nonFormattedLinks.add(i + 1) // Then add assignment number to result
            }
        }
        return nonFormattedLinks
    }

    /**
     * Formats the assertion for the links that are not formatted correctly
     * @param nonFormattedLinks A list of Integers that are the number of the assignments with non-existent links
     * @return String AssertionText with formatted numbers
     */
    String formatLinkFormatAssertion(List<Integer> nonFormattedLinks) {
        String assertionText
        if (nonFormattedLinks.isEmpty()) { // No assertion
            assertionText = null
        } else if (nonFormattedLinks.size() == 1) { // Singular
            assertionText = "Assignment ${nonFormattedLinks[0]} has a link with an incorrect format."
        } else { // Plural
            assertionText = "Assignments ${formatNumListToText(nonFormattedLinks)} have a link with an incorrect format."
        }
        return assertionText
    }

    /**
     * Save the input that the user has given as class variables.
     *
     * @param name Full student name that needs to be verified.
     * @param group Group number that needs to be verified.
     */
    void provideStudentGroup(String name, String group) {
        this.inputStudentName = name
        this.inputGroupNumber = group as Integer
    }

    /**
     * List of WebElements where each item is a full block description of one group.
     */
    @FindAll([
            @FindBy(css = "html > body > div > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > div > div:nth-of-type(3) > ul:nth-of-type(7) > ul")
    ])
    List<WebElement> fullGroups

    /**
     *  Number of groups in total.
     *
     * @return int The number of groups that there are in total.
     */
    int getNumberOfGroups() {
        return fullGroups.size()
    }

    /**
     * List of WebElements where each item is the full presentation date sentence (e.g., Presentation date: 28 May 2020).
     * Found with CSS selector.
     */
    @FindAll([
            @FindBy(css = "html > body > div > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > div > div:nth-of-type(3) > ul:nth-of-type(7) > ul > li:nth-of-type(1)")
    ])
    List<WebElement> allDatesWebElements

    /**
     * Reforms date sentence of correct group-number to a date object.
     *
     * @param groupNumber Int that is the group number that is linked with a certain date
     * @return date LocalDate object that is the date where the group with this group number is linked to.
     */
    LocalDate getDate(int groupNumber) {
        // Get date text of right WebElement
        WebElement fullDateWebElement = allDatesWebElements.get(groupNumber - 1)
        String fullDate = fullDateWebElement.getText()

        // Make text into date
        String dateString = fullDate.replaceAll("Presentation Date: ",
                "") // Extract the date portion from the full date string
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH)
        // Define the formatter for parsing the date
        return LocalDate.parse(dateString, inputFormatter) // Parse the date string into a LocalDate object
    }

    /**
     * List of WebElements where each web-element is the full presenter sentence of a group (e.g., Presenters: Luna Geens, Tom Smith, Angela Merkel, Sophie Link)
     * Found with CSS Selector
     */
    @FindAll([
            @FindBy(css = "html > body > div > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > div > div:nth-of-type(3) > ul:nth-of-type(7) > ul > li:nth-of-type(2)")
    ])
    List<WebElement> allPresentersWebElements

    /**
     * Gets the names of people that are in a group and need to present.
     *
     * @param groupNumber Int that is the group number where u want the presenters from.
     * @return presenters List of Strings where each item is a full name of a presenter.
     */
    List<String> getPresenters(int groupNumber) {
        // Get presenter text of right WebElement
        WebElement fullPresentersWebElement = allPresentersWebElements.get(groupNumber - 1)
        String fullPresenters = fullPresentersWebElement.getText()

        // Make text into list
        fullPresenters = fullPresenters.replace("Presenters: ", "")
        return fullPresenters.split(", ")
    }

    /**
     * List of WebElements where each web-element is the full opponent sentence of a group (e.g., Opponents: Luna Geens, Tom Smith, Angela Merkel, Sophie Link)
     * Found with CSS Selector
     */
    @FindAll([
            @FindBy(css = "html > body > div > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > div > div:nth-of-type(3) > ul:nth-of-type(7) > ul > li:nth-of-type(3)")
    ])
    List<WebElement> allOpponentsWebElements

    /**
     * Gets the names of people that are in a group and have the role of opponent.
     *
     * @param groupNumber Int that is the group number where u want the opponents from.
     * @return List of Strings where each item is a full name of an opponent.
     */
    List<String> getOpponents(int groupNumber) {
        // Get text from right WebElement
        WebElement fullOpponentsWebElement = allOpponentsWebElements.get(groupNumber - 1)
        String fullOpponents = fullOpponentsWebElement.getText()

        // Make text into List
        fullOpponents = fullOpponents.replace("Opponents: ", "")
        return fullOpponents.split(", ")
    }

    /**
     * Search the group number of the group where a student is presenter, and where a student is opponent by the name of the student.
     * Assumes that if the student is in any group, that the student is two groups (one as presenter and one as opponent).
     *
     * @param studentName The full name of the student.
     * @return List of Integer that is either null when the student is not in any groups, or contains two Integers,
     * where the first one is the presenter group and the second one is the opponent group.
     */
    List<Integer> getGroupNumbers(String studentName) {
        // Initiate result variables
        List<Integer> groupNumbers = []
        int presenterGroup = 0
        int opponentGroup = 0

        // Loop over all groups
        int totalNumberOfGroups = getNumberOfGroups()
        for (int i = 0; i < totalNumberOfGroups; i++) {
            int groupNum = i + 1
            // Check if they are presenter in this group
            List<String> presenters = getPresenters(groupNum)
            if (presenters.contains(studentName)) {
                presenterGroup = groupNum
            }
            // Check if they are opponent in this group
            List<String> opponents = getOpponents(groupNum)
            if (opponents.contains(studentName)) {
                opponentGroup = groupNum
            }
        }
        // If we have found groups, add them in the right order to the result variable
        if (presenterGroup != 0) {
            groupNumbers.add(presenterGroup)
        }
        if (opponentGroup != 0) {
            groupNumbers.add(opponentGroup)
        }
        return groupNumbers.isEmpty() ? null : groupNumbers
    }

    /**
     * Check if the student is in any group.
     *
     * @return boolean True if the student is in one or more groups.
     */
    boolean inAnyGroup() {
        return !(getGroupNumbers(inputStudentName) == null)
    }

    /**
     * Check if the given student is either an opponent or a presenter in the given group number.
     *
     * @return boolean True if the student is part of the student group, either as opponent or presenter (or both).
     */
    boolean verifyStudentGroupWarning() {
        return getPresenters(inputGroupNumber).contains(inputStudentName) || getOpponents(inputGroupNumber).contains(inputStudentName)
    }

    /**
     * Searches the date where the given student should present on.
     * Formats that date and puts it into an output message.
     *
     * @return String Informational message that contains the date the student should present on.
     */
    String presencePresenter() {
        // Get presenter group numbers of student, and search the date.
        List<Integer> groupNums = getGroupNumbers(inputStudentName)
        LocalDate presenterDate = getDate(groupNums.get(0))
        // Reformat date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        String formattedDate = presenterDate.format(formatter)
        return "The student $inputStudentName should present on $formattedDate."
    }

    /**
     * Searches the date where the given student should play the role of opponent on.
     * Formats that date and puts it into an output message.
     *
     * @return String Informational message that contains the date the student should present on.
     */
    String presenceOpponent() {
        // Get opponent group numbers of student, and search the date.
        List<Integer> groupNums = getGroupNumbers(inputStudentName)
        LocalDate opponentDate = getDate(groupNums.get(1))
        // Reformat date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        String formattedDate = opponentDate.format(formatter)

        return "The student $inputStudentName should play the role of opponent on $formattedDate."
    }

}


















