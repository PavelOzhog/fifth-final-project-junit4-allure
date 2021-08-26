package ru.appline.framework.ru.appline.tests;

import org.junit.Test;

public class FifthTest extends BaseTests {


    @Test
    public void testCase() {
        app.getHomePage()
                .chooseMenu("Вклады")
                .chooseCurrency("Рубли")
                .fillContrib("300000")
                .selectIterm("6 месяцев")
                .fillMothReplin("50000")
                .selectCheckboxes("Ежемесячная капитализация")
                .checkDataContrib();
    }
}