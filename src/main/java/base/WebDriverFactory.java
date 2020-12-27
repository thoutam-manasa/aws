package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromiumDriverManager;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

//import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import base.Logs;


public class WebDriverFactory {
	static WebDriver driver;
	static Logs log=new Logs(driver);
	static String path1="drivers/chromedriver.exe";
	static String path2="drivers/geckodriver.exe";
	static String path3="drivers/msedgedriver.exe";
	static String path4="drivers/IEDriverServer.exe";
	

//method to launch selected browser
	public static WebDriver Launch_Browser(String url) throws IOException
	{
	 ChromeOptions chromeOptions;
     FirefoxOptions firefoxOptions;
     URL testGridUrl = null;
     String strExecutionPlatform = System.getProperty("executionPlatform").trim().toUpperCase();
    
     
     switch (strExecutionPlatform) {
         case "LOCAL_CHROME":
        	 System.setProperty("webdriver.chrome.driver",path1);
     		chromeOptions=new ChromeOptions();
     		chromeOptions.addArguments("--disable-notifications");
     		driver=new ChromeDriver(chromeOptions);
     		System.out.println("Chrome browser is launched");
     		log.update("******Chrome browser Successfully Launched******");
             break;
         case "LOCAL_FIREFOX":
        	 System.setProperty("webdriver.gecko.driver",path2);
     		firefoxOptions=new FirefoxOptions();
     		firefoxOptions.addPreference("dom.webnotifications.enabled","false");
     		driver=new FirefoxDriver(firefoxOptions);
     		System.out.println("Firefox browser is launched");
     		log.update("******Firefox  browser Successfully Launched******");
             break;
         case "AWS_CHROME":
        	 chromeOptions = new ChromeOptions();
             chromeOptions.setHeadless(true);
             chromeOptions.addArguments("--no-sandbox");
             chromeOptions.addArguments("--disable-dev-shm-usage");
             chromeOptions.addArguments("start-maximized");
             chromeOptions.addArguments("--disable-gpu");
             chromeOptions.addArguments("enable-automation");
             chromeOptions.addArguments("--disable-infobars");
            // chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
             chromeOptions.setBinary(readData().getProperty("AWS_CHROME_BROWSER_PATH").trim());
//             WebDriverManager.chromedriver().setup();
             System.setProperty("webdriver.chrome.driver", readData().getProperty("AWS_CHROME_DRIVER_PATH").trim());
             driver = new ChromeDriver(chromeOptions);
             driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
             break;
         case "AWS_FIREFOX":
        	 firefoxOptions = new FirefoxOptions();
             firefoxOptions.setHeadless(true);
//             System.setProperty("webdriver.gecko.driver", readData().getProperty("AWS_FIREFOX_DRIVER_PATH").trim());
             WebDriverManager.firefoxdriver().setup();
             driver = new FirefoxDriver(firefoxOptions);
                         break;
                         
         case "AWS_DEVICEFARM_CHROME":
             testGridUrl = getTestGridUrl();
             DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
             chromeOptions = new ChromeOptions();
             //                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
             desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
             driver = new RemoteWebDriver(testGridUrl, desiredCapabilities);
             break;
         case "AWS_DEVICEFARM_FIREFOX":
             testGridUrl = getTestGridUrl();
             driver = new RemoteWebDriver(testGridUrl, DesiredCapabilities.firefox());
             ExtentCucumberAdapter.addTestStepLog(strExecutionPlatform + " Driver Creation Completed");
           
             break;
         case "AWS_FARGATE":
             DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
             ChromeOptions chromeOpt = new ChromeOptions();
             chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOpt);
             driver = new RemoteWebDriver(new URL("http://3.19.74.252:4444/wd/hub"), chromeCapabilities);
             ExtentCucumberAdapter.addTestStepLog(strExecutionPlatform + " Driver Creation Completed");
             
             break;
         default:
             ExtentCucumberAdapter.addTestStepLog("ExecutionPlatform Platform must be set in settings file.");
         
     }
	
	
	
	driver.get(readData().getProperty("url"));
		driver.manage().window().maximize();
		return driver;
	}
	
	 public static Properties readData() {
	        Properties configProp = new Properties();
	        try {
	            String env = System.getProperty("env");
	            File file = new File("configuration.properties");
	            FileInputStream fileInput = null;
	            fileInput = new FileInputStream(file);
	           configProp.load(fileInput);
	           
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return configProp;
	    }
	
	 public static URL getTestGridUrl() {
	        String strAWSprojectArn = readData().getProperty("AWSprojectArn");
	        System.out.println("eeee:" + strAWSprojectArn);
	        DeviceFarmClient client = DeviceFarmClient.builder().region(Region.US_WEST_2).build();
	        CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder()
	                .expiresInSeconds(300) // 5 minutes
	                .projectArn(strAWSprojectArn)
	                .build();
	        URL testGridUrl = null;
	        try {
	            CreateTestGridUrlResponse response = client.createTestGridUrl(request);
	            testGridUrl = new URL(response.url());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("testGridUrl: " + testGridUrl);
	        return testGridUrl;
	    }
}
