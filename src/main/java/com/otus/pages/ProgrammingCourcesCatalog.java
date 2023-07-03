package com.otus.pages;

import com.google.inject.Inject;
import com.otus.annotations.Path;
import com.otus.annotations.UrlTemplate;
import com.otus.annotations.Urls;
import di.GuiceScooped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.*;

@Path("/catalog/courses?categories=programming")
@Urls(
    @UrlTemplate(name = "category", value = "?categories=$1")
)
public class ProgrammingCourcesCatalog extends AbsBasePage<ProgrammingCourcesCatalog> {

  @Inject
  public ProgrammingCourcesCatalog(GuiceScooped guiceScooped) {
    super(guiceScooped);
  }

  public void searchMostCostlyAndTattyPreparatoryCourses(){

    List<WebElement> listElement;
    List<String> urlCourses = new ArrayList<>();
    Map<String, Integer> coursesNameAndPrice = new HashMap<>();

    JavascriptExecutor js = (JavascriptExecutor) driver;

    try {
      while (true) {
        WebElement button = driver.findElement(By.xpath("//button[contains(text(), 'Показать еще ')]"));

        js.executeScript("arguments[0].scrollIntoView();", button);

        button.click();
      }
    } catch (Exception ignored){

    }

    listElement = driver.findElements(By.xpath("//p[contains(text(), 'Подготовительный')]//ancestor::a"));

    listElement.stream().forEach(s -> {
      urlCourses.add(s.getAttribute("href"));
    });

    urlCourses.stream().forEach((c) -> {
      try {
        Document doc = Jsoup.connect(c).get();
        Elements priceCourses = doc.selectXpath("//div[contains(text(), 'Стоимость')]/following::div[1]");
        Elements nameCourses = doc.selectXpath("//div[contains(text(), 'Подготовительный')]/following::h3[1]");

        String price = priceCourses.text().replaceAll("\\D+", "");

        coursesNameAndPrice.put(nameCourses.text(),Integer.parseInt(price));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    coursesNameAndPrice.entrySet().stream()
        .filter((p) -> p.getValue() != null)
        .max(Map.Entry.comparingByValue()).ifPresent(System.out::println);

    coursesNameAndPrice.entrySet().stream()
        .filter((p) -> p.getValue() != null)
        .min(Map.Entry.comparingByValue()).ifPresent(System.out::println);

  }
}
