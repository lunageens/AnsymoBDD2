package com.Ansymo.cucumber.stepdefinitions.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = https://ansymore.uantwerpen.be/
// location in project = src/test/com.Ansymo.cucumber.stepdefinitions.pages package
// no real implementation of page pattern yet
public class HomePage {
    @FindBy(xpath = "//a[@href='/courses']")
    public WebElement linkCourses;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    
}