package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeoutException;

import base.DriverWait;
import base.Logs;
import utilities.ExcelRead;


public class OrdersDetails_Page {
		WebDriver driver;
		Logs log;
		DriverWait driverWait;

		By edit = By.xpath("//tbody/tr[2]/td[13]/input[1]");
		By card_type = By.id("ctl00_MainContent_fmwOrder_cardList_0");
		By expiry_info = By.id("ctl00_MainContent_fmwOrder_TextBox1");
		By update = By.id("ctl00_MainContent_fmwOrder_UpdateButton");
		By update_card = By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[10]");
		By update_expiry = By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[12]");
		By logout = By.xpath("//a[@id='ctl00_logout']");
		


		public  OrdersDetails_Page(WebDriver driver) throws TimeoutException{
			this.driver=driver;
			driverWait=new DriverWait(driver);
			log=new Logs(driver);
		}
		
		
		//giving click action
		public void Click_edit() {

			driver.findElement(edit).click();
			//System.out.println("Edit button is clicked");
			log.update("******Edit button is clicked******");

		}
		
		//selecting card type
		public void RadioBTN() {

			driver.findElement(card_type).click();
			
			//System.out.println("new card-type is selected");
			log.update("******new card-type is selected******");

		}
		
		//set card expirt date
		public void Expiry_Info() {

			driver.findElement(expiry_info).clear();
			driver.findElement(expiry_info).sendKeys("12/19");

			//System.out.println("expiry date is edited");
			log.update("******expiry date is edited******");
		}
		
		//clicking on update button
		public void Update() {

			driver.findElement(update).click();

			//System.out.println("Update button is clicked");
			log.update("******Update button is clicked******");
		}
		
		
		//giving click action
		public void Verify_Update_Details() {

			String cardType=driver.findElement(update_card).getText();
			String cardexpiry=driver.findElement(update_expiry).getText();
			String Expiry="12/19";
			String card= "Visa";
			//System.out.println("a"+cardType+"v"+cardexpiry);
			if((driver.findElement(update_card).isDisplayed())&&(driver.findElement(update_expiry).isDisplayed())) {
				System.out.println("details updated");
			}else {
				System.out.println("details not updated");
			}
			//System.out.println("Checking for updated details");
			log.update("******Checking for updated details******");
		}
		
		//clicking on logout button
		public void clickonLogout() {

			driver.findElement(logout).click();

			System.out.println("logout sucessful");
			log.update("******logout sucessful******");
		}
		
		

		
		}
		