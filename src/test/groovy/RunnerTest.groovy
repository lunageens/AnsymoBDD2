import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.Suite

// TODO Assertion error -> to logger frameworks errors
// TODO Allure reports
// TODO Plain Java and Junit files?
// TODO Assertion Error and answers about warnings - Implementation versus testing?

/**
 * JUnit class that specifies how Cucumber should run tests.
 * Make sure to add Test in the name at the end for JUnit to find them
 */
// The cucumber options are now in the junit-platform.properties (separate file)
// from @Run with (Cucumber)
@Suite
@IncludeEngines("cucumber")
class RunnerTest {
// this may be empty
}