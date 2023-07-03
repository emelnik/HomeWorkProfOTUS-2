package com.otus.driver;

import com.otus.driver.impl.ChromeWebDriver;
import com.otus.exceptions.DriverTypeNotSupported;
import org.openqa.selenium.WebDriver;

public class DriverFactory implements IDriverFactory {

  @Override
  public WebDriver getDriver(String browserName) throws DriverTypeNotSupported {

    switch (browserName) {
      case "chrome": {
        return new ChromeWebDriver().newDriver();
      }
      default: {
        try {
          throw new DriverTypeNotSupported(browserName);
        } catch (DriverTypeNotSupported ex) {
          ex.printStackTrace();
          return null;
        }
      }
    }
  }
}
