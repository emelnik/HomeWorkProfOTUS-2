package com.otus.steps.pages;

import com.google.inject.Inject;
import com.otus.pages.ProgrammingCourcesCatalog;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Тогда;

public class CoursesPageSteps {

  @Inject
  public ProgrammingCourcesCatalog programmingCourcesCatalog;

  @Если("Перейти на страницу курсов по программированию")
  public void openProgrammingCoursesCatalog(){
    programmingCourcesCatalog.open();
  }

  @Тогда("Ищем самый дорогой и самый дешевый подготовительный курс и выводим информацию о них в консоль")
  public void searchMostCostlyAndTattyPreparatoryCourses(){

    programmingCourcesCatalog.searchMostCostlyAndTattyPreparatoryCourses();

  }

}
