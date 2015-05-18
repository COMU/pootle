package com.example.bsrakdag.pootle;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

/**
 * Created by bsrakdag on 15.05.2015.
 */
public class FragmentTranslate extends Fragment {

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
    String url, url2;
    String suggestion_url;
    TextView cevrilecek;
    TextView cevrilecek_dil;
    ArrayAdapter<String> dataAdapter;
    EditText cevrilmis;
    String aranacak;
    String unitscevap;
    Button show, ileri, geri, send;
    Spinner suggestion_list;
    CheckBox need_checkBox;
    String languages_name;
    ListView suggestion;
    ArrayAdapter<String> adapter;
    ArrayList<String> units = new ArrayList<String>();
    String units_need[];

    ArrayList<String> suggestion_target_f = new ArrayList<String>();
    int indeks = 0;
    String need;
    String suggestions[];
    String ciktii;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_translate, container, false);

        FragmentTranslate.this.storepath = this.getArguments().getString("arrstore_cikti");
        FragmentTranslate.this.ciktii= this.getArguments().getString("login_cikti");
        FragmentTranslate.this.username = this.getArguments().getString("username");
        FragmentTranslate.this.password = this.getArguments().getString("password");
        FragmentTranslate.this.hostname = this.getArguments().getString("hostname");
        FragmentTranslate.this.need = this.getArguments().getString("need");
        FragmentTranslate.this.storepath = this.getArguments().getString("path");
        FragmentTranslate.this.languages_name = this.getArguments().getString("languages_name");

        //GERİ DÖNMEK İÇİN
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentManager fm= getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    FragmentFile fragment = new FragmentFile();
                    Bundle bundle = new Bundle();
                    bundle.putString("arrstore_ciktii", storepath);
                    bundle.putString("login_cikti", ciktii);
                    bundle.putString("path", storepath);
                    bundle.putString("need", need);
                    bundle.putString("username", username);
                    bundle.putString("hostname", hostname);
                    bundle.putString("password", password);
                    bundle.putString("languages_name", languages_name);
                    fragment.setArguments(bundle);

                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                    return true;
                } else {
                    return false;
                }
            }
        });


        FragmentTranslate.this.cevrilecek_dil = (TextView) rootView.findViewById(R.id.cevrilen_dil);
        cevrilecek_dil.setText(languages_name);
        FragmentTranslate.this.cevrilecek = (TextView) rootView.findViewById(R.id.cevrilecek);
        FragmentTranslate.this.cevrilmis = (EditText) rootView.findViewById(R.id.cevrilmis);
        FragmentTranslate.this.aranacak = cevrilecek.toString();
        FragmentTranslate.this.suggestion= (ListView) rootView.findViewById(R.id.oneri);






        FragmentTranslate.this.ileri = (Button) rootView.findViewById(R.id.ilerigit);
        ileri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                try {
                    Ilerigit();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        FragmentTranslate.this.geri = (Button) rootView.findViewById(R.id.gerigit);
        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                try {
                    Gerigit();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        FragmentTranslate.this.send = (Button) rootView.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                try {
                    cevir();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        FragmentTranslate.this.need_checkBox = (CheckBox) rootView.findViewById(R.id.checkBox_need);
        FragmentTranslate.this.kontrol = 0;

        //total isteniyorsa:
        if (Integer.parseInt(need) == 0) {

            url = "http://" + hostname + storepath;

            String responseStr = null;
            try {
                responseStr = baglan3(url, null, null, kontrol);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
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
            } catch (InterruptedException e) {
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

                if (target_f != null) {
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
                } catch (InterruptedException e) {
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
                    if (target_f != null) {
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


        return rootView;
    }

    public void suggestion_listele(String girdi) {
        suggestion_target_f.clear();
        suggestions = null;
        try {

            JSONObject obj_sug = new JSONObject(girdi);

            //önerileri suggestions id li listview e set eder.
            String suggestion = obj_sug.getString("suggestions");
            suggestion = suggestion.replace("\\", "");
            suggestion = suggestion.substring(1, suggestion.length() - 1);


            indeks = suggestion.indexOf(",");

            System.out.println("ééééééééééééééééééSTRING OLARAK SUGGESTIONNLARR::" + suggestion);


            //birden fazla suggestion varsa
            if (indeks != -1) {
                //string şeklinde gelen öneri yollarını , ile ayırır bir diziye atar.

                suggestions = suggestion.split(",");
                for (int i = 0; i < suggestions.length; i++) {

                    System.out.println("SUGGESTION " + suggestions[i]);

                }
                for (int i = 0; i < suggestions.length; i++) {

                    suggestions[i] = suggestions[i].substring(1, suggestions[i].length() - 1);

                }

                for (int i = 0; i < suggestions.length; i++) {


                    //suggestionları
                    suggestion_url = "http://" + hostname + suggestions[i];
                    System.out.println(suggestion_url);
                    String responseStrsuggestion = baglan3(suggestion_url, null, null, kontrol);
                    System.out.println("BAGLANDIKTAN SONRA SUGGESTION CIKTISI: " + responseStrsuggestion);
                    try {
                        JSONObject obj_sug_target = new JSONObject(responseStrsuggestion);
                        String SNAME = obj_sug_target.getString("target_f");
                        System.out.println("SNAME:    " + SNAME);
                        byte suggest_text[] = SNAME.getBytes("ISO-8859-1");
                        String suggest_target_f = new String(suggest_text, "UTF-8");
                        FragmentTranslate.this.suggestion_target_f.add(i, suggest_target_f);


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
                        FragmentTranslate.this.suggestion_target_f.add(0, suggest_target_f);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                //sıfır tane suggestion varsa
                else {
                    FragmentTranslate.this.suggestion_target_f.add(0, "Öneri yok");
                }


            }




        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FragmentTranslate.this.adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, suggestion_target_f);
        FragmentTranslate.this.suggestion.setAdapter(adapter);
        FragmentTranslate.this.suggestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                FragmentTranslate.this.cevrilmis.setText(suggestion_target_f.get(itemPosition).toString());

            }
        });


    }


    public String baglan3(final String urll, final Object ob, String git, int kont) throws JSONException, InterruptedException {

        FragmentTranslate.this.url2 = urll;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                //sunucuya bağlanma ve ordan language api lerini çekme
                DefaultHttpClient httpclient = new DefaultHttpClient();
                try {
                    httpclient.getCredentialsProvider().setCredentials(
                            new AuthScope(hostname, 80),
                            new UsernamePasswordCredentials(username, password));


                    if (kontrol == 1) {

                        HttpContext localContext = new BasicHttpContext();
                        HttpPut put = new HttpPut(url2);
                        put.addHeader("X-Apikey", "");
                        StringEntity se = new StringEntity(ob.toString());
                        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                        put.addHeader("Accept", "application/json");
                        put.addHeader("Content-type", "application/json");
                        put.setEntity(se);

                        HttpResponse response = httpclient.execute(put, localContext);
                        HttpEntity entity = response.getEntity();
                        text = entity.toString();
                        System.out.println("%%%%%%%%%%%SONUC:" + text);


                    }
                    HttpGet httpget = new HttpGet(url2);

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
            }
        });
        t.start(); // spawn thread
        t.join();  // wait for thread to finish


        return responses;
    }

    public void cevir() throws IOException, JSONException, InterruptedException {


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
        View vi = new View(getActivity());
        Ilerigit();

    }

    public void Gerigit() throws JSONException, InterruptedException {

        if (x != totalunits) {
            x = x - 1;
            if (x == 0) {
                x = totalunits - 1;
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
            if (target_f != null) {
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

    public void Ilerigit() throws JSONException, InterruptedException {

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
            if (target_f != null) {
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



