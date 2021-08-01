package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import utilities.Actions;

public class OpenWeatherMapPage {
	Actions action = new Actions();
	WebDriver driver;
	
    public OpenWeatherMapPage(WebDriver driver){

        this.driver = driver;

        PageFactory.initElements(driver, this);

    }
	
    @FindBy(css = "div[class='owm-loader-container']")
    WebElement divLoaderContainer;
    
	@FindBy(css = "input[name='q']")
	WebElement txtSearch;
	
	@FindBy(css = "div[id='forecast-list']")
	WebElement lstForecastList;
	
	@FindBy(css = "svg[class='icon-current-location']")
	WebElement icoLocation;
	
	@FindBy(xpath = "//div[@class='controls']/span[@class='control-el owm-switch']")
	WebElement btnDifferentWeather;
	
	@FindBy(xpath = "//div[@class='switch-container']/div[contains(., 'Metric: °C, m/s')]")
	WebElement sldMetricMeasurementSystem;
	
	@FindBy(xpath = "//div[@class='switch-container']/div[contains(., 'Imperial: °F, mph')]")
	WebElement sldImperialMeasurementSystem;
	
	@FindBy(xpath = "//div[@class='section-content']//span[@class='orange-text']")
	WebElement lblCurrentDateTime;
	
	@FindBy(xpath = "//div[@class='section-content']//h2")
	WebElement lblCurrentLocation;
	
	@FindBy(css = "div[class='current-temp'] svg[class='owm-weather-icon']")
	WebElement icoCurrentWeather;
	
	@FindBy(xpath = "//div[@class='current-temp']//span[@class='heading']")
	WebElement lblCurrentTemperature;
	
	@FindBy(xpath = "//div[@class='current-temp']/following-sibling::div[@class='bold']")
	WebElement lblCurrentWeatherDescription;

	public WebElement getTxtSearch() {
		return txtSearch;
	}

	public void searchForLocation(String strSearch)
	{
		action.enterText(txtSearch, strSearch);
		action.pressEnterOnKeyboard(txtSearch);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(lstForecastList));
	}
	
	public WebElement getLstForecast() {
		return lstForecastList;
	}

	public WebElement getIcoLocation() {
		return icoLocation;
	}
	
	public WebElement getBtnDifferentWeather() {
		return btnDifferentWeather;
	}

	public WebElement getSldMetricMeasurementSystem() {
		return sldMetricMeasurementSystem;
	}

	public WebElement getSldImperialMeasurementSystem() {
		return sldImperialMeasurementSystem;
	}

	public WebElement getLblCurrentDateTime() {
		return lblCurrentDateTime;
	}
	
	public String getLblCurrentDateTimeText() {
		return this.lblCurrentDateTime.getText().trim();
	}

	public WebElement getLblCurrentLocation() {
		return lblCurrentLocation;
	}

	public String getLblCurrentLocationText() {
		return this.lblCurrentLocation.getText().trim();
	}

	public WebElement getIcoCurrentWeather() {
		return icoCurrentWeather;
	}

	public WebElement getLblCurrentTemperature() {
		return lblCurrentTemperature;
	}

	public String getLblCurrentTemperatureText() {
		return this.lblCurrentTemperature.getText().trim();
	}

	public WebElement getLblCurrentWeatherDescription() {
		return lblCurrentWeatherDescription;
	}
	
	public WebElement getDivLoaderContainer() {
		return divLoaderContainer;
	}
	
	public void selectOptionFromForecastList(String strSearchSubstring)
	{
		try
		{
			WebElement optFromSearchResult = lstForecastList.findElement(By.xpath("//a[contains(., '" + strSearchSubstring + "')]"));
			if (optFromSearchResult != null)
			{
				action.clickElement(driver, optFromSearchResult);
			}
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(new ExpectedCondition<Boolean>() {
		        public Boolean apply(WebDriver wdriver) {
		            return ((JavascriptExecutor) driver).executeScript(
		                "return document.readyState"
		            ).equals("complete");
		        }
		    });
	  	    waitForPageFinishLoading();
		}
		catch (NoSuchElementException nse)
		{
			Reporter.log("There is no option in result list match with the search location");
		}
	}

	public String getFirstOptionLocationFromSearchResult(String strSearchSubstring)
	{
		String strFirstOptionLocation = "";
		try
		{
			WebElement optFromSearchResult = lstForecastList.findElement(By.xpath("//a[contains(., '" + strSearchSubstring + "')]"));
			strFirstOptionLocation = optFromSearchResult.getText().trim();
		}
		catch (NoSuchElementException nse)
		{
			Reporter.log("There is no option in result list match with the search location");
		}
		return strFirstOptionLocation;
	}
	
	public String getFirstOptionTemperatureFromSearchResult(String strSearchSubstring)
	{
		String strFirstOptionTemp = "";
		try
		{
			WebElement optTempFromSearchResult = lstForecastList.findElement(By.xpath("//td[contains(., '" + strSearchSubstring + "')]//span[@class='badge badge-info']"));
			strFirstOptionTemp = optTempFromSearchResult.getText().trim();
		}
		catch (NoSuchElementException nse)
		{
			Reporter.log("There is no temperature in result list for the search location");
		}
		return strFirstOptionTemp;
	}
	
	public void waitForPageFinishLoading()
	{
		try
		{
			WebElement icoLoading = divLoaderContainer.findElement(By.cssSelector("div[class='owm-loader']"));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.invisibilityOf(icoLoading));

		}
		catch (NoSuchElementException nse)
		{
			Reporter.log("Loading icon does not appear on UI");
		}
	}
}
