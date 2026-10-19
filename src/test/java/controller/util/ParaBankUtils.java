package controller.util;

import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ParaBankUtils {

    public static final String DASHBOARD = "Dashboard";
    public static final String TIMESHEET = "Timesheets";
    public static final String URL = "https://opensource-demo.orangehrmlive.com/";
    public static final String PARABANK_URL = "https://parabank.parasoft.com/parabank/index.htm";
    public static final String PARA_USER = "john";
    public static final String PARA_PWD = "demo";
    public static final String LOCAL_URL = "http://127.0.0.1:8081/parabank/index.htm";
    public static final String OVERVIEW_URL = "http://127.0.0.1:8081/parabank/overview.htm";
    private ParaBankUtils(){}


    public static JsonNode readJsonTree(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON tree from: " + filePath, e);
        }
    }
}
