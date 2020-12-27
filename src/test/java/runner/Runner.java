package runner;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stepdefinitions.CukeHooks;
import runner.Runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/main/resources/features/Scenario-1.feature",
		glue="stepdefinitions",
		tags = "@TC_01",
		monochrome=true,
		dryRun=false,
		plugin= {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		) 

public class Runner {
	 private Runner() {

	    }

	    protected static final Logger LOG = LoggerFactory.getLogger(Runner.class);
	    protected static Integer passedCount;
	    protected static Integer failedCount;
	    protected static String finalOutput = "{\"moduleName\":regressiontest_ui/regressiontest_api/regressiontest_rwd_ui,\"pass\":passCount,\"fail\":failCount,\"status\":In Progress/Pass/Fail};";

	    @BeforeClass
	    public static void beforeClass() {
	        LOG.info("########### INSIDE BEFORE CLASS ###########");
	        CukeHooks.passedCount = 0;
	        CukeHooks.failedCount = 0;


	    }


	    @AfterClass
	    public static void afterSuite() {

	        String status = "PASS";
	        if (CukeHooks.failedCount > 0) {
	            status = "FAIL";
	        }


	    }
	

}
