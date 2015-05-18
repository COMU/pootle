package com.example.bsrakdag.pootle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bsrakdag on 17.05.2015.
 */
public class SessionFragment extends android.support.v4.app.Fragment {
    Button change;
    Spinner changespinner;
    private String username,password,hostname;

    String url,cikti;
    HttpResponse response;
    HttpGet httpget;

    ArrayList<String> listserver = new ArrayList<String>();
    String gecici, gecici2;
    public String[] geciciserver;
    String aranacak;
    int indeks;
    ArrayAdapter<String> dataAdapter;
    private Veritabani dataBase;
    private String[] sutunlar = {"_servername", "username", "password", "nickname"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_session_page, container, false);

        SessionFragment.this.changespinner = (Spinner) rootView.findViewById(R.id.spinnerchangelist);
        //kayıtlı sunucuları sayfa ilk açıldığında göstermek için

        listserver.clear();
        bilgiGetir();
        if (listserver == null) {
            Toast.makeText(getActivity(), "KAYITLI SUNUCU YOK" , Toast.LENGTH_SHORT).show();


        } else {
            dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listserver);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            changespinner.setAdapter(dataAdapter);
            changespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    SessionFragment.this.indeks = arg2;
                    // TODO Auto-generated method stub

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }


            });
        }
        SessionFragment.this.change = (Button) rootView.findViewById(R.id.changebutton);
        change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                try {
                    aranacak=listserver.get(indeks);
                    geciciserver=aranacak.split(" ");
                    SessionFragment.this.hostname=geciciserver[0];
                    aramaYap(SessionFragment.this.hostname);
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

                        SessionFragment.this.httpget = new HttpGet(url);

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
                    SessionFragment.this.cikti= EntityUtils.toString(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SessionFragment.this.httpget.abort();

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
    private void bilgiGetir() {
        dataBase = new Veritabani(getActivity());
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor okunan = db.query("ServerTablosu", sutunlar, null, null, null, null, null);
        int i = 0;
        while (okunan.moveToNext()) {

            gecici = okunan.getString((okunan.getColumnIndex("_servername")));
            gecici2 = okunan.getString((okunan.getColumnIndex("nickname")));
            gecici = gecici + "  -->  " + gecici2;
            SessionFragment.this.listserver.add(i, gecici);
            i = i + 1;
        }
        okunan.close();

    }

    public void aramaYap(String search) {

        SQLiteDatabase db = dataBase.getReadableDatabase();
        String searchStr = search;

        Cursor c = db.rawQuery("SELECT * FROM ServerTablosu WHERE _servername like '" + searchStr + "'", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    SessionFragment.this.password = c.getString(c.getColumnIndex("password"));
                    SessionFragment.this.username = c.getString(c.getColumnIndex("username"));

                } while (c.moveToNext());
            }
        }

    }


}
