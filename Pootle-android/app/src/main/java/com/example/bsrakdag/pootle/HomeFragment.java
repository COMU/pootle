package com.example.bsrakdag.pootle;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by bsrakdag on 23.04.2015.
 */
public class HomeFragment extends Fragment {

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    HttpResponse response;
    HttpGet httpget;

    private FragmentTabHost mTabHost;
    ArrayList<String> languagelist;
    ArrayList<String> languagepathlist;
    private String username,password,hostname;
    private Button ok;
    private EditText editTextUsername,editTextPassword;

    private Boolean saveLogin;
    EditText text;
    String url;
    String cikti;
    Button login;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login_page, container, false);
        //GERİ DÖNMEK İÇİN
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Toast.makeText(getActivity(), "ÇIKMAK İÇİN BİRKEZ DAHA BASIN", Toast.LENGTH_SHORT).show();
                    android.os.Process.killProcess(android.os.Process.myPid());

                    return true;
                } else {
                    return false;
                }
            }
        });
        HomeFragment.this.editTextUsername = (EditText)rootView.findViewById(R.id.editTextUsername);
        HomeFragment.this.editTextPassword = (EditText)rootView.findViewById(R.id.editTextPassword);
        HomeFragment.this.text = (EditText)rootView.findViewById(R.id.server);
        HomeFragment.this.login = (Button) rootView.findViewById(R.id.buttonlogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                try {
                    baglan();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        return rootView;
    }




    public void baglan() throws InterruptedException, IOException {




            HomeFragment.this.username = editTextUsername.getText().toString();
            HomeFragment.this.password = editTextPassword.getText().toString();
            HomeFragment.this.hostname=text.getText().toString();
            url= "http://"+hostname+"/api/v1/languages/";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //code to do the HTTP request
                    //sunucuya bağlanma ve ordan language api lerini çekme
                    DefaultHttpClient httpclient = new DefaultHttpClient();
                    try {
                        httpclient.getCredentialsProvider().setCredentials(
                                new AuthScope(hostname, 80),
                                new UsernamePasswordCredentials(username, password));

                        HomeFragment.this.httpget = new HttpGet(url);

                        System.out.println("executing request" + httpget.getRequestLine());
                        response = httpclient.execute(httpget);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        t.start(); // spawn thread
        t.join();  // wait for thread to finish



        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());

                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }
                try {
                    HomeFragment.this.cikti= EntityUtils.toString(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HomeFragment.this.httpget.abort();

                //fragment değişimi için
                FragmentManager fm= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                FragmentLanguage fragment = new FragmentLanguage();
                Bundle bundle = new Bundle();
                bundle.putString("login_cikti", cikti);
                bundle.putString("username", username);
                bundle.putString("password", password);
                bundle.putString("server", hostname);

                fragment.setArguments(bundle);


                transaction.replace(R.id.container, fragment);
                transaction.commit();


            }
        });

        t2.start(); // spawn thread
        t2.join();  // wait for thread to finish


    }





}



