package com.ript.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImportWalletActivity extends AppCompatActivity {

    private Intent intent;

    @Bind(R.id.editAddress) EditText editAddress;
    @Bind(R.id.editSecret) EditText editSecret;

    @Bind(R.id.btnImport) Button btnImport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet2);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private boolean grabKeys(){

        if (editAddress.getText().toString().isEmpty()){
            Toast.makeText(ImportWalletActivity.this, "Address can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }

//        else if (editSecret.getText().toString().isEmpty()){
//            Toast.makeText(ImportWalletActivity.this, "Only account info will be given without secret", Toast.LENGTH_LONG).show();
//            return true;
//        }

        else {
            return true;
        }
    }

    @OnClick(R.id.btnImport)
    public void btnImport(){

        if (grabKeys()){
            intent = new Intent(ImportWalletActivity.this, MainWallet.class);
            intent.putExtra("address", editAddress.getText().toString());
            //intent.putExtra("secret", editSecret.getText().toString());
            startActivity(intent);
        }
    }

}
