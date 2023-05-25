package pageObjects;

import managers.FileReaderManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

// page_url = https://ansymore.uantwerpen.be/courses/software-testing
public class SoftwareTestingPage extends CourseDetailsPage {

    WebDriver driver;

    public SoftwareTestingPage(WebDriver driver) {
        super(driver);
        System.out.println("a new software testing page is called by calling constructor of coursedetialspage");
    }

    public void navigateToSoftWareTesting() {
        System.out.println("method navigate to software testing method of software testing page is called");
        // instead of making instance of configfile reader everytime -> use file reader manager with singleton pattern
        driver.get(FileReaderManager.getInstance().getConfigReader().getSoftwareTestingUrl());
    }

    @FindAll(@FindBy(css = "html > body > div > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > div > div:nth-of-type(3) > ul:nth-of-type(6)"))
    List <WebElement> AssignmentsElements;
    public List<WebElement> getAssignmentsLinkElements() {
        System.out.println("method get assingmentlsinkelement in softwaretestingpage is being called");
        // TODO check if this is the right list
        List<WebElement> assignmentsLinkElements = new ArrayList<>();
        for (WebElement assignmentElement : AssignmentsElements) {
            if (assignmentElement.getText().contains("Assignment")) {
                assignmentsLinkElements.add(assignmentElement); // get only the links
            }
        }
        return assignmentsLinkElements;
    }

    public boolean verifyAssignmentsLinkPresent(){
        System.out.println("method verify assingmentlink present is being called of software testing page");
        return !getAssignmentsLinkElements().isEmpty();
    }

    public void verifyAssignmentsLinkFormat(){
        System.out.println("method verify assingment link format is being called of softwaretesting page");
        List <WebElement> Links = getAssignmentsLinkElements();
        // TODO actual implementation of this method
    }

    public void verifyAssignmentsLinkExistence(){
        List <WebElement> Links = getAssignmentsLinkElements();
        // TODO actual implementation of this method
    }

    public void userInput(String name, String group){
        // TODO actual implementation of this method
    }

    public boolean inAnyGroup(){
        // TODO actual implementation of this method
        return true;
    }

    public void verifyStudentGroup(){
        // TODO actual implementation of this method
    }

    public void presencePresenter(){
        // TODO actual implementation of this method
    }

    public void presenceOpponent(){
        // TODO actual implementation of this method
    }
}