package com.example.Pootle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
/**
 * Created by busra on 17.12.2014.
 */
public class TranslatePage extends Activity {

    //kontrol put mu post mu onu kontrol eder baglan metodunda
    int kontrol = 0;

    String text = null;

    //o anki indeksi tutar.
    int x = 0;

    int totalunits;
    String unitspath;
    String storepath;
    String username;
    String password;
    String hostname;
    String responses;
    String url;
    String suggestion_url;
    TextView cevrilecek;
    EditText cevrilmis;
    String unitscevap;
    Button show;
    Spinner suggestion_list;
    CheckBox need_checkBox;


    ArrayList<String> units = new ArrayList<String>();
    String units_need[];

    ArrayList<String> suggestion_target_f = new ArrayList<String>();
    int indeks = 0;
    String need;
    String suggestions[];



    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.tranlatepage);

        cevrilecek = (TextView) findViewById(R.id.cevrilecek);
        cevrilmis = (EditText) findViewById(R.id.cevrilmis);
        show = (Button) findViewById(R.id.showsuggestions);
        suggestion_list = (Spinner) findViewById(R.id.spinner_need);
        need_checkBox = (CheckBox) findViewById(R.id.checkBox_need);
        hostname = getIntent().getStringExtra("hostname");
        kontrol = 0;
        need = getIntent().getStringExtra("need");
        storepath = getIntent().getStringExtra("path");
        System.out.println(storepath);

        //total isteniyorsa:
        if (Integer.parseInt(need) == 0) {

            url = "http://" + hostname + storepath;

            String responseStr = null;
            try {
                responseStr = baglan3(url, null, null, kontrol);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println("sooonn ressspoonnseee " + responseStr);


            //her bir units çekilecek ve sırayla gösterilecek

            try {

                JSONObject obj = new JSONObject(responseStr);
                JSONArray arr = obj.getJSONArray("units");
                totalunits = arr.length();

                for (int i = 0; i < totalunits; i++) {
                    units.add(arr.getString(i));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                unitscevap = baglan3("http://" + hostname + units.get(x), null, null, kontrol);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            unitspath = "http://" + hostname + units.get(x);


            suggestion_listele(unitscevap);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^UUNNIIIIITSSSPATTHH:" + unitscevap);

            try {

                //cevrilmesi gerekenleri textview e set eder
                JSONObject obj2 = new JSONObject(unitscevap);
                String source_f = obj2.getString("source_f");
                byte stext[] = source_f.getBytes("ISO-8859-1");
                String s_value = new String(stext, "UTF-8");
                cevrilecek.setText(s_value);
                cevrilecek.setTextColor(Color.BLACK);

                //varsa önceden çevrilmişleri edittext e set eder
                String target_f = obj2.getString("target_f");

                if(target_f!=null){
                    byte ttext[] = target_f.getBytes("ISO-8859-1");
                    String t_value = new String(ttext, "UTF-8");
                    cevrilmis.setText(t_value);
                    cevrilmis.setTextColor(Color.BLACK);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
        //çeviri gerekli olanlar isteniyorsa:
        else if (Integer.parseInt(need) == 1) {

            kontrol = 0;

            units_need = storepath.split(",");
            totalunits = units_need.length;
            // artık elimde çeviri gerekli olan units path leri var. "/api/v1/units/65212/"
            for (int i = 0; i < units_need.length; i++) {
                url = "http://" + hostname + units_need[i];
                try {
                    unitscevap = baglan3(url, null, null, kontrol);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {

                    //cevrilmesi gerekenleri textview e set eder
                    JSONObject obj2 = new JSONObject(unitscevap);
                    String source_f = obj2.getString("source_f");
                    byte stext[] = source_f.getBytes("ISO-8859-1");
                    String s_value = new String(stext, "UTF-8");
                    cevrilecek.setText(s_value);
                    cevrilecek.setTextColor(Color.BLACK);


                    //varsa önceden çevrilmişleri edittext e set eder
                    String target_f = obj2.getString("target_f");
                    if(target_f!=null){
                        byte ttext[] = target_f.getBytes("ISO-8859-1");
                        String t_value = new String(ttext, "UTF-8");
                        cevrilmis.setText(t_value);
                        cevrilmis.setTextColor(Color.BLACK);
                    }

                    suggestion_listele(unitscevap);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }


        }


    }

    public void suggestion_listele(String girdi) {

        suggestion_target_f.clear();
        suggestions = null;
        try {

            JSONObject obj_sug = new JSONObject(girdi);

            //önerileri suggestions id li listview e set eder.
            String suggestion = obj_sug.getString("suggestions");
            suggestion=suggestion.replace("\\", "");
            suggestion=suggestion.substring(1, suggestion.length() - 1);


            indeks = suggestion.indexOf(",");

            System.out.println("ééééééééééééééééééSTRING OLARAK SUGGESTIONNLARR::" + suggestion);



            //birden fazla suggestion varsa
            if (indeks != -1) {
                //string şeklinde gelen öneri yollarını , ile ayırır bir diziye atar.

                suggestions = suggestion.split(",");
                for (int i = 0; i < suggestions.length; i++) {

                    System.out.println("SUGGESTION "+ suggestions[i]);

                }
                for (int i = 0; i < suggestions.length; i++) {

                        suggestions[i] = suggestions[i].substring(1, suggestions[i].length() - 1);

                }

                for (int i = 0; i < suggestions.length; i++) {


                    hostname = getIntent().getStringExtra("hostname");

                    //suggestionları
                    suggestion_url = "http://" + hostname + suggestions[i];
                    System.out.println(suggestion_url);
                    String responseStrsuggestion = baglan3(suggestion_url, null, null, kontrol);
                    System.out.println("BAGLANDIKTAN SONRA SUGGESTION CIKTISI: "+responseStrsuggestion);
                    try {
                        JSONObject obj_sug_target = new JSONObject(responseStrsuggestion);
                        String SNAME = obj_sug_target.getString("target_f");
                        System.out.println("SNAME:    "+  SNAME);
                        byte suggest_text[] = SNAME.getBytes("ISO-8859-1");
                        String suggest_target_f = new String(suggest_text, "UTF-8");
                        suggestion_target_f.add(i,suggest_target_f);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }


                //sıfır veya bir tane suggestion varsa
            } else {
                indeks = suggestion.indexOf("\"");
                //bir tane suggestion varsa
                if (indeks != -1) {
                    String sug = suggestion.substring(1, suggestion.length() - 1);

                    System.out.println("SSSSSSSUGGGGGGGGGG:  " + sug);
                    //suggestionları
                    suggestion_url = "http://" + hostname + sug;
                    System.out.println(suggestion_url);
                    String responseStrsuggestion = baglan3(suggestion_url, null, null, kontrol);
                    try {
                        JSONObject obj_sug_target = new JSONObject(responseStrsuggestion);
                        String SNAME = obj_sug_target.getString("target_f");
                        byte suggest_text[] = SNAME.getBytes("ISO-8859-1");
                        String suggest_target_f = new String(suggest_text, "UTF-8");
                        suggestion_target_f.add(0,suggest_target_f);




                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                //sıfır tane suggestion varsa
                else {
                    suggestion_target_f.add(0,"Öneri yok");
                }


            }


            suggestion_list.setVisibility(View.INVISIBLE);
            // Setting a Custom Adapter to the Spinner
            suggestion_list.setAdapter(new MyAdapter(TranslatePage.this, R.layout.custom,
                    suggestion_target_f));

            suggestion_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int postion, long arg3) {
                    // TODO Auto-generated method stub

                    if (parent.getSelectedItem().toString() != "Öneri yok") {
                        cevrilmis.setText(suggestion_target_f.get(postion).toString());

                    }



                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public String baglan3(String urll, Object ob, String git, int kont) throws JSONException {


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


            if (kontrol == 1) {

                HttpContext localContext = new BasicHttpContext();
                HttpPut put = new HttpPut(urlem);
                put.addHeader("X-Apikey", "");
                StringEntity se = new StringEntity(ob.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                put.addHeader("Accept", "application/json");
                put.addHeader("Content-type", "application/json");
                put.setEntity(se);


                try {

                    HttpResponse response = httpclient.execute(put, localContext);
                    HttpEntity entity = response.getEntity();
                    text = entity.toString();
                    System.out.println("%%%%%%%%%%%SONUC:" + text);
                } catch (Exception e) {
                    return e.getLocalizedMessage();
                }


            }
            HttpGet httpget = new HttpGet(urlem);

            System.out.println("executing request" + httpget.getRequestLine());

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            System.out.println(response.getStatusLine());

            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                responses = EntityUtils.toString(entity);
                httpget.abort();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return responses;
    }

    public void cevir(View view) throws IOException, JSONException {

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        String gidecek = cevrilmis.getText().toString();
        String unitgidecekyol = unitspath;
        final JSONObject gonderilecek_json_obj = new JSONObject(unitscevap);
        gonderilecek_json_obj.put("target_f", gidecek);
        System.out.println("GÖNDERİLECEK OBJE:     " + gonderilecek_json_obj.toString());

        //çalışma gerekli ise state 50 yap yollaa
        need_checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    gonderilecek_json_obj.put("state", "50");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        kontrol = 1;


        baglan3(unitgidecekyol, gonderilecek_json_obj, gidecek, kontrol);
        need_checkBox.setChecked(false);
        View vi = new View(this);
        Ilerigit(vi);

    }

    public void Gerigit(View view) throws JSONException {
        suggestion_list.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);
        if (x != totalunits) {
            x = x - 1;
            if (x == 0) {
                x = totalunits-1;
            }
        }
        if (x == 0) {
            x = totalunits;
        }
        kontrol = 0;
        //bir sonraki units e geçer. need=0 ise units in indeksini arttırır. değilse units_need in indeksini arttırır.
        if (Integer.parseInt(need) == 0) {
            unitscevap = baglan3("http://" + hostname + units.get(x), null, null, kontrol);
            unitspath = "http://" + hostname + units.get(x);
        } else {
            unitspath = "http://" + hostname + units_need[x];
            unitscevap = baglan3("http://" + hostname + units_need[x], null, null, kontrol);


        }
        try {
            JSONObject obj2 = new JSONObject(unitscevap);
            String source_f = obj2.getString("source_f");
            byte stext[] = source_f.getBytes("ISO-8859-1");
            String s_value = new String(stext, "UTF-8");
            cevrilecek.setText(s_value);
            cevrilecek.setTextColor(Color.BLACK);


            cevrilecek.setText(source_f);
            String target_f = obj2.getString("target_f");
            if(target_f!=null){
                byte ttext[] = target_f.getBytes("ISO-8859-1");
                String t_value = new String(ttext, "UTF-8");
                cevrilmis.setText(t_value);
                cevrilmis.setTextColor(Color.BLACK);
            }


            suggestion_listele(unitscevap);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void Ilerigit(View view) throws JSONException {
        suggestion_list.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);

        if (x != totalunits) {
            x = x + 1;
            if (x == totalunits) {
                x = 0;
            }
        }
        if (x == totalunits) {
            x = 0;
        }
        kontrol = 0;
        //bir öncekş units e geçer. need=0 ise units in indeksini azaltır. değilse units_need in indeksini azaltır.
        if (Integer.parseInt(need) == 0) {
            unitscevap = baglan3("http://" + hostname + units.get(x), null, null, kontrol);
            unitspath = "http://" + hostname + units.get(x);
        } else {
            unitspath = "http://" + hostname + units_need[x];
            unitscevap = baglan3("http://" + hostname + units_need[x], null, null, kontrol);


        }
        try {
            JSONObject obj2 = new JSONObject(unitscevap);
            String source_f = obj2.getString("source_f");
            byte stext[] = source_f.getBytes("ISO-8859-1");
            String s_value = new String(stext, "UTF-8");
            cevrilecek.setText(s_value);
            cevrilecek.setTextColor(Color.BLACK);


            String target_f = obj2.getString("target_f");
            if(target_f!=null){
                byte ttext[] = target_f.getBytes("ISO-8859-1");
                String t_value = new String(ttext, "UTF-8");
                cevrilmis.setText(t_value);
                cevrilmis.setTextColor(Color.BLACK);
            }



            suggestion_listele(unitscevap);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void suggestion_listele(View view) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        show.setVisibility(View.GONE);
        suggestion_list.setVisibility(View.VISIBLE);


    }

    public class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context ctx, int txtViewResourceId,ArrayList objects) {
            super(ctx, txtViewResourceId, objects);
        }
        @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        } @Override public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        } public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View mySpinner = inflater.inflate(R.layout.custom, parent, false);

            TextView main_text = (TextView) mySpinner .findViewById(R.id.suggest);
            main_text.setText(suggestion_target_f.get(position));

            ImageView left_icon = (ImageView) mySpinner .findViewById(R.id.imgtrue);
            left_icon.setOnClickListener(spinnerOnClickListener);

            ImageView right_icon = (ImageView) mySpinner .findViewById(R.id.imgfalse);

            right_icon.setOnClickListener(spinnerOnClickListener);


            return mySpinner; }
    }

    private View.OnClickListener spinnerOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            // Get the id of the ImageButton that is clicked
            ImageView btn = (ImageView) v;


    };
}