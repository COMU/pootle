package com.example.Pootle;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;

import java.lang.String;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;


public class LoginPage extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private String username,password,hostname;
    private Button ok;
    private EditText editTextUsername,editTextPassword;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    EditText text;
    String url;
    String cikti;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //remember me button
        ok = (Button)findViewById(R.id.buttonLogin);
        ok.setOnClickListener(this);
        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);

        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextUsername.setText(loginPreferences.getString("username",""));
            editTextPassword.setText(loginPreferences.getString("password",""));
            saveLoginCheckBox.setChecked(true);
        }
    }

    public void onClick(View view) {
        if (view == ok) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);

            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();

            if (saveLoginCheckBox.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", username);
                loginPrefsEditor.putString("password", password);
                loginPrefsEditor.commit();
            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }

            text= (EditText)findViewById(R.id.server);
            hostname=text.getText().toString();

            url= "http://"+hostname+":80/api/v1/languages/";


            //sunucuya bağlanma ve ordan language api lerini çekme
            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                httpclient.getCredentialsProvider().setCredentials(
                        new AuthScope(hostname, 80),
                        new UsernamePasswordCredentials(username, password));

                HttpGet httpget = new HttpGet(url);

                System.out.println("executing request" + httpget.getRequestLine());
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());

                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    cikti=EntityUtils.toString(entity);

                    System.out.println(cikti);

                    Intent i = new Intent(LoginPage.this, ProjectsPage.class);
                    i.putExtra("cikti", cikti);
                    i.putExtra("hostname", hostname);
                    i.putExtra("username",username);
                    i.putExtra("password", password);
                    httpget.abort();
                    startActivity(i);


                }
            } catch(Exception e){
                e.printStackTrace();
            }


        }
    }









}


