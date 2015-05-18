package com.example.bsrakdag.pootle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.view.View.OnTouchListener;
import static android.widget.AdapterView.*;

/**
 * Created by bsrakdag on 14.05.2015.
 */
public class FragmentLanguage extends Fragment implements OnItemSelectedListener {


    ArrayList<String> listt = new ArrayList<String>();
    ArrayList<String> listt2 = new ArrayList<String>();
    ArrayList<String> listpath = new ArrayList<String>();
    ArrayList<String> translation_projects = new ArrayList<String>();
    ArrayList<String> stores = new ArrayList<String>();

    String url,url2,url3,url4;
    String hostname;
    String username;
    String password;
    String languages_name;
    String responses;
    String arrstore;
    Spinner s1,s2;
    Button button;
    String ciktii;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project, container, false);

        FragmentLanguage.this.ciktii = this.getArguments().getString("login_cikti");
        FragmentLanguage.this.username= this.getArguments().getString("username");
        FragmentLanguage.this.password= this.getArguments().getString("password");
        FragmentLanguage.this.hostname= this.getArguments().getString("server");

        //GERİ DÖNMEK İÇİN
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentManager fm= getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    HomeFragment fragment = new HomeFragment();
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                    return true;
                } else {
                    return false;
                }
            }
        });


        System.out.println( "FRAGMENT LANGUAGE YE GELEN VERİİİİ: "+ ciktii);


        FragmentLanguage.this.s1 = (Spinner) rootView.findViewById(R.id.spinner_language);
        FragmentLanguage.this.s2= (Spinner) rootView.findViewById(R.id.spinner_project);
        FragmentLanguage.this.button= (Button) rootView.findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                listele();

            }
        });

        try {
            JSONObject genreJsonObject = new JSONObject(ciktii);
            // get the title
            genreJsonObject.get("meta");
            // get the data
            JSONArray genreArray = (JSONArray) genreJsonObject.get("objects");


            for (int i = 0; i < genreArray.length(); i++) {
                JSONObject firstGenre = (JSONObject) genreArray.get(i);

                //çekilen diller listt e eklendi
                listt.add(i,firstGenre.getString("fullname"));

                //dil yollarını ekler
                listpath.add(i,firstGenre.getString("resource_uri"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // dizi adaptera ekleniyor ve dropdown seklinde bir layout secilir

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, listt);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(dataAdapter);
        s1.setOnItemSelectedListener((OnItemSelectedListener) this);




        return rootView;
    }
    //geri tuşuna basınca uygulamadan çıkma uyarısı


    public String baglan(String urll) throws InterruptedException {

        FragmentLanguage.this.url2=urll;
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {

                //sunucuya bağlanma ve ordan language api lerini çekme
                DefaultHttpClient httpclient = new DefaultHttpClient();
                try {
                    httpclient.getCredentialsProvider().setCredentials(
                            new AuthScope(hostname, 80),
                            new UsernamePasswordCredentials(username, password));

                    HttpGet httpget = new HttpGet(url2);

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
            }
        });
        t.start(); // spawn thread
        t.join();  // wait for thread to finish
        return responses;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_language:
                listt2.clear();
                translation_projects.clear();
                stores.clear();


                //listt dil adlarını tutuyor listpath de url yi tutuyor örneğin: listt:Türkiye listpath : /api/vi/languages/24/

                if(position!=0){
                    FragmentLanguage.this.languages_name=listt.get(position);
                    url3 = "http://" + hostname + listpath.get(position).toString();
                    System.out.println("BAGLANILAN LİSTPATH İÇİN URL"+ url );
                    String responseStr = null;
                    try {
                        responseStr = baglan(url3).toString();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    //translation_projects ler çekilecek. Çünkü bu projenin yoludur.

                    try {


                        JSONObject obj = new JSONObject(responseStr);
                        JSONArray arr = obj.getJSONArray("translation_projects");
                        for (int i = 0; i < arr.length(); i++) {
                            translation_projects.add(i,arr.getString(i));

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

                            listt2.add(tokens[0]);
                            stores.add(obj2.getString("stores"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listt2);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s2.setAdapter(dataAdapter);
                    s2.setOnItemSelectedListener((OnItemSelectedListener) this);

                }



                break;
            case R.id.spinner_project:
                if(listt2.size()==1){
                    Toast.makeText(getActivity(),"Lütfen Dil Seçiniz",Toast.LENGTH_SHORT).show();



                }else if(listt2.size()!=1){

                    FragmentLanguage.this.arrstore=stores.get(position).toString().replace("\\", "");
                    // do stuffs with you spinner 2
                    // dizi adaptera ekleniyor ve dropdown seklinde bir layout secilir.

                    System.out.println( "STOREEEESSSSSSSSS: " +arrstore );



                    break;
                }

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void listele() {

        // Perform action on click
        if(arrstore!=null){
            //fragment değişimi için
            FragmentManager fm= getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            FragmentFile fragment = new FragmentFile();
            Bundle bundle = new Bundle();
            bundle.putString("login_cikti", ciktii);
            bundle.putString("arrstore_cikti", arrstore);
            bundle.putString("username", username);
            bundle.putString("password", password);
            bundle.putString("server", hostname);
            bundle.putString("languages_name", languages_name);

            fragment.setArguments(bundle);

            transaction.replace(R.id.container, fragment);
            transaction.commit();

        }else{
            Toast.makeText(getActivity(),"Lütfen Proje Seçiniz",Toast.LENGTH_SHORT).show();


        }
    }
}
