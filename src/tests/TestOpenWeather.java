package tests;
import org.testng.annotations.Test;
import pages.OpenWeatherMapPage;
import org.testng.annotations.Parameters;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.asserts.SoftAssert;

import utilities.Actions;

public class TestOpenWeather {
	WebDriver driver = null;
	OpenWeatherMapPage objOpenWeatherMap;
	SoftAssert softAssert = new SoftAssert();
	Actions action;

  @BeforeTest
  @Parameters("browser")
  public void setBrowser(String browser) throws Exception {
		if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else
		{
			throw new Exception("Invalid browser or browser not supported");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://openweathermap.org/");
  	  	objOpenWeatherMap = new OpenWeatherMapPage(driver);
  	  	action = new Actions();
  	    objOpenWeatherMap.waitForPageFinishLoading();
  }

  @Test
  public void testSearchForSpecificLocationWeather() throws InterruptedException{
	  String strSearchLocation = "Haiphong";
	  String strFirstOptionLocation = "";
	  String strFirstOptionTemp = "";
	  // Set specific location for searching and then press Enter to search
	  objOpenWeatherMap.searchForLocation(strSearchLocation);
	  
	  // Get the first option location and temperature for comparison later
	  strFirstOptionLocation = objOpenWeatherMap.getFirstOptionLocationFromSearchResult(strSearchLocation);
	  strFirstOptionTemp = objOpenWeatherMap.getFirstOptionTemperatureFromSearchResult(strSearchLocation);
	  // Click on the location link in result list
	  objOpenWeatherMap.selectOptionFromForecastList(strSearchLocation);
	  
	  // Verify the current time is correct
	  SimpleDateFormat sdf = new SimpleDateFormat("MMM d, hh:mmaa");
	  String strCurrentTime = sdf.format(new Date()).replace("AM", "am").replace("PM", "pm");
	  softAssert.assertEquals(objOpenWeatherMap.getLblCurrentDateTimeText(), strCurrentTime);
	  // Verify the specific location is correct
	  softAssert.assertEquals(objOpenWeatherMap.getLblCurrentLocationText(), strFirstOptionLocation);
	  // Verify the specific location temperature is correct
	  softAssert.assertEquals(objOpenWeatherMap.getLblCurrentTemperatureText(), strFirstOptionTemp);
	  softAssert.assertAll();
  }
  
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
