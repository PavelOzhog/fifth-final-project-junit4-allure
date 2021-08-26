package ru.appline.framework.ru.appline.tests;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import ru.appline.framework.managers.DriverManager;
import ru.appline.framework.managers.InitManager;
import ru.appline.framework.managers.PageManager;
import ru.appline.framework.managers.TestPropManager;

import static ru.appline.framework.utils.PropConst.BASE_URL;

public class BaseTests {

        private final TestPropManager propManager = TestPropManager.getTestPropManager();
        protected PageManager app = PageManager.getPageManager();

        @BeforeClass
        public static void testBeforeClass() {
            InitManager.initFramework();
        }


        @Before
        public  void before(){
            DriverManager.getDriverManager().getDriver().get(propManager.getProperty(BASE_URL));
        }

        @AfterClass
        public static void afterClass() {
            InitManager.quitFramework();

        }

}
