package com.Ansymo.cucumber.stepdefinitions.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

// page_url = https://ansymore.uantwerpen.be/
// location in project = src/test/com.Ansymo.cucumber.stepdefinitions.pages package
// no real implementation of page pattern yet
public class HomePage {
    @FindBy(xpath = "//a[@href='/courses']")
    public WebElement linkCourses;
    private WebDriver driver;

    public HomePage(WebDriver driver) { this.driver = driver; }

    public void navigateToHomePage(String url) {
        driver.get(url);
    }

    public void clickMenuItem(String linkText) {
        driver.findElement(By.linkText(linkText)).click();
        System.out.println("Clicked on the  Course item in the menu section");
    }
    
}