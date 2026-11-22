package com.playwright.framework.support;

import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.lang.reflect.Method;

public class QuarantineListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        Annotations.Quarantine quarantine = findQuarantine(method, result.getTestClass().getRealClass());

        if (quarantine == null) {
            return;
        }

        boolean runQuarantined = Boolean.parseBoolean(
                System.getProperty("runQuarantined", "false")
        );

        String ticket = quarantine.ticket().isBlank() ? "N/A" : quarantine.ticket();
        String owner = quarantine.owner().isBlank() ? "unassigned" : quarantine.owner();
        String message = String.format(
                "QUARANTINED [ticket=%s, owner=%s]: %s",
                ticket,
                owner,
                quarantine.reason()
        );

        Allure.label("quarantine", "true");
        Allure.label("quarantine_ticket", ticket);
        Allure.label("quarantine_owner", owner);
        Allure.parameter("Quarantine Reason", quarantine.reason());
        Allure.description(message);

        if (!runQuarantined) {
            throw new SkipException(message);
        }
    }

    private Annotations.Quarantine findQuarantine(Method method, Class<?> testClass) {
        if (method != null && method.isAnnotationPresent(Annotations.Quarantine.class)) {
            return method.getAnnotation(Annotations.Quarantine.class);
        }
        if (testClass != null && testClass.isAnnotationPresent(Annotations.Quarantine.class)) {
            return testClass.getAnnotation(Annotations.Quarantine.class);
        }
        return null;
    }
}
