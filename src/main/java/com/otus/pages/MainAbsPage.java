package com.otus.pages;

import com.google.inject.Inject;
import com.otus.annotations.Path;
import com.otus.data.Month;
import di.GuiceScooped;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Path("/")
public class MainAbsPage extends AbsBasePage<MainAbsPage> {

  @Inject
  public MainAbsPage(GuiceScooped guiceScooped) {
    super(guiceScooped);
  }

  @FindBy(css = ".lessons > a")
  private List<WebElement> listElement;


  @FindBy(css = "[class='lessons'] .lessons__new-item-start, [class='lessons'] .lessons__new-item-bottom > .lessons__new-item-time")
  private List<WebElement> lessonsItems;

  List<WebElement> listCartCourses = new ArrayList<>();

  public void checkClickPopularCourses() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    List<WebElement> webElements = driver.findElements(By
        .xpath("//div[text()='Популярные курсы']/following-sibling::div/a[contains(@href, 'lessons')]"));

    WebElement coursesCart = webElements.get(ThreadLocalRandom.current().nextInt(0, 2));

    js.executeScript("arguments[0].style.outline = '5px solid #ff0000';", coursesCart);

    js.executeScript("arguments[0].removeAttribute(\"style\")", coursesCart);

    js.executeScript("window.scrollBy(0,600)");

    coursesCart.click();

  }

  public void filterNameCourses(String coursesName) {

    String coursesTitle;
    JavascriptExecutor js = (JavascriptExecutor) driver;

    List<WebElement> list = listElement.stream()
        .filter(webElement -> webElement
            .findElement(By.cssSelector(".lessons__new-item-title"))
            .getText().equals(coursesName))
        .collect(Collectors.toList());

    if (!list.isEmpty()) {

      for (WebElement webElement : list) {

        js.executeScript("arguments[0].scrollIntoView();", webElement);

        webElement.click();

        coursesTitle = driver.findElement(By.cssSelector("h1")).getText();

        Assertions.assertEquals(coursesName, coursesTitle);

      }
    } else {

      Assertions.assertFalse(list.isEmpty());

    }
  }

  public void findCoursesInDateOrEarly (LocalDate dateCourses) {

    Map<WebElement, LocalDate> elementByDate = new HashMap<>();

      lessonsItems
          .stream()
          .filter((WebElement element) -> !element.getText().equals("О дате старта будет объявлено позже"))
          .map((WebElement element) -> {
                String dateStr = element.getText().replaceAll("^С ", "");
                Pattern pattern = Pattern.compile("(\\d+|^\\s+)\\s+([a-zA-Z]+).*");
                Matcher matcher = pattern.matcher(dateStr);

                if (matcher.find()) {
                  int dayInMonth = Integer.parseInt(matcher.group(1));
                  String month = String.valueOf(Month.getMonthNumber(matcher.group(2)));
                  int year = LocalDate.now().getYear();

                  dateStr = dayInMonth + " " + month + " " + year;

                  elementByDate.put(element, LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("d M yyyy")));
                }

                return elementByDate;
                }
          ).collect(Collectors.toList());

    elementByDate.entrySet().stream()
          .forEach(e -> {
            if(e.getValue().isAfter(dateCourses) || e.getValue().equals(dateCourses)){

              WebElement cartCoursesElement = e.getKey().findElement(By.xpath(".//ancestor::a"));
              String titleCourses = cartCoursesElement
                  .findElement(By.cssSelector(".lessons__new-item-title"))
                  .getText();

              System.out.printf("Название курса - %s, курс стартует с - %s \n", titleCourses, e.getValue());
            }
              }
              );
  }

}


