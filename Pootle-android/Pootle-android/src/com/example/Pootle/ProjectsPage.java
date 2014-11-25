package com.example.Pootle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by busra on 21.11.2014.
 */
public class ProjectsPage extends Activity {


    String ciktii;
    ArrayList<String> listt = new ArrayList<String>();
    ListView listview;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languagespage);


        ciktii=getIntent().getStringExtra("cikti");


        System.out.println("***************************************************************");

        try {
            JSONObject genreJsonObject = new JSONObject(ciktii);
            // get the title
            genreJsonObject.get("meta");
            // get the data
            JSONArray genreArray = (JSONArray) genreJsonObject.get("objects");
            // get the first genre
            ArrayList<String> listdata = new ArrayList<String>();

            for (int i=0; i<genreArray.length();i++) {
                JSONObject firstGenre = (JSONObject) genreArray.get(i);
                listt.add(firstGenre.getString("fullname"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        listview = (ListView)findViewById(R.id.listView);
        ArrayList<String> languageslist = new ArrayList<String>(listt);

        ArrayAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.languagespage,R.id.listTextView ,languageslist);

        listview.setAdapter(listAdapter);

        System.out.println("***************************************************************");


    }
}




