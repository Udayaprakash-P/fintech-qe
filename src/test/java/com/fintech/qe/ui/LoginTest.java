package com.fintech.qe.ui;

import com.microsoft.playwright.*;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class LoginTest {

    private Playwright playwright;
    private Browser browser;

    @BeforeClass
    void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterClass
    void teardown() {
        browser.close();
        playwright.close();
    }

    @Test
    public void customerCanLoginSuccessfully() {
        Page page = browser.newPage();
        page.navigate("https://example.com/login"); // simulated app

        page.fill("#username", "customer_user");
        page.fill("#password", "password123");
        page.click("#login");

        assertTrue(page.url().contains("dashboard"),
                "User should land on dashboard after login");
    }
}
