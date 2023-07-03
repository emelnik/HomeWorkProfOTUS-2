package com.otus.components;

import com.google.inject.Inject;
import di.GuiceScooped;
import com.otus.pageobject.AbsPageObject;

public abstract class AbsComponent<T> extends AbsPageObject<T> {

  @Inject
  public AbsComponent(GuiceScooped guiceScooped) {
    super(guiceScooped);
  }

}
