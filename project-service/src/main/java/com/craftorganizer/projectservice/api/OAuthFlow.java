package com.craftorganizer.projectservice.api;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.util.Scanner;

public class OAuthFlow {

    public static void main(String[] args) throws Exception {
        final String consumerKey = System.getenv("CONSUMER_KEY");
        final String consumerSecret = System.getenv("CONSUMER_SECRET");
        final String callbackUrl = System.getenv("CALLBACK_URL");

        OAuth10aService service = new ServiceBuilder(consumerKey)
                .apiSecret(consumerSecret)
                .callback(callbackUrl)
                .build(new RavelryApi());

        OAuth1RequestToken requestToken = service.getRequestToken();
        System.out.println("Request Token: " + requestToken.getToken());
        System.out.println("Request Token Secret: " + requestToken.getTokenSecret());

        String authorizationUrl = service.getAuthorizationUrl(requestToken);
        System.out.println("Go to the following URL and authorize the application:");
        System.out.println(authorizationUrl);

        System.out.print("Enter the OAuth Verifier provided by Ravelry: ");
        Scanner in = new Scanner(System.in);
        String oauthVerifier = in.nextLine();

        OAuth1AccessToken accessToken = service.getAccessToken(requestToken, oauthVerifier);
        System.out.println("Access Token: " + accessToken.getToken());
        System.out.println("Access Token Secret: " + accessToken.getTokenSecret());
    }
}

