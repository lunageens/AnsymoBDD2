package stepdefinitions;

import cucumber.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;

public class Hooks {

    static TestContext testContext;

    public Hooks(TestContext context){
        testContext = context;
    }

    @Before
    public void beforeScenario(){
        System.out.println("------------------------------------------------------start of scenario------------------------------------------------------");
    }

    @After
    public void afterScenario(){
        System.out.println("------------------------------------------------------end of scenario------------------------------------------------------");
    }

}
