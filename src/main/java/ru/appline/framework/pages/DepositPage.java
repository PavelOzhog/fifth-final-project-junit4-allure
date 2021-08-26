package ru.appline.framework.pages;

import io.qameta.allure.Step;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DepositPage extends BasePage {


    @FindBy(xpath = "//a[contains(text(), 'Как открыть')]")
    WebElement page;

    @FindBy(xpath = "//div[@class='calculator__currency-content']//span")
    List<WebElement> currencies;

    @FindBy(xpath = "//select[@class='calculator__slide-input js-slide-value']")
    Select selectIterm;

    @FindBy(xpath = "//div[@class='calculator__slide-section']//label[@class='calculator__slide-input-label']")
    List<WebElement> listFields;

    @FindBy(xpath = "//div[@class='dep-graph']//span[@class='js-calc-amount']")
    WebElement nowHaveField;

    @FindBy(xpath = "//span[@class='calculator__check-text']")
    List<WebElement> checkBoxes;

    @FindBy(xpath = "//input[@name='amount']")
    WebElement fieldContrib;

    @FindBy(xpath = "//input[@name='replenish']")
    WebElement monthReplin;

    @FindBy(xpath = "//span[@class='js-calc-replenish']")
    WebElement replenForPeriod;

    @FindBy(xpath = "//span[@class='js-calc-earned']")
    WebElement earnedPercent;

    @FindBy(xpath = "//span[contains(text(),'Ежемесячная капитализация')]/../..//div[@class='jq-checkbox calculator__check checked']")
    WebElement checkBoxValue;

    @FindBy(xpath = "//span[@class='js-calc-replenish']")
    WebElement replenishPercent;

    @FindBy(xpath = "//span[@class='js-calc-result']")
    WebElement withdraw;


    String termXpath = "//div[@class='jq-selectbox__select-text']";
    String iternVariantsXpath = "//div[@class='jq-selectbox__dropdown']//li";
    String summOfContribXpath = "//input[@name='amount']";
    String selectXpath = "//select[@class='calculator__slide-input js-slide-value']";


    /**
     * Метод для прокрутки элемента (метод не используется)
     */

    public DepositPage skroll() {
        scrollToElementJs(page);
        return this;
    }


    /**
     * Метод для выбора валюты
     *
     * @param typeCurrency наименование валюты, которую хотим выбрать
     */
    @Step("Выбираем валюту {typeCurrency} на странице со вкладами")
    public DepositPage chooseCurrency(String typeCurrency) {
        for (WebElement currency : currencies) {
            if (currency.getText().trim().equalsIgnoreCase(typeCurrency)) {
                wait.until(ExpectedConditions.elementToBeClickable(currency)).click();
                return pageManager.getDepositPage();

            }
        }
        Assert.fail("Меню '" + typeCurrency + "' не было найдено на данной странице!");
        return pageManager.getDepositPage();
    }


    /**
     * Метод для заполнения поля "Первоначальный взнос"
     *
     * @param value значение первоначального взноса
     * @return объек POM текущей страницы
     */
    @Step("Вводим сумму вклада {value}")
    public DepositPage fillContrib(String value) {
        wait.until(ExpectedConditions.elementToBeClickable(fieldContrib));
        fill(fieldContrib, value);
        wait.until(myCustomAttributeToBe(fieldContrib, "value", value));
//Assert.assertEquals("поле ежемесячный платеж  заполнено неверно",value,changeString(fieldContrib.getAttribute("innerText")) );
        return this;
    }


    /**
     * Метод для заполнения поля ежемесячного платежа
     *
     * @param value значение для заполнения поля
     * @return объек POM текущей страницы
     */
    @Step("Вводим сумму ежемесячного платежа {value}")
    public DepositPage fillMothReplin(String value) {
        wait.until(ExpectedConditions.elementToBeClickable(monthReplin));
        fill(monthReplin, value);

        try{
            wait.until(myCustomAttributeToBe(monthReplin, "value", value));
        }catch (TimeoutException e){
            Assert.fail();
        }

        return this;
    }


    /**
     * Метод для заполнения чек бокса
     *
     * @param nameCheckbox имя чекбокса
     * @return объек POM текущей страницы
     */
    @Step("Ставим галочку на поле {nameCheckbox}")
    public DepositPage selectCheckboxes(String nameCheckbox) {
        WebElement checkBox = checkBoxes.stream().filter(element -> element.getText().trim()
                        .equalsIgnoreCase(nameCheckbox)).findFirst()
                .orElseThrow(() -> new AssertionFailedError("данного чек-бокса не найдено"));
        checkBox.click();
        wait.until(ExpectedConditions.visibilityOf(checkBoxValue));
        return this;
    }


    /**
     * Метод для проверки заполненных полей
     *
     * @return объек POM текущей страницы
     */
    @Step("Проверяем заполненные данные")
    public DepositPage checkDataContrib() {

        checkValueByText(earnedPercent, "12243,26", "innerText");
        checkValueByText(replenishPercent, "250000", "innerText");
        checkValueByText(withdraw, "562243,26", "innerText");
        return this;
    }


    /**
     * Метод для выбора срока вклада
     *
     * @param value срок вклада из выпадающего списка
     * @return объек POM текущей страницы
     */
    public DepositPage selectIterm(String value) {
        Select select = new Select(driverManager.getDriver().findElement(By.xpath(selectXpath)));
        select.selectByVisibleText(value);
        return this;
    }

}


