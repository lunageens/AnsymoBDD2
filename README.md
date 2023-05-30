
This program is set up to use Selenium to test the GUI of a web-application (and not the implementation).

# 1. Use of different frameworks
## 1.1 Selenium Webdriver
Selenium is a powerful open-source framework for automated web testing. It has two different versions, 1) the WebDriver, and 2) the IDE. Using the Selenium IDE we can easily record and playback scripts. For this particular exercises, we could use the Selenium IDE to export code, but these scripts should be runnable java programs (which is why we will use maven). Therefore, i choose to use the **Selenium Webdriver** dependency (see `WebDriverManager.java` class).

## 1.2 BDD testing with Gherkin and Cucumber
As often with Selenium, we will use it in combination with a framework for Behaviour-Driven Development (BDD). BDD focuses on defining and specifying desired behaviours of (the GUI of) the web-application in a common natural language. A framework for BDD provides the structure and set of guidelines for implementing BDD practices. It includes tools, libraries, and utilities to support the creation and execution of BDD tests.

### [1.2.1 Scenario description](src/main/test/resources/features/BrowseCourses.feature)
An example of such a framework is **Gherkin**, Gherkin is a language used to write BDD scenarios in a structured, human-readable format. We used Gherkin to define scenarios in the `src/test/resources/BrowseCourses.feature` file. One could also use other languages, such as Jbehave, to write scenarios.

### [1.2.2 Scenario implementation](src/main/test/java/runners/RunnerTest.java)
On top of that, **Cucumber** is another example of such a BDD framework. Cucumber supports various programming languages, such as Java (as in this project). It is used to create the executable specification written in Gherkin syntax. As seen in `BrowseCoursesSteps.java`, it is used to define the implementation of the scenarios we wrote in natural language.

 -  The actual implementation is written following the page pattern, where methods are categorized per page (see the package `pageObjects`).
 -  The actual implementation is written in **Java**.
 -  The **JUnit** testing framework for Java is used to support the implementation of assertions in the test, to check if the tests pass or fails.
 -  **Pico-container** is used as a lightweight dependency injection container for Java, which can be used in BDD frameworks for managing dependencies in test scenarios.

Cucumber also supports `Hooks.java`, that tell the program what to do before and after the execution of each scenario for example. The `BaseClass.java` provides two manager objects, so that we can share them between the `BrowseCoursesSteps.java` and `Hooks.java` classes. The `RunnerTest.java` is the overall class used to run the test.

### 1.2.3 Combination of scenario description and implementation in other projects
If one would use JBehave, one could define those steps with the Jbehave framework as well. One would make a runner class with the support of JUnit framework.

## 1.3 Groovy
In combination with a BDD framework, we will use a scripting language. As said, the actual implementation in Step files and the Selenium Page Object files initially was fully written in Java. However, scripting languages like **Groovy** are often used for automating specific tasks or writing scripts. Groovy has more simplified syntax an automates tasks in comparison to Java (and its JUnit framework) in most cases. Groovy runs on the Java Virtual Machine (JVM), meaning that one can perfectly combine the two languages. Other scripting languages are for example Ruby. Some examples of how Groovy has improved the (readability of) the code:

 -  In `BrowseCoursesSteps.java`,
 -  In `BrowseSoftwareTestingSteps.java`,
 -  In the package `pageObjects`,
 -

## 1.4 [Logging frameworks](src/test/resources/log4j.properties)
**Log4j** and **SLF4J** are used to log application-related messages, such as the warnings were needed in some tests. Therefore, it is used in the Step files.

 -  Log4j is a logging library to log messages in Java applications. It allows developers to configure logging behavior dynamically and provides various logging levels (e.g., DEBUG, INFO, WARN, ERROR) to differentiate the severity of logged messages. Log4j also supports different output targets such as console, file, and database.
 -  SLF4J (Simple Logging Facade for Java) provides a common API for various logging frameworks, including Log4j.

Browser-specific logs in Firefox were long and suppressed via the following line in the `WebDriverManager.java` class:
`System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");`

