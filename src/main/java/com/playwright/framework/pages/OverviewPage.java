package com.playwright.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.playwright.framework.util.ParaBankUtils;

import java.math.BigDecimal;

public class OverviewPage {
    private Page page;
    private Locator accountsTable;
    private Locator accountLinks;
    private Locator cellValue;

    public OverviewPage(Page page) {
        this.page = page;
        this.accountsTable = page.locator("#accountTable");
        this.accountLinks = page.getByRole( AriaRole.LINK, new Page.GetByRoleOptions().setName("Accounts Overview") );
    }

    public void navigate() {
        page.navigate(ParaBankUtils.OVERVIEW_URL);
    }

    public boolean pageIsVisible(){
        return this.page.url().equals(ParaBankUtils.OVERVIEW_URL);
    }

    public BigDecimal getCellValue(String account){
        Locator balanceCell = page.locator("#accountTable tbody tr")
                .filter(new Locator.FilterOptions().setHasText(account))
                .locator("td")
                .nth(1); //
        balanceCell.waitFor();

        String rawBalance = balanceCell.innerText().replace("$", "").replace(",", "").trim();
        return new BigDecimal(rawBalance);
    }
}
