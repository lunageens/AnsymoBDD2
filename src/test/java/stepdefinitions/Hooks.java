package stepdefinitions;

import cucumber.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;

public class Hooks {

    static TestContext testContext;

    public Hooks(TestContext context){
        System.out.println("Hooks constructor is called");
        testContext = context;
    }

    @Before
    public void beforeScenario(){
        System.out.println("Method beforeScenario of Hooks is called");
        System.out.println("------------------------------------------------------start of scenario------------------------------------------------------");
    }

    @After
    public void afterScenario(){
        System.out.println("Method afterScenario of Hooks is called");
        testContext.getWebDriverManager().closeDriver();
        System.out.println("------------------------------------------------------end of scenario------------------------------------------------------");
    }

}
