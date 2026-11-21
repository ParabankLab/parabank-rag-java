package com.playwright.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.playwright.framework.util.ParaBankUtils;

public class AdminPage {
    private Page page;
    private Locator initDB;
    private Locator cleanDB;

    public AdminPage(Page page) {
        this.page = page;
        this.cleanDB =page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Clean"));
        this.initDB = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Initialize"));
    }

    public void cleanAndInitDB() {
        cleanDB.click();
        initDB.click();
    }


    public void navigate(){
        page.navigate(ParaBankUtils.ADMIN_URL);
    }

}
