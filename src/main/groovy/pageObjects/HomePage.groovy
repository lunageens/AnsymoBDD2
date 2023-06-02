package pageObjects

import managers.FileReaderManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

/**
 * Applying the page pattern, all the methods related to the homepage
 * are specified and implemented in this class.
 * This is a Selenium Page Object.
 */
// page_url = https://ansymore.uantwerpen.be/
class HomePage {
    /**
     * WebDriver used to create the page and execute methods.
     */
    WebDriver driver

    /**
     * Constructor of HomePage
     *
     * @param driver WebDriver used to create the page and execute methods.
     */
    HomePage(WebDriver driver) {
        this.driver = driver
        PageFactory.initElements(this.driver, this)
    }

    /**
     * WebElement of the Courses menu option, found by the css operator.
     */
    @FindBy(css = "a[title='Courses']")
    WebElement linkCourses

    /**
     * Driver goes to the HomePage.
     */
    void navigateToHomePage() {
        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationHomeUrl()) // instead of making an instance of the config file reader everytime -> use file reader manager with singleton pattern
    }

    /**
     * Clicks the menu item that has the specified text.
     * Only implemented for the Courses menu item currently.
     *
     * @param linkText Text in the menu on the homepage that you want to click on. Only implemented for 'Courses'.
     */
    void clickMenuLink(String linkText) { // will always navigate to courses and not other menu items, only works for our string
        linkCourses.click()
    }
}
