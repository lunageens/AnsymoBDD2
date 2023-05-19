package com.Ansymo.cucumber.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// this is junit class
// location in project: src/test/java/com.Ansymo.cucumber.runners package
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/BrowseCourses.feature"}, //specify the .feature file
        glue = {"com.Ansymo.cucumber.stepdefinitions"}, // specify the package with step definitions
        plugin = {"pretty","json:target/cucumber-reports/reports.json"})
public class RunnerTest {
    // this may be empty
}
