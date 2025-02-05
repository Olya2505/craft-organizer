package com.craftorganizer.projectservice.swagger;

public class HTMLResponseMessages {

    public static final String HTTP_200 = "Successful request gets this response code and returns requested data. " +
            "Example data can be seen below";
    public static final String HTTP_201 = "Successful request saves posted data and returns that data with this " +
            "response code. Example data can be seen below";
    public static final String HTTP_204 = "Successful request deletes requested data and returns this " +
            "response code";
    public static final String HTTP_400 = "Unsuccessful request responds with this code. Passed data has errors - " +
            "fields have incorrect values or there are missing fields";
    public static final String HTTP_401 = "Unsuccessful request responds with this code. The request requires user " +
            "authentication";
    public static final String HTTP_403 = "Unsuccessful request responds with this code. Accessing the resource you " +
            "were trying to reach is forbidden";
    public static final String HTTP_404 = "Unsuccessful request responds with this code. Requested resource has not " +
            "been found";
    public static final String HTTP_500 = "Unsuccessful request responds with this code. Internal server error. " +
            "Check the response headers for information";
    public static final String HTTP_503 = "Service is temporarily unavailable. The server is currently unable to " +
            "handle the request due to maintenance or overload. Retry after some time or check the response headers " +
            "for more details.";
}
