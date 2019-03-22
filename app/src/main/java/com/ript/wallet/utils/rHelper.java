package com.ript.wallet.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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


//    public void httpGrabAccount(){
//        try {
//            rHelper.createClient().newCall(rHelper.createRequest(url))
//                    .enqueue(new Callback() {
//                        @Override
//                        public void onFailure(final Call call, IOException e) {
//
//                        }
//
//                        @Override
//                        public void onResponse(Call call, final Response response) throws IOException {
//                            String res = response.body().string();
//                            try {
//                                jsonObject = new JSONObject(res);
//                                JSONObject deep  = new JSONObject(jsonObject.getString("account_data"));
//                                map.put("account", deep.getString("account"));
//                                map.put("parent", deep.getString("parent"));
//                                map.put("initial_balance", deep.getString("initial_balance"));
//                                map.put("inception", deep.getString("inception"));
//                                map.put("ledger_index", deep.getString("ledger_index"));
//                                map.put("tx_hash", deep.getString("tx_hash"));
//                                runOnUiThread(new Runnable(){
//                                    public void run() {
//                                        setupDisplay();
//                                    }
//                                });
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//    private void subscribe() {
//        webSocketClient.send("{\n" +
//                "    \"type\": \"subscribe\",\n" +
//                "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-USD\"] }]\n" +
//                "}");
//    }


}