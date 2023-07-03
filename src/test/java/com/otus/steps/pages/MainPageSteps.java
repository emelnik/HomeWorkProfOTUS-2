package com.otus.steps.pages;

import com.google.inject.Inject;
import com.otus.pages.MainAbsPage;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Тогда;

import java.time.LocalDate;

public class MainPageSteps {

  @Inject
  public MainAbsPage mainPage;

  @Если("Открыть главную страницу")
  public void openMainPage() {
    mainPage.open();
  }

  @Тогда("Находим курс по названию - {string} и кликаем на плитку курса")
  public void filterCoursesName(String coursesName) {
    mainPage.filterNameCourses(coursesName);
  }

  @Тогда("Ищем курсы на дату {string} или позже и выводим в консоль информацию о найденных курсах")
  public void searchCoursesInDateOrLess(String dateCourses){
    mainPage.findCoursesInDateOrEarly(LocalDate.parse(dateCourses));
  }

}