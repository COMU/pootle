package com.example.Pootle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bsrakdag on 21.03.2015.
 */
public class LanguageProject extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner s1, s2;
    String ciktii;
    ArrayList<String> listt = new ArrayList<String>();
    ArrayList<String> listpath = new ArrayList<String>();
    ArrayList<String> translation_projects = new ArrayList<String>();
    ArrayList<String> translation_projects_name = new ArrayList<String>();
    ArrayList<String> file_name = new ArrayList<String>();
    ArrayList<String> stores = new ArrayList<String>();
    ArrayList<String> stores_project = new ArrayList<String>();
    TextView projeler;

    Spinner spinner_languages;
    Spinner spinner_projects;

    String url;
    String hostname;
    String username;
    String password;
    String responses;
    String arrstore;

    public void onCreate(Bundle InstanceState) {
        super.onCreate(InstanceState);
        this.setContentView(R.layout.language);

        s1 = (Spinner) findViewById(R.id.spinner_language);
        s2 = (Spinner) findViewById(R.id.spinner_project);
        s1.setSelected(false);
        s2.setSelected(false);
        s1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        s2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ciktii = getIntent().getStringExtra("cikti");
        try {
            JSONObject genreJsonObject = new JSONObject(ciktii);
            // get the title
            genreJsonObject.get("meta");
            // get the data
            JSONArray genreArray = (JSONArray) genreJsonObject.get("objects");


            for (int i = 1; i < genreArray.length(); i++) {
                JSONObject firstGenre = (JSONObject) genreArray.get(i);

                //çekilen diller listt e eklendi
                listt.add(firstGenre.getString("fullname"));

                //dil yollarını ekler
                listpath.add(firstGenre.getString("resource_uri"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
     // dizi adaptera ekleniyor ve dropdown seklinde bir layout secilir
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, listt);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // spinnera dropdownlist ekleniyor
        s1.setAdapter(adapter);



    }

    public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {




        switch (parent.getId()) {
            case R.id.spinner_language:
                // do stuffs with you spinner 1
                s1.setSelection(pos);


                translation_projects_name.clear();
                translation_projects.clear();
                stores.clear();
                //listt adları tutuyor listpath de url yi tutuyor örneğin: listt:Türkiye listpath : /api/vi/languages/24/


                hostname = getIntent().getStringExtra("hostname");

                url = "http://" + hostname + listpath.get(pos).toString();

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
                        String delims = "[/]";
                        String[] tokens = n.split(delims);

                        translation_projects_name.add(tokens[0]);
                        stores.add(obj2.getString("stores"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, translation_projects_name);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s2.setAdapter(adapter2);


                break;
            case R.id.spinner_project:
                s2.setSelection(pos);
                arrstore=stores.get(pos).toString().replace("\\", "");
                // do stuffs with you spinner 2
                // dizi adaptera ekleniyor ve dropdown seklinde bir layout secilir.

                System.out.println( "STOREEEESSSSSSSSS: " +arrstore );
                Intent files = new Intent(LanguageProject.this, FilesPage.class);
                files.putExtra("store_path", arrstore);
                files.putExtra("username", username);
                files.putExtra("hostname", hostname);
                files.putExtra("password", password);
                startActivity(files);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }
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
                httpget.abort();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return responses;
    }


}

