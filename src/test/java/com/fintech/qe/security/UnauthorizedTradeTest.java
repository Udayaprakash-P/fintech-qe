package com.fintech.qe.security;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UnauthorizedTradeTest {

    @Test
    public void complianceUserCannotPlaceTrade() {

        try (Playwright playwright = Playwright.create()) {

            APIRequestContext api = playwright.request().newContext(
                    new APIRequest.NewContextOptions()
                            .setBaseURL("https://api.example.com")
                            .setExtraHTTPHeaders(
                                    java.util.Map.of("Authorization", "Bearer compliance-token")
                            )
            );

            APIResponse response = api.post(
                    "/trades",
                    RequestOptions.create()
                            .setData("""
                                    {
                                      "asset": "AAPL",
                                      "quantity": 5,
                                      "action": "BUY"
                                    }
                                    """)
            );

            assertEquals(response.status(), 403,
                    "Unauthorized user must not be allowed to trade");
        }
    }
}
