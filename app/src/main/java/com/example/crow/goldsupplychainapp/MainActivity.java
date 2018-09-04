package com.example.crow.goldsupplychainapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private Context mContext;

    private String mEmail;
    private String mNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        mEmail = intent.getStringExtra(LoginActivity.INTENT_EMAIL);
        mNickname = intent.getStringExtra(LoginActivity.INTENT_NICK);

        Button logouy_button = findViewById(R.id.logout_button);
        logouy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        prepare();
    }

    //Prepare data for show
    private void prepare(){
        TextView nickname_textview = (TextView) findViewById(R.id.nickname_textView);
        nickname_textview.setText("Welcome,"+mNickname);
    }
}
