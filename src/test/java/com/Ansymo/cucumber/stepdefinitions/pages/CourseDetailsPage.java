package com.Ansymo.cucumber.stepdefinitions.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

// page_url = about:blank
public class CourseDetailsPage {
    private WebDriver driver;
    public CourseDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCourseTitle(){
        // kwni of dit klopt
        WebElement titleElement = driver.findElement(By.cssSelector("h1"));
        return titleElement.getText(); }

    public List<String> getProfessorName() {
        // if there are 0, 1, or more
        // span.professor-name -> kwni of dit klopt
        List<WebElement> professorElements = driver.findElements(By.cssSelector("#content > div.buildmode-full > div > div.nd-region-middle-wrapper.nd-no-sidebars > div > div.field.field-professor-linked > a"));
        List<String> professorNames = new ArrayList<>();
        for (WebElement professorElement : professorElements) {
            professorNames.add(professorElement.getText());
        }
        return professorNames;
    }

    public boolean isCoursePageLoaded() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Maximum wait time of 10 seconds

            // Define the expected condition for the page to be loaded
            ExpectedCondition<Boolean> isPageLoadedCondition = driver -> {
                // Implement the logic to check if the page is loaded successfully
                // For example, wait for a specific element to be present or visible on the page
                try {
                    // Check if a specific element is present on the page
                    return driver.findElement(By.cssSelector("body")).isDisplayed();
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    // Element not found or stale, return false
                    return false;
                }
            };

            // Wait until the page is loaded or the timeout is reached
            return wait.until(isPageLoadedCondition);
        }

}