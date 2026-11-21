package com.playwright.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.playwright.framework.util.ParaBankUtils;

public class TransferPage {
    private Page page;
    private Locator amountInput;
    private Locator fromAccountIdSelect;
    private Locator toAccountIdSelect;
    private Locator transferBtn;
    private Locator transferConfirmationMessage;

    public TransferPage(Page page) {
        this.page = page;
        this.fromAccountIdSelect = page.locator("#fromAccountId");
        this.toAccountIdSelect = page.locator("#toAccountId");
        this.amountInput = page.locator("input[name='input']");
        this.transferBtn = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Transfer"));
        this.transferConfirmationMessage = page.locator("#showResult h1");
    }

    public void fillAmountInput(String amount) {
        this.amountInput.fill(amount);
    }

    public void setFromAccountIdSelect(String fromAccountId) {
        this.fromAccountIdSelect.selectOption(fromAccountId);
    }

    public void setToAccountIdSelect(String toAccountId) {
        this.toAccountIdSelect.selectOption(toAccountId);
    }

    public void clickTransferBtn() {
        this.transferBtn.click();
    }

    public void navigate(){
        page.navigate(ParaBankUtils.TRANSFER_URL);
    }

    public boolean pageIsVisible(){
        return this.page.url().equals(ParaBankUtils.TRANSFER_URL);
    }

    public boolean isConfirmationMessageVisible(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.transferConfirmationMessage.isVisible();
    }
    public String getConfirmationMessage(){
        return this.transferConfirmationMessage.innerText();
    }
}
