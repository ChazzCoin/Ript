package com.ript.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ript.wallet.models.account_info;
import com.ript.wallet.utils.rAccountHelper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainWallet extends AppCompatActivity {

    HashMap<String, String> keys = new HashMap<>();
    WebSocketClient webSocketClient;
    final String TAG = "RIPPLE";
    private Intent intent;
    List<String> addresses = new ArrayList<>();

    public account_info rootAccount;
    public boolean hasSecret;

    public Gson gson = new Gson();
    public JSONObject jsonObject;
    public JSONObject resultObject;
    public JSONObject accountObject;

    @Bind(R.id.dName) TextView dName;
    @Bind(R.id.dCurrency) TextView dCurrency;
    @Bind(R.id.dBalance) TextView dBalance;
    @Bind(R.id.dInfoOne) TextView dInfoOne;
    @Bind(R.id.dInfoTwo) TextView dInfoTwo;

    @Bind(R.id.btnSendMessage) Button btnSendMessage;

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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        addresses.add(keys.get("address"));
        initWebSocket();
    }

    private void checkJsonObject(String mess) throws JSONException {
        try {
            jsonObject = new JSONObject(mess);
            if (jsonObject.has(rAccountHelper.RESULT)){
                resultObject = new JSONObject(jsonObject.getString(rAccountHelper.RESULT));
                if (resultObject.has(rAccountHelper.ACCOUNT_DATA)){
                    accountObject = new JSONObject(resultObject.getString(rAccountHelper.ACCOUNT_DATA));
                    rootAccount = gson.fromJson(String.valueOf(accountObject), account_info.class);
                    setupAccountInfoDisplay();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupAccountInfoDisplay() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainWallet.this, "Receiving...", Toast.LENGTH_LONG).show();
                dCurrency.setText("XRP");
                dBalance.setText(rootAccount.getBalance());
                dInfoOne.setText(rootAccount.getIndex());
                dName.setText(rootAccount.getAccount());
            }
        });
    }

    @OnClick(R.id.btnSendMessage)
    public void setBtnSendMessage(){
        sendMessage(rAccountHelper.sendAccount_Info_Message(keys.get("address")));
    }

    /**
     *  WebSocket Tools
     */

    // ON/OFF
    private void toggleSocket(boolean enabled) {
        if (enabled){
            webSocketClient.connect();
        } else {
            webSocketClient.close();
        }
    }

    // Send Request
    private void sendMessage(String message) {
        webSocketClient.send(message);
    }

    public void onSocketMessage(String message) {
        runOnUiThread(() -> Toast.makeText(MainWallet.this, message, Toast.LENGTH_LONG).show());
    }

    //WebSocket Handling
    public void initWebSocket(){


        webSocketClient = new WebSocketClient(rAccountHelper.getRippleS2()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d(TAG, "ON OPEN : " + handshakedata);
                onSocketMessage("Socket Open");
                sendMessage(String.valueOf(rAccountHelper.subscribeToAccountInfo(addresses)));
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, "RECEIVING MESSAGE!! : " + message);
                try {
                    checkJsonObject(message);
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
        webSocketClient.setSocketFactory(factory);
        toggleSocket(true);
    }
}
