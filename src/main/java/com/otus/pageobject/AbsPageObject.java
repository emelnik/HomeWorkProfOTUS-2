package com.otus.pageobject;

import com.google.inject.Inject;
import di.GuiceScooped;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbsPageObject<T> {

  protected WebDriver driver;

  @Inject
  public AbsPageObject(GuiceScooped guiceScooped) {
    this.driver = guiceScooped.driver;
    PageFactory.initElements(driver,this);
  }

}
