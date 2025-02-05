package com.craftorganizer.projectservice.api;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.ParameterList;

public class RavelryApi extends DefaultApi10a {

    @Override
    public String getRequestTokenEndpoint() {
        return "https://www.ravelry.com/oauth/request_token";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://www.ravelry.com/oauth/access_token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://www.ravelry.com/oauth/authorize";
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        ParameterList parameters = new ParameterList();
        parameters.add("oauth_token", requestToken.getToken());
        return parameters.appendTo(getAuthorizationBaseUrl());
    }
}