//            case "На срок":
//                for (WebElement field : listFields) {
//                    if (field.getText().trim().equalsIgnoreCase(typeFinField)) {
//                        wait.until(ExpectedConditions.elementToBeClickable(field));
//                        field = field.findElement(By.xpath(termXpath));
//                        field.click();
//                        List<WebElement> itemVariants = driverManager.getDriver().findElements(By.xpath(iternVariantsXpath));
//                        for (WebElement itemVariant : itemVariants) {
//                            if (itemVariant.getAttribute("innerText").trim().equalsIgnoreCase(value)) {
//                                wait.until(ExpectedConditions.elementToBeClickable(itemVariant));
//                                itemVariant.click();
//                            }
//                        }
//                    }
//                }
//                break;


//    public DepositPage fillFinanceField(String typeFinField, String value) {
//        for (WebElement field : listFields) {
//            System.out.println("---------");
//            System.out.println(field.getText());
//            if (field.getText().trim().equalsIgnoreCase(typeFinField)) {
//                wait.until(ExpectedConditions.elementToBeClickable(field));
//                field = field.findElement(By.xpath("//input[@name='amount']"));
//                fill(field, value);
//                //wait.until(MyAttributeToBe(nowHaveField,value, "value"));
//                Assert.assertEquals("Значения полей не совпадают", value, changeString(nowHaveField.getAttribute("innerText")));
//                return pageManager.getDepositPage();
//            }
//        }
//        Assert.fail("Меню '" + typeFinField + "' не было найдено на данной странице!");
//        return pageManager.getDepositPage();
//
//    }

//объявить два поля и заполнить одинаково
//    public DepositPage fillDataFields(String typeFinField, String value) {
//        switch (typeFinField) {
//            case "Сумма вклада":
//
//            case "Ежемесячное пополнение":
//                for (WebElement field : listFields) {
//
//                    //сделать отдельный метод
//                    if (field.getText().trim().equalsIgnoreCase(typeFinField)) {
//                        wait.until(ExpectedConditions.elementToBeClickable(field));
//                        field = field.findElement(By.xpath("//input[@name='replenish']"));
//                        fill(field, value);
//                        //wait.until(MyTextToBe(nowHaveField, value));
//                        // Assert.assertEquals("Значения полей не совпадают", value, changeString(nowHaveField.getAttribute("innerText")));
//                    }
//                }
//                break;
//            default:
//                Assert.fail(("Поле '" + typeFinField + "' не было найдено на странице"));
//        }
//        return this;
//    }