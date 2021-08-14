package oneway;

import org.testng.annotations.Test;
import base.ExcelFramework;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class RunnerOneWay extends ExcelFramework {

	public RunnerOneWay(String pathWithFileName) {
		super(pathWithFileName);
	}

	public WebDriver driver;

	@Test(dataProvider = "dp")
	public void searchFlights(
			String from, 
			String to, 
			String departureDate, 
			String adults, 
			String children, 
			String infants, 
			String title, 
			String firstname, 
			String lastname, 
			String mobile, 
			String email) throws InterruptedException {
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/test/resources/properties/oneway.property"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.id("wzrk-cancel")).click();

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		driver.findElement(By.xpath(prop.getProperty("from"))).sendKeys(from); // Please enter first name using letters
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.className("ui-menu-item")).click();
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath(prop.getProperty("to"))).sendKeys(to); // Please enter first name using letters
		Thread.sleep(2000);
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
		driver.findElement(By.className("ui-menu-item")).click();
		
//		driver.findElement(By.xpath(prop.getProperty("adults"))).click();
//		driver.findElement(By.xpath(prop.getProperty("children"))).click();
//		driver.findElement(By.xpath(prop.getProperty("infants"))).click();
		
		driver.findElement(By.xpath(prop.getProperty("searchflights"))).click();

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}

	
	@DataProvider
	public Object[][] dp() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/test/resources/properties/oneway.property"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExcelFramework ex = new ExcelFramework(prop.getProperty("excelUrl"));
		int rowCount = ex.getLastRowNum("One way trip");
		Object data[][] = new Object[rowCount][11];

		for (int i = 1; i < rowCount-1; i++)

		{
			for (int j = 0; j < 11; j++) {
				System.out.println(ex.readData("One way trip", i, j));
				data[i-1][j] = ex.readData("One way trip", i, j);
			}
		}

		return data;
	}

	@BeforeMethod
	public void beforeTest() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/test/resources/properties/oneway.property"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setProperty("webdriver.chrome.driver", prop.getProperty("cpath"));
		driver = new ChromeDriver();
	}

	@AfterMethod
	public void afterTest() throws Exception {
		Thread.sleep(2000);
		 driver.close();
	}

}