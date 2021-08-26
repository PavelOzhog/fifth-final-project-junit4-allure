package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {
    @FindBy(xpath = "//div[@class='service']")
    List<WebElement> mainMenu;


    @Step("Выбираем {string}")
    public DepositPage chooseMenu(String string) {
        for (WebElement element : mainMenu) {
            List<WebElement> subMenues = element.findElements(By.xpath("//div[@class='service__title']"));
            for (WebElement subMenu : subMenues) {
                System.out.println(subMenu.getText());
                if (subMenu.getText().trim().equalsIgnoreCase(string)) {
                    System.out.println("-----");
                    System.out.println(subMenu.getText());
                    wait.until(ExpectedConditions.elementToBeClickable(subMenu)).click();
                    return pageManager.getDepositPage();
                }
            }

        }
        Assert.fail("Меню '" + string + "' не было найдено на стартовой странице!");
        return pageManager.getDepositPage();
    }

}



