package pageObjects;


import dataProviders.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://ansymore.uantwerpen.be/
public class HomePage {

    WebDriver driver;
    ConfigFileReader configFileReader;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        configFileReader = new ConfigFileReader() ;
    }

    @FindBy(css = "a[title='Courses']")
    public WebElement linkCourses;

    public void navigateToHomePage() {
        driver.get(configFileReader.getApplicationHomeUrl());
    }

    public void clickMenuItem(String linkText) { // will always navigate to courses and not other menu items, only works for our string
        linkCourses.click();
    }
}