package pageObjects;


import dataProviders.ConfigFileReader;
import managers.FileReaderManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://ansymore.uantwerpen.be/
public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        System.out.println("A new homepage is created");
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "a[title='Courses']")
    public WebElement linkCourses;

    public void navigateToHomePage() {
        System.out.println("method navigate to homepage is called");
        // instead of making instance of configfile reader everytime -> use file reader manager with singleton pattern
        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationHomeUrl());
    }

    public void clickMenuItem(String linkText) { // will always navigate to courses and not other menu items, only works for our string
        System.out.println("method clickmenuitem of homepage is called");
        linkCourses.click();
    }
}