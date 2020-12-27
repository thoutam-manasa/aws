package stepdefinitions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import stepdefinitions.MasterStepDefs;

import org.openqa.selenium.support.PageFactory;

//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.java.en.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.*;
import pages.LoginPage;
import utilities.ExcelRead;

import base.WebDriverFactory;
import base.Screenshot;
import cucumber.api.java.en.*;


public class SmartBear extends MasterStepDefs{
	WebDriver driver;
	Screenshot sc;
	ExcelRead excelRead=new ExcelRead();
	
	LoginPage login;
	OrdersDetails_Page Orders;
	

	
@Given("^Open chrome and start application$")
public void open_chrome_and_start_application() throws Throwable {
	 
	excelRead.read();
	driver=WebDriverFactory.Launch_Browser("url");
	 ExtentCucumberAdapter.addTestStepLog("Application launched successfully");
     try {
         ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
     } catch (IOException e) {
         e.printStackTrace();
     }
	
}

@When("Entering Username,Password and Click on Login button")
public void entering_Username_Password_and_Click_on_Login_button() throws Throwable {
	 
	login=new LoginPage(driver);
	login.enterUserName();
	login.enterPassword();
    login.clickOnLoginButton();

    ExtentCucumberAdapter.addTestStepLog("Entering Username,Password and Click on Login button");
    try {
        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
    } catch (IOException e) {
        e.printStackTrace();
    }
}


@Then("^user should be able to login$")
public void user_should_be_able_to_login() throws Throwable {

	login=new LoginPage(driver);
	
	Assert.assertEquals("http://downloads.smartbear.com/samples/TestComplete10/WebOrders/Login.aspx", driver.getCurrentUrl());
	
	
	System.out.println(driver.getCurrentUrl());
	System.out.println("login sucessfull"); 
	sc.getScreenshot(driver);
	ExtentCucumberAdapter.addTestStepLog("User is able to login-verified Successfull login message");
    try {
        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
    } catch (IOException e) {
        e.printStackTrace();
    }
	//logger.info("login sucessful");
}

@And("^user clicks on edit option$")
public void user_update_mail_id() throws Throwable {
	Orders = new OrdersDetails_Page(driver);
	Orders.Click_edit();
	 ExtentCucumberAdapter.addTestStepLog("user clicks on edit button");
     try {
         ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
     } catch (IOException e) {
         e.printStackTrace();
     }
	
}

@When("user update card type")
public void user_update_card_type() throws TimeoutException {
	Orders = new OrdersDetails_Page(driver);
  Orders.RadioBTN(); 
  ExtentCucumberAdapter.addTestStepLog("user update card type");
  try {
      ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
  } catch (IOException e) {
      e.printStackTrace();
  }
}

@When("user update card expiry date")
public void user_update_card_expiry_date() throws Throwable {
	Orders = new OrdersDetails_Page(driver);
	Orders.Expiry_Info();
	 ExtentCucumberAdapter.addTestStepLog("user update card expiry date");
	  try {
	      ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
	  } catch (IOException e) {
	      e.printStackTrace();
	  }
}

@When("user clicks on update button")
public void user_clicks_on_update_button() throws Throwable{
	Orders = new OrdersDetails_Page(driver);
	Orders.Update();
	 ExtentCucumberAdapter.addTestStepLog("user clicks on update button");
	  try {
	      ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
	  } catch (IOException e) {
	      e.printStackTrace();
	  }
}

@When("user verifies all the updates are done or not")
public void user_verifies_all_the_updates_are_done_or_not() throws Throwable{
	Orders = new OrdersDetails_Page(driver);
	Orders.Verify_Update_Details();
	 ExtentCucumberAdapter.addTestStepLog("user verifies all the updates are done or not");
	  try {
	      ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
	  } catch (IOException e) {
	      e.printStackTrace();
	  }
}


@And("^user clicks on logout$")
public void user_clicks_on_logout() throws Throwable {
	Orders = new OrdersDetails_Page(driver);
	Orders.clickonLogout();
	System.out.println(" logout successfully");
	
	 ExtentCucumberAdapter.addTestStepLog("user clicks on logout option");
     try {
         ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getScreenhot(driver));
     } catch (IOException e) {
         e.printStackTrace();
     }
}



@Then("^application should be closed$")
public void application_should_be_closed() throws Throwable {
	TimeUnit.SECONDS.sleep(5);
	driver.quit();

}


}
