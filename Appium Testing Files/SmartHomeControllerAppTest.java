package firstmvnartificialid;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class SmartHomeControllerAppTest {

	public static void main(String[] args) {
		 //device details
     try {
    	   	{ DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "vivo Y36");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        
      //base package, launcher 
        capabilities.setCapability("appPackage", "com.example.smarthomecontroller");
        capabilities.setCapability("appActivity", ".MainActivity");
        
        //driver details
        AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

        
        WebElement navigateButton = driver.findElement(By.id("com.example.smarthomecontroller:id/button_settings"));
        navigateButton.click();
        
        // Navigate using Button Click
        //driver.findElement(By.id("com.example.app:id/buttonNext")).click(); // Update ID as per your app

        // Verify New Screen (Example: Check if a new text appears)
        boolean isSaveSettingButtonDisplayed = driver.findElement(By.id("com.example.smarthomecontroller:id/button_save_settings")).isDisplayed();
        Thread.sleep(2000);
        // Print Result
        if (isSaveSettingButtonDisplayed) {
            System.out.println("Navigation Successful!");
        } else {
            System.out.println("Navigation Failed!");
        }
       
        
        driver.quit();

	} 
    	   	}catch(Exception e) {
    	   	 e.printStackTrace();
    	   	}

	}
	}
