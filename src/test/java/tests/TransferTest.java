package tests;

import com.microsoft.playwright.*;
import com.playwright.framework.support.Annotations.Quarantine;
import io.qameta.allure.*;
import org.junit.jupiter.api.Order;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class TransferTest extends BaseTest{




    @Test(priority = 1)
    @Story("Checking balances before Transfer of funds")
    @Description("Checking balances before Transfer of funds")
    @Severity(SeverityLevel.NORMAL)
    void checkAccountBalanceTest() {
          Allure.step("TC-POS-11 - Verify that  Overview page is visible", () -> {
            overviewPage().navigate();
            Assert.assertTrue(overviewPage().pageIsVisible(), "Accounts Overview is not visible");
        });

        Allure.step("TC-POS-13 - Check Account Balances for form Account", () -> {
            String fromAccount = node.get("transferAccountData").get(0).get("fromAccountId").asText();
            BigDecimal fromBalance = overviewPage().getCellValue(fromAccount);
            Assert.assertTrue(fromBalance.toPlainString().equals(node.get("transferAccountData").get(0).get("fromBalance").asText()), "Account Balances are not equal" );
        });

        Allure.step("TC-POS-14 - Check Account Balances for toAccount", () -> {
            String toAccount = node.get("transferAccountData").get(0).get("toAccountId").asText();
            BigDecimal toBalance = overviewPage().getCellValue(toAccount);
            Assert.assertTrue(toBalance.toPlainString().equals(node.get("transferAccountData").get(0).get("toBalance").asText()), "Account Balances are not equal" );
        });


    }
    @Test(priority = 2)
    @Story("Successful Transfer of funds")
    @Description("Verify that a user can transfer money between accounts.")
    @Severity(SeverityLevel.NORMAL)

    void transferAmountTest() {
        Allure.step("TC-POS-21 - Verify that Transfer page is visible", () -> {
            transferPage().navigate();
            System.out.println("Transfer page is visible: " + page.url());
            Assert.assertTrue(transferPage().pageIsVisible(), "Transfer Page is not visible");
        });

        Allure.step("TC-POS-22 - Transfer Amount ", () -> {
            transferPage().fillAmountInput(node.get("transferAccountData").get(0).get("transferAmount").asText());
            transferPage().setFromAccountIdSelect(node.get("transferAccountData").get(0).get("fromAccountId").asText());
            transferPage().setToAccountIdSelect(node.get("transferAccountData").get(0).get("toAccountId").asText());
            transferPage().clickTransferBtn();
            Assert.assertTrue(transferPage().isConfirmationMessageVisible(), "Transfer of funds not successful.");
        });

        Allure.step("TC-POS-11 - Verify that  Overview page is visible", () -> {
            overviewPage().navigate();
            Assert.assertTrue(overviewPage().pageIsVisible(), "Accounts Overview is not visible");
        });

        Allure.step("TC-POS-23 - Check Account Balances for toAccount", () -> {
            String toAccount = node.get("transferAccountData").get(0).get("toAccountId").asText();
            BigDecimal toBalance = overviewPage().getCellValue(toAccount);
            Assert.assertTrue(toBalance.toPlainString().equals(node.get("transferAccountData").get(0).get("toAfterTrBalance").asText()), "Account Balances are not equal" );
        });

        Allure.step("TC-POS-24 - Check Account Balances for forAccount", () -> {
            String fromAccount = node.get("transferAccountData").get(0).get("fromAccountId").asText();
            BigDecimal fromBalance = overviewPage().getCellValue(fromAccount);
            Assert.assertTrue(fromBalance.toPlainString().equals(node.get("transferAccountData").get(0).get("fromAfterTrBalance").asText()), "Account Balances are not equal" );
        });
    }

    @Test
    @Quarantine(
        reason = "Intermittent checkout modal timeout in CI",
                ticket = "TC-POS-99",
                owner = "sdet-team"
            )
    public void myFlakyTest(){
        Allure.step("TC-POS-99 - My Flaky Test", () -> {
           // String fromAccount = node.get("transferAccountData").get(0).get("fromAccountId").asText();
           // BigDecimal fromBalance = overviewPage().getCellValue(fromAccount);
            Assert.assertTrue(false, "Flaky Test ran" );
        });
    }

}
