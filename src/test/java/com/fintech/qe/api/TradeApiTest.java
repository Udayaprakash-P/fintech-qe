package com.fintech.qe.api;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class TradeApiTest {

    private Playwright playwright;
    private APIRequestContext api;

    @BeforeClass
    void setup() {
        playwright = Playwright.create();
        api = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://api.example.com")
                        .setExtraHTTPHeaders(
                                java.util.Map.of("Authorization", "Bearer valid-token")
                        )
        );
    }

    @AfterClass
    void teardown() {
        api.dispose();
        playwright.close();
    }

    @Test
    public void authorizedUserCanPlaceTrade() {
        APIResponse response = api.post(
                "/trades",
                RequestOptions.create()
                        .setData("""
                                {
                                  "asset": "AAPL",
                                  "quantity": 10,
                                  "action": "BUY"
                                }
                                """)
        );

        assertEquals(response.status(), 201);
        assertTrue(response.text().contains("CONFIRMED"));
    }
}

