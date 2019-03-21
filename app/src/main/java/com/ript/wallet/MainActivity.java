package com.ript.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ript.wallet.utils.rHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private final String r_ADDRESS = "rLTncPPPNaXnhpmgHm1SVZyTwsy8zjn3CX";
    public String url;
    public String userAddress;
    public JSONArray Jarray;
    public JSONObject jsonObject;
    private TextView output;
    public OkHttpClient client;

    HashMap<String, String> map = new HashMap<>();

    @Bind(R.id.rAddress) TextView rAddress;
    @Bind(R.id.rAccount) TextView rAccount;
    @Bind(R.id.rParent) TextView rParent;
    @Bind(R.id.rInitial_balance) TextView rInitial_balance;
    @Bind(R.id.rLedger_index) TextView rLedger_index;
    @Bind(R.id.rInception) TextView rInception;
    @Bind(R.id.rTx_hash) TextView rTx_hash;

    @Bind(R.id.getAccount) Button btnGetAccount;
    @Bind(R.id.btnLoadDisplay) Button btnLoadDisplay;

    @Bind(R.id.enterAddress) EditText enterAddress;

    public void setupDisplay(){
        rAddress.setText(r_ADDRESS);

        rAccount.setText(map.get("account"));
        rParent.setText(map.get("parent"));
        rInitial_balance.setText(map.get("initial_balance"));
        rLedger_index.setText(map.get("ledger_index"));
        rInception.setText(map.get("inception"));
        rTx_hash.setText(map.get("tx_hash"));
    }

    public void grabUserAddress(){

        url = rHelper.url_ACCOUNT + r_ADDRESS;

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        client = new OkHttpClient();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @OnClick(R.id.btnLoadDisplay)
    public void loadDisplay(){
        //startActivity(new Intent(MainActivity.this, gDaxFeeder.class));

        start();
        //setupDisplay();
    }


    @OnClick(R.id.getAccount)
    public void getAccount(){
        grabUserAddress();
        httpGrabAccount();
    }


    public void httpGrabAccount(){
        try {
            rHelper.createClient().newCall(rHelper.createRequest(url))
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(final Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            String res = response.body().string();
                            try {
                                jsonObject = new JSONObject(res);
                                JSONObject deep  = new JSONObject(jsonObject.getString("account_data"));
                                map.put("account", deep.getString("account"));
                                map.put("parent", deep.getString("parent"));
                                map.put("initial_balance", deep.getString("initial_balance"));
                                map.put("inception", deep.getString("inception"));
                                map.put("ledger_index", deep.getString("ledger_index"));
                                map.put("tx_hash", deep.getString("tx_hash"));
                                runOnUiThread(new Runnable(){
                                    public void run() {
                                        setupDisplay();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void start() {
        Request request = new Request.Builder().url("wss://ws-feed.gdax.com").build();
        gdaxWebSocketListener listener = new gdaxWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }
    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rAddress.setText(output.getText().toString() + "\n\n" + txt);
            }
        });
    }





    private final class gdaxWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;



        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("");
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            rAddress.setText("Receiving : " + text);
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            rAddress.setText("Closing : " + code + " / " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            rAddress.setText("Error : " + t.getMessage());
        }
    }

}