## 1.5 Reporting frameworks
Using the **JavaDoc** Tools of IntelliJ IDEA, one can find the index.html file in the [`javadoc` directory](javadoc/index.html) that describes the utility of each method, class and variable.
> URL Javadoc: [Ansymore site on Netlify](https://ansymo2site.netlify.app/).

Using the reports produced by **Cucumber**, we can find the Cucumber.html file in the [`target/cucumber-reports`](target/cucumber-reports/cucumber-html-reports/Cucumber.html) directory that gives us a short explanation of the test results. Because this html file was missing a SPA redirect rule and we wanted to deploy all the sites to **Netlify**, i created a `_redirects` file in the netlifydocs directory and automated copying it to the correct `target/cucumber-reports/cucumber-html-reports` directory with the support of the **maven-resources-plugin**.
> URL Cucumber reports: [Ansymore Cucumber reports](https://anysmo2cucumberresults.netlify.app/).

For more detailed reporting, one can make use of the **Allure** reporting framework.
> URL Allure reports: [Ansymore Allure reports]( ).

# 2 Expected behaviour
The program is set up to test certain behaviours of the [Ansymo web-application](https://ansymore.uantwerpen.be). One could alter the `@CucumberOptions(tags = "...")` in the Runner class to only test the script from specific exercises.

## 2.1 [BrowseCoursesSteps](src/test/java/stepdefinitions/BrowseCoursesSteps.java)
Refers to `@Exercise5`, where we browse courses from the homepage on to the overall courses page and then for each course, a detailed page with more information. The script is the following:

> Create a test script that navigates to all the different courses from the menu.
> The script should loop over the different menu items and verify that each page is loaded.
> Verify that each course has a professor (field not empty).
> The script should only fail when a page cannot be loaded or when the course does not have a professor.

## 2.2 BrowseSoftwareTestingSteps
Firstly, in `@Exercise6`, a script should check the link existence and format of assignments of the software testing
course. The test script is the following:

> Create a script that navigates to the “Software Testing” course.
> For each assignment, verify that the link to the assignment is: “/system/files/uploads/courses/Testing/assignment<NR>.pdf”.
> The script should fail when the link layout differs. Make sure that the script is dynamic! Adding or removing assignments should not result in a failure.
> Verify that the document (link) exists on the server. Give a warning when it does not exist.

Secondly, in `@Exercise7` and `@Exercise8`, the user gives a student group number and student name as input.

A student name can be in no, or two groups (one time as a presenter, and one time as opponent). In `@Exercise7`, this is checked. The script goes as follows:

> Create a function with student and group as input that verifies whether the student is in the provided group.
> Give a warning when the student is not in the provided group. Fail if the student cannot be found in any of the groups.
> Create a script that verifies that you are in the correct group.

In `@Exercise8`, we can use that function to create a function that shows the user the date he should present and the date he should be an opponent. The test script is:

> Using the function from exercise 7:
>   A) Create a function that returns the date a student needs to present a lecture.
>   B) Create a function that returns the date a student is an opponent.
> Create a script that verifies when you needed to present/were the opponent using the above functions.

# 3 [Configurations](configs/Configuration.properties)
When using this program, alter the `Configuration.properties` file as you wish. The different keys have the following meaning:

 -  The urlHome is the URL to the home page of the application. In case this URL ever changes, please alter this value.
 -  The urlCourses is the URL to the page where all the courses are listed together. In case this URL ever changes, please alter this value.
 -  The urlSoftwareTesting is the URL to the detailed page about the Software Testing course. In case this URL ever changes, please alter this value.
 -  The environment key can have values as specified in the `EnvironmentType` enum file. Currently, only local browsing is implemented.
 -  The browser key can have values as specified in the `DriverType` enum file. It is the browser where the tests are run on using Selenium. The program is written and tested with a recent version of Firefox (133.0.2). Make sure u use a browser (version) that is compatible with the Selenium webdriver version u are using. For example, Selenium WebDriver 4x requires Firefox 78 or greater. Lower versions of Selenium also require the use of an additional webdriver (`geckodriver.exe`) for Firefox. Please be aware that after all tests are run, this program closes all processes related to that browser in order to enhance memory use.
 -  The implicitlyWait key specifies the number of seconds to tell the web driver to wait for a certain amount of time before it throws a “No Such Element Exception”. Once we set the time, the web driver will wait for the element for that time before throwing an exception.
 -  The windowMaximize key is set to true if u want the browser to maximize the window while testing.
 -  The headless key is set to true if u do not want to see the browser GUI while running the tests.
 -  The operatingSystem key can have values as specified in the `OperatingSystemType` enum file.

The `Configuration.properties` file is read in with the `ConfigFileReader.java` class, which is managed by the `FileReaderManager.java` class using the Singleton pattern.