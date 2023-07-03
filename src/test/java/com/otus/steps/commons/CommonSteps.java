package com.otus.steps.commons;

import com.google.inject.Inject;
import com.otus.driver.DriverFactory;
import com.otus.exceptions.DriverTypeNotSupported;
import com.otus.pages.MainAbsPage;
import di.GuiceScooped;
import io.cucumber.java.ru.Пусть;

public class CommonSteps {

  @Inject
  public GuiceScooped guiceScooped;

  @Inject
  public MainAbsPage mainPage;

  @Пусть("Открываем браузер {string}")
  public void openBrowser(String browserName) throws DriverTypeNotSupported {
    guiceScooped.driver = new DriverFactory().getDriver(browserName);
  }

}
