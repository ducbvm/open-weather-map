package utilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class Actions {

	public void enterText(WebElement element, String strText)
	{
		element.clear();
		element.sendKeys(strText);
	}
	
	public void waitForElement(WebDriver driver, WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementToBeClickable(WebDriver driver, WebElement element)
	{
		waitForElement(driver, element);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void clickElement(WebDriver driver, WebElement element)
	{
		try
		{
			waitForElementToBeClickable(driver, element);
			element.click();	
		}
		catch (NullPointerException npe)
		{
			Reporter.log("Cannot click on element as element does not exist on UI");
		}
	}
	
	public void pressEnterOnKeyboard(WebElement element)
	{
		try
		{
			element.sendKeys(Keys.ENTER);
		}
		catch (NullPointerException npe)
		{
			Reporter.log("Cannot press Enter for element as element does not exist on UI");
		}
	}
	
	public boolean doesElementExist(WebElement element)
	{
		if (element != null)
		{	
			return element.isDisplayed();
		}
		return false;
	}
}
