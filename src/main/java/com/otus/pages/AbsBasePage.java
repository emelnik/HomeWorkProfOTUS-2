package com.otus.pages;

import com.google.inject.Inject;
import com.otus.annotations.Path;
import di.GuiceScooped;
import com.otus.pageobject.AbsPageObject;

public abstract class AbsBasePage<T> extends AbsPageObject<T> {

  @Inject
  public AbsBasePage(GuiceScooped guiceScooped) {
    super(guiceScooped);
  }

  private String baseUrl = System.getProperty("webdriver.base.url", "https://otus.ru");

  private String getPath() {

    Path path = getClass().getAnnotation(Path.class);
    if (path != null) {
      return path.value();
    }

    return "";
  }

  public T open() {
    driver.get(baseUrl + getPath());

    return (T) this;
  }

}
