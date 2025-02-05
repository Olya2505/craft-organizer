package com.craftorganizer.projectservice.controller;

import com.craftorganizer.projectservice.swagger.DescriptionVariables;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "OAuth Controller", description = DescriptionVariables.OAUTH)
@RestController
public class OAuthController {


    @GetMapping("/oauth/callback")
    public String oauthCallback(@RequestParam("oauth_token") String oauthToken,
                                @RequestParam("oauth_verifier") String oauthVerifier) {
        return "Received oauth_token: " + oauthToken + " and oauth_verifier: " + oauthVerifier;
    }
}

