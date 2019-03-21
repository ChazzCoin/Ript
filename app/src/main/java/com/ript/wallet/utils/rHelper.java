package com.ript.wallet.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Class designed to help connect and discount from the rippled api
 */
public class rHelper {

    OkHttpClient client;
    public static final String url_ACCOUNT = "https://data.ripple.com/v2/accounts/";

    public static Request createRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    public static OkHttpClient createClient() throws IOException {
        OkHttpClient client = new OkHttpClient();
        return client;
    }



}