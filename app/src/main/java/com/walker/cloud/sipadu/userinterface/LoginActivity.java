package com.walker.cloud.sipadu.userinterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.walker.cloud.sipadu.R;
import com.walker.cloud.sipadu.asset.SessionManager;
import com.walker.cloud.sipadu.asset.Request;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private TextView message;
    private Button loginBtn;
    private EditText inputNIM, inputPassword;
    private LinearLayout loginHolder, label;
    private static final String d="LOGINCoba";

    private SessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.firstInstall();

        if (sessionManager.isLoggedIn()) {
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity .class));
        }
        loginBtn = (Button) findViewById(R.id.masuk);
        inputNIM = (EditText) findViewById(R.id.nim_input);
        inputPassword = (EditText) findViewById(R.id.password_input);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                if(!inputNIM.getText().toString().isEmpty()){
                    if(!inputPassword.getText().toString().isEmpty()){
                        Request request = new Request(LoginActivity.this);
                        request.verivyNim(inputNIM.getText().toString(),inputPassword.getText().toString(),0);
                        progressDialog.dismiss();
//                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
//                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Password Kosong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Nim Kosong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void showProgress() {
        progressDialog=null;// Initialize to null
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

}
