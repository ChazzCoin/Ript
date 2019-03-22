package com.ript.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ript.wallet.utils.rHelper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.net.ssl.SSLSocketFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;


public class MainWallet extends AppCompatActivity {

    HashMap<String, String> keys = new HashMap<>();
    WebSocketClient webSocketClient;
    final String TAG = "GDAX";
    private Intent intent;

    public boolean hasSecret;

    String fName;
    String fCurrency;
    String fBalance;
    String fInfoOne;
    String fInfoTwo;

    private final String r_ADDRESS = "rLTncPPPNaXnhpmgHm1SVZyTwsy8zjn3CX";
    public String url;
    public JSONObject jsonObject;
    public OkHttpClient client;

    HashMap<String, String> map = new HashMap<>();

    @Bind(R.id.dName) TextView dName;
    @Bind(R.id.dCurrency) TextView dCurrency;
    @Bind(R.id.dBalance) TextView dBalance;
    @Bind(R.id.dInfoOne) TextView dInfoOne;
    @Bind(R.id.dInfoTwo) TextView dInfoTwo;


    public void grabUserAddress(){

        url = rHelper.url_ACCOUNT + r_ADDRESS;

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        //TODO: CHANGE FRAGMENTS HERE
                        return true;
                    case R.id.navigation_dashboard:
                        //TODO: CHANGE FRAGMENTS HERE
                        return true;
                    case R.id.navigation_notifications:
                        //TODO: CHANGE FRAGMENTS HERE
                        return true;
                }
                return false;
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshSocket: {
                webSocketClient.close();
                initWebSocket();
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main);
        ButterKnife.bind(this);

        intent = getIntent();

        if (intent.hasExtra("address")){
            keys.put("address", intent.getStringExtra("address"));
            if (!intent.hasExtra("secret") || intent.getStringExtra("secret").equals("")){
                hasSecret = false;
            } else {
                keys.put("secret", intent.getStringExtra("secret"));
                hasSecret = true;
            }
        }

        client = new OkHttpClient();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initWebSocket();
    }





    private void setupDisplay(String mess) throws JSONException {

        try {
            jsonObject = new JSONObject(mess);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject.isNull("status")){
            fBalance = "null";
            fCurrency = "null";
            fInfoOne = "null";
            fInfoTwo = "null";
            fName = "null";
        } else {
            JSONObject deeper = new JSONObject(jsonObject.getString("result"));
            JSONObject deepest = new JSONObject(deeper.getString("account_data"));
            fName = deepest.getString("Account");
            fBalance = deepest.getString("Balance");
            //fInfoOne = jsonObject.getString("validated");
        }

        runOnUiThread(new Runnable(){
            public void run() {
                Toast.makeText(MainWallet.this, "Receiving...", Toast.LENGTH_LONG).show();
                dBalance.setText(fBalance);
                dCurrency.setText("XRP");
                dInfoOne.setText(fInfoOne);
                dName.setText(fName);
            }
        });
    }

    private JSONObject createJsonObject(){


        JSONObject subscribe = new JSONObject();
        try {
            subscribe.put("id", "2");
            subscribe.put("command", "account_info");
            subscribe.put("account", keys.get("address"));
            subscribe.put("strict", "true");
            subscribe.put("ledger_index", "current");
            subscribe.put("queue", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return subscribe;
    }


    private void ripple() {
        webSocketClient.send(String.valueOf(createJsonObject()));
    }

    public void onSocketMessage(String message) {
        runOnUiThread(new Runnable(){
            public void run() {
                Toast.makeText(MainWallet.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initWebSocket(){

        URI gdaxURI = null;
        //wss://ws-feed.gdax.com
        //wss://s2.ripple.com:443

        try {
            gdaxURI = new URI("wss://s2.ripple.com:443");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        webSocketClient = new WebSocketClient(gdaxURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d(TAG, "ON OPEN : " + handshakedata);
                onSocketMessage("Socket Open");
                ripple();
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, "MESSAGE!! : " + message);
                try {
                    setupDisplay(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d(TAG, "ON CLOSE : " + reason);
                onSocketMessage("Socket Closed");
            }

            @Override
            public void onError(Exception ex) {
                Log.d(TAG, "ON ERROR : " + ex.getMessage());
            }
        };

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            webSocketClient.setSocket(factory.createSocket());
        } catch (IOException e) {
            e.printStackTrace();
        }
        webSocketClient.connect();
    }
}
