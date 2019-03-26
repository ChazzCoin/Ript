package com.ript.wallet.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Class designed to help connect and discount from the rippled api
 */
public class rAccountHelper {

    public static final String url_RIPPLE_s2 = "wss://s2.ripple.com:443";
    public static final String url_RIPPLE_s3 = "wss://s3.ripple.com:443";

    public static final String RESULT = "result";
    public static final String ACCOUNT_DATA = "account_data";

    public static final String ADDRESS = "address";
    public static final String SECRET = "secret";

    public static URI getRippleS2(){
        URI rippleUri = null;

        try {
            rippleUri = new URI(url_RIPPLE_s2);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return rippleUri;
    }

    public static URI getRippleS3(){
        URI rippleUri = null;

        try {
            rippleUri = new URI(url_RIPPLE_s3);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return rippleUri;
    }

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

    // SUBSCRIPTION FOR ACCOUNT_INFO
    public static JSONObject subscribeToAccountInfo(List<String> addresses){

        JSONObject account_subscription = new JSONObject();
        try {
            account_subscription.put("id", "subscribe to account_info");
            account_subscription.put("command", "subscribe");
            account_subscription.put("accounts", addresses);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account_subscription;
    }

    // MESSAGE FOR ACCOUNT_INFO
    public static String sendAccount_Info_Message(String address){

        JSONObject account_info = new JSONObject();
        try {
            account_info.put("id", "2");
            account_info.put("command", "account_info");
            account_info.put("account", address);
            account_info.put("strict", "true");
            account_info.put("ledger_index", "current");
            account_info.put("queue", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account_info.toString();
    }







}