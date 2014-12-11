package com.example.Pootle;

import android.app.Activity;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by busra on 26.11.2014.
 */
public class FilesPage extends Activity {

    String storepath;
    String username;
    String password;
    String hostname;
    String url;
    String storearray[];
    String responses;
    ArrayList<String> filelist = new ArrayList<String>();
    ListView listview3;


    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.filespage);
        // ProjectsPage den dosya yollarının hepsini string şeklinde alır.
        storepath = getIntent().getStringExtra("store_path");


        //string şeklinde gelen dosya yollarını , ile ayırır bir diziye atar.
        storearray = storepath.split(",");

        // stringte bütün "\/" yerine "/" yazar çünkü httpget hata veriyor.
        for (int i=0;i<storearray.length;i++ ){
            storearray[i]=storearray[i].replace("\\", "");
       }

        //her dosya yoluna tek tek giderek dosya adlarını filelist e atar.
        for (int i=0;i<storearray.length;i++){

            String path = storearray[i].substring(2, storearray[i].length() - 1);
            hostname = getIntent().getStringExtra("hostname");
            url = "http://" + hostname + path;

            String responseStr= baglan2(url);


            try {
                JSONObject obj = new JSONObject(responseStr);
                String n = obj.getString("name");
                filelist.add(n);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //dosyaları liste şeklinde görüntüleme

        listview3 = (ListView) findViewById(R.id.listView3);
        ArrayList<String> filenamelist = new ArrayList<String>(filelist);


        listview3.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,filenamelist));






    }

        //baglantı yapar ve string şeklinde sonuc gösterir.

    public String baglan2(String urll) {

        String urlem = urll;


        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        hostname = getIntent().getStringExtra("hostname");
        //sunucuya bağlanma ve ordan language api lerini çekme
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(hostname, 80),
                    new UsernamePasswordCredentials(username, password));

            HttpGet httpget = new HttpGet(urlem);

            System.out.println("executing request" + httpget.getRequestLine());

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            System.out.println(response.getStatusLine());

            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                responses = EntityUtils.toString(entity);
                System.out.println("++++++++++++++++++++++" + responses);
                httpget.abort();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return responses;
    }


    }


