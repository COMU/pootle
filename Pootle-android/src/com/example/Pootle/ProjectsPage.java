package com.example.Pootle;

import android.app.Activity;



import android.view.View;
import android.os.Bundle;
import java.lang.String;

import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.Pootle.R.id.*;


/**
 * Created by busra on 21.11.2014.
 */
public class ProjectsPage extends Activity {


    String ciktii;
    ArrayList<String> listt = new ArrayList<String>();
    ArrayList<String> listpath = new ArrayList<String>();
    ArrayList<String> translation_projects = new ArrayList<String>();
    ArrayList<String> translation_projects_name = new ArrayList<String>();
    ArrayList<String> store_path = new ArrayList<String>();


    ListView listview;
    ListView listview2;




    String url;
    String hostname;
    String username;
    String password;
    String responses;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languagespage);


        ciktii = getIntent().getStringExtra("cikti");


        System.out.println("***************************************************************");

        try {
            JSONObject genreJsonObject = new JSONObject(ciktii);
            // get the title
            genreJsonObject.get("meta");
            // get the data
            JSONArray genreArray = (JSONArray) genreJsonObject.get("objects");


            for (int i = 0; i < genreArray.length(); i++) {
                JSONObject firstGenre = (JSONObject) genreArray.get(i);

                //çekilen diller listt e eklendi
                listt.add(firstGenre.getString("fullname"));
                listpath.add(firstGenre.getString("resource_uri"));

            }


            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-/n" + listpath);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //dilleri liste şeklinde görüntüleme

        listview = (ListView) findViewById(listView);


        ArrayList<String> languageslist = new ArrayList<String>(listt);

        listview.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, languageslist));


        //listeden bir dile tıklanıldığında projectname o dilin adını tutar
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                //listt adları tutuyor listpath de url yi tutuyor örneğin: listt:Türkiye listpath : /api/vi/languages/24/


                                                hostname = getIntent().getStringExtra("hostname");

                                                url = "http://" + hostname + listpath.get(position).toString();

                                                String responseStr = baglan(url).toString();


                                                //translation_projects ler çekilecek. Çünkü bu projenin yoludur.

                                                try {


                                                    JSONObject obj = new JSONObject(responseStr);
                                                    JSONArray arr = obj.getJSONArray("translation_projects");
                                                    for (int i = 0; i < arr.length(); i++) {
                                                        translation_projects.add(arr.getString(i));

                                                    }
                                                    Integer projesayisi = translation_projects.size();

                                                    //her translation_project ' e karşılık gelen proje adları farklı mesela translation_project: "translation_projects": ["/api/v1/translation-projects/11/", "/api/v1/translation-projects/64/", "/api/v1/translation-projects/65/"]
                                                    //burda bir dile ait üç farklı proje var.

                                                    for (int k = 0; k < projesayisi; k++) {

                                                        String translationurl = "http://" + hostname + translation_projects.get(k);
                                                        String translationpath = baglan(translationurl);


                                                        JSONObject obj2 = new JSONObject(translationpath);
                                                        String n = obj2.getString("real_path");
                                                        System.out.println("nnnnnnnnnn" + n);
                                                        String[] nn = n.split(",");
                                                        translation_projects_name.add(nn[0]);
                                                    }
                                                    listele(translation_projects_name);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                        }


        );




    }
    public void listele(ArrayList arry) {

        //projeleri liste şeklinde görüntüleme

        listview2 = (ListView) findViewById(listView2);
        ArrayList<String> projectlist = new ArrayList<String>(arry);


        listview2.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, projectlist));

        //listeden bir projeye tıklanıldığında
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      //store_path'i tutmalı



            }
        });
    }


 //baglantı yapar ve string şeklinde sonuc gösterir.

    public String baglan(String urll) {

        String url = urll;

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        hostname = getIntent().getStringExtra("hostname");

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

            System.out.println(response.getStatusLine());

            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                responses = EntityUtils.toString(entity);
                System.out.println("++++++++++++++++++++++" + responses);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return responses;
    }
}











