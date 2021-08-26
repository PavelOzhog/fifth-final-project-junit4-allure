package ru.appline.framework.managers;

import ru.appline.framework.pages.DepositPage;
import ru.appline.framework.pages.HomePage;



public class PageManager {
    private static PageManager INSTANCE = null;

    private HomePage homePage;
    private DepositPage depositPage;


    private PageManager() {

    }


    public static PageManager getPageManager() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }



    public HomePage getHomePage() {
        if(homePage==null){
            homePage= new HomePage();
        }
        return homePage;
    }

    public DepositPage getDepositPage(){
        if(depositPage==null){
            depositPage= new DepositPage();
        }
        return depositPage;
    }



}
