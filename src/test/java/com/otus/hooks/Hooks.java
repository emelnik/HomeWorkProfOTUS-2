package com.otus.hooks;

import com.google.inject.Inject;
import di.GuiceScooped;
import io.cucumber.java.After;

public class Hooks {

  @Inject
  private GuiceScooped guiceScooped;

  @After
  public void close(){

    if(guiceScooped.driver != null){
      guiceScooped.driver.close();
      guiceScooped.driver.quit();
    }

  }

}
