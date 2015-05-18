package com.example.bsrakdag.pootle;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
 * Created by bsrakdag on 14.05.2015.
 */
public class FragmentFile extends Fragment {
    String storepath;
    String storepath_need_translate,units_path_send;
    String username;
    String password;
    String need_url_units_path,need_url_unis;
    String hostname;
    String url;
    String need_url;
    Integer toplam;
    String sonuc1;
    String gecicisonuc;
    Integer sonuc;
    Integer indeks;
    Integer sonuc3,sonuc4,sonuc5;
    Integer cevirigerekli;
    Integer total_units;
    String need;
    String url3;
    String storearray[];
    String responses;
    String languages_name;
    int row_indeks;

    int indeksof=0;
    int indeksof2=0;


    ArrayList<String> filelist = new ArrayList<String>();
    ArrayList<String> need_translate_list = new ArrayList<String>();
    ArrayList<Integer> totallist = new ArrayList<Integer>();
    ArrayList<Integer> cevirilist = new ArrayList<Integer>();
    String ciktii;

    TableLayout files_table;
    TextView file;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_files, container, false);
        FragmentFile.this.ciktii=this.getArguments().getString("login_cikti");
        FragmentFile.this.storepath = this.getArguments().getString("arrstore_cikti");
        FragmentFile.this.username= this.getArguments().getString("username");
        FragmentFile.this.password= this.getArguments().getString("password");
        FragmentFile.this.hostname= this.getArguments().getString("server");
        FragmentFile.this.languages_name= this.getArguments().getString("languages_name");

        //GERİ DÖNMEK İÇİN
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    //fragment değişimi için
                    FragmentManager fm= getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    FragmentLanguage fragment = new FragmentLanguage();
                    Bundle bundle = new Bundle();
                    bundle.putString("login_cikti", ciktii);
                    bundle.putString("arrstore_cikti",storepath);
                    bundle.putString("username", username);
                    bundle.putString("password", password);
                    bundle.putString("hostname", hostname);
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



        indeksof=storepath.indexOf(",");

        if(indeksof!=-1){

            //birden çok gelen dosya yollarını , ile ayırır bir diziye atar.
            storepath=storepath.substring(1,storepath.length()-1);
            System.out.println("ŞİMDİ BAKK "+ storepath);
            storearray = storepath.split(",");
            for(int i=0;i<storearray.length;i++){

                storearray[i]=storearray[i].substring(1,storearray[i].length()-1);



                //dosyaadlarını listeler
                url = "http://" + hostname + storearray[i];
                System.out.println(url);
                String responseStr= null;
                try {
                    responseStr = baglan2(url);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject obj = new JSONObject(responseStr);
                    String fname = obj.getString("name");
                    fname=fname.substring(0,20);
                    filelist.add(fname);

                    url3 = "http://" + hostname + storearray[i]+"statistics/";
                    String calculator= baglan2(url3);
                    System.out.println("CAALLCULATORR URLL:  " + calculator);
                    toplam = toplamHesapla(calculator);
                    totallist.add(toplam);

                    cevirigerekli = CevirigerekliHesapla(calculator);
                    cevirilist.add(cevirigerekli);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }else{
            indeksof2=storepath.indexOf("\"");


            if(indeksof!=-1){

                //birtane gelen dosya yolunu diziye atar.
                storearray[0]=storepath.substring(2, storepath.length() - 2);


                //her dosya yoluna tek tek giderek dosya adlarını filelist e atar.

                //dosyaadlarını listeler
                url = "http://" + hostname + storearray[0];
                System.out.println(url);
                String responseStr= null;
                try {
                    responseStr = baglan2(url);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject obj = new JSONObject(responseStr);
                    String fname = obj.getString("name");
                    fname=fname.substring(0,fname.length()-10);
                    filelist.add(fname);

                    url3 = "http://" + hostname + storearray[0]+"statistics/";
                    String calculator= baglan2(url3);
                    System.out.println("CAALLCULATORR URLL:  " + calculator);
                    toplam = toplamHesapla(calculator);
                    totallist.add(toplam);

                    cevirigerekli = CevirigerekliHesapla(calculator);
                    cevirilist.add(cevirigerekli);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else{
                //hiç dosya yolu yok uyarı versin.
                storearray=null;

            }


        }



        TableLayout stk = (TableLayout) rootView.findViewById(R.id.tablerow);
        TableRow tbrow0 = new TableRow(getActivity());
        TextView tv0 = new TextView(getActivity());
        tv0.setText("    ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(R.string.files);
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(R.string.total);
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(R.string.needtranslate);
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        tbrow0.setId(0);
        stk.addView(tbrow0);

        //dosya sayısı kadar satır oluşturur.
        for (int i=0; i<filelist.size();i++) {
            //özellikler eklenecek

            final TableRow tbrow = new TableRow(getActivity());

            tbrow.setId(i+1);

            //her satıra sırayla dosya ikonu , dosya adı, total, çeviri gerekli olan kısmın değerlerini atar.

            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.textfile);// you need to get the resource ID of the image (or URL)
            imageView.setLayoutParams(new TableRow.LayoutParams(60, 60));
            tbrow.addView(imageView,0);

            //dosya adıı
            TextView text = new TextView(getActivity());
            text.setText(filelist.get(i).toString());
            text.setTextColor(Color.BLACK);
            final int finalI = i;
            final int finalI1 = i;
            tbrow.addView(text,1);

            // /statistics/ toplam çevrilmesi gereken kelime
            TextView text2 = new TextView(getActivity());
            text2.setText(totallist.get(i).toString());
            text2.setTextColor(Color.BLACK);

            //eğer buraya tıklandıysa sırayla bütün /api/v1/units/66252/ leri çeviride aç
            text2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    need="0";
                    // TODO Auto-generated method stub
                    String path = storearray[finalI1];
                    System.out.println(path);

                    //fragment değişimi için
                    FragmentManager fm= getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    FragmentTranslate fragment = new FragmentTranslate();
                    Bundle bundle = new Bundle();
                    bundle.putString("path", path);
                    bundle.putString("login_cikti", ciktii);
                    bundle.putString("need", need);
                    bundle.putString("arrstore_cikti", storepath);
                    bundle.putString("username", username);
                    bundle.putString("hostname", hostname);
                    bundle.putString("password", password);
                    bundle.putString("languages_name", languages_name);

                    fragment.setArguments(bundle);


                    transaction.replace(R.id.container, fragment);
                    transaction.commit();


                }
            });
            tbrow.addView(text2,2);


            // state=50 olan unitlseri sırasıyla çeviride aç
            // tek tek unitslere gidip state=50 olanları need_translate_list e atıyacak sonra bu list string şeklinde karşışaya yollanacak.
            TextView text3 = new TextView(getActivity());
            text3.setText(cevirilist.get(i).toString());
            text3.setTextColor(Color.BLACK);

            //eğer buraya tıklandıysa (need=1)state=0 olanların yolunu diğer activity e yollar.
            text3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    //tranlsatepage te ona göre çalışacak

                    need="1";

                    //bağlanılması gereken dosya yolu:
                    storepath_need_translate= storearray[tbrow.getId()-1].toString();
                    System.out.println("/////store_need_translate"+ storepath_need_translate );

                    //şimdi unitsler çekilecek
                    need_url= "http://" + hostname + storepath_need_translate;
                    System.out.println("/////NEED_URL"+ need_url );
                    String units_path= null;
                    try {
                        units_path = baglan2(need_url);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {


                        JSONObject units_obj = new JSONObject(units_path);
                        JSONArray arr = units_obj.getJSONArray("units");
                        total_units = arr.length();

                        //units lere tek tek bağlan içinde state=50 olanları need_translate_list ekle
                        for(int i=0;i<total_units;i++){
                            need_url_units_path= arr.get(i).toString();

                            //artık units url si hazır bağlan:
                            need_url_unis="http://" + hostname + need_url_units_path;
                            String need_url_unis_response= baglan2(need_url_unis);
                            //state=50 olanı çek
                            JSONObject need_obj_units = new JSONObject(need_url_unis_response);
                            String cevap = need_obj_units.getString("state");
                            if(Integer.parseInt(cevap)==50 || Integer.parseInt(cevap)==0){
                                need_translate_list.add(need_url_units_path);

                            }

                        }

                        //şimdi need_translte_list i string hale getir ve Translate activity e yolla.
                        for(int i=0; i<need_translate_list.size();i++){
                            //fazladan , olmasın diye if le kontrol ediyorum yoksa string sonunda , oluyor.
                            if(need_translate_list.size()-1!=i){
                                units_path_send=need_translate_list.get(i) + ",";

                            }else{
                                units_path_send=units_path_send+need_translate_list.get(i);
                            }
                        }
                        System.out.println("STATE=50 OLAN UNİTSLERİN PATH STRİNGİ:"+ units_path_send );

                        //fragment değişimi için
                        FragmentManager fm= getFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        FragmentTranslate fragment = new FragmentTranslate();
                        Bundle bundle = new Bundle();
                        bundle.putString("path", units_path_send);
                        bundle.putString("need", need);
                        bundle.putString("username", username);
                        bundle.putString("hostname", hostname);
                        bundle.putString("password", password);
                        bundle.putString("languages_name", languages_name);
                        bundle.putString("arrstore_cikti", storepath);

                        fragment.setArguments(bundle);


                        transaction.replace(R.id.container, fragment);
                        transaction.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            });
            tbrow.addView(text3,3);

            stk.addView(tbrow);
        }


        return rootView;
    }
    public Integer toplamHesapla( String parcalanacakkelime){

        //çevrilecek ve çevrilmesi gereken çevirileri hesaplar


        try {
            JSONObject obj = new JSONObject(parcalanacakkelime);
            sonuc1= obj.getString("statistics");
            System.out.println("statistics sonuccc: "+ sonuc1);
            JSONObject obj2 = new JSONObject(sonuc1);
            sonuc1= obj2.getString("total");
            System.out.println("totall sonuccc: "+ sonuc1);

            JSONObject obj3 = new JSONObject(sonuc1);
            sonuc= obj3.getInt("words");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return sonuc;
    }

    public Integer CevirigerekliHesapla(String parcalanacakkelime){

        try {

            //  unstranslated + statistics/ fuzzy/ words = çeviri gerekli kelimeler
            JSONObject obj = new JSONObject(parcalanacakkelime);
            gecicisonuc= obj.getString("statistics");
            System.out.println("birincii statistics sonuccc: "+ gecicisonuc);
            JSONObject obj2 = new JSONObject(gecicisonuc);
            gecicisonuc= obj2.getString("untranslated");
            System.out.println("birincii totall sonuccc: "+ gecicisonuc);
            JSONObject obj3 = new JSONObject(gecicisonuc);
            sonuc3= obj3.getInt("words");

            gecicisonuc=null;
            JSONObject obj4 = new JSONObject(parcalanacakkelime);
            gecicisonuc= obj4.getString("statistics");
            System.out.println("ikinccii statistics sonuccc: "+ gecicisonuc);
            JSONObject obj5 = new JSONObject(gecicisonuc);
            gecicisonuc= obj5.getString("fuzzy");
            System.out.println("ikinciii totall sonuccc: "+ gecicisonuc);
            JSONObject obj6 = new JSONObject(gecicisonuc);
            sonuc4= obj6.getInt("words");

            sonuc5=sonuc3+sonuc4;






        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sonuc3;
    }


    //baglantı yapar ve string şeklinde sonuc gösterir.

    public String baglan2(String urll) throws InterruptedException {

        final String urlem = urll;
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                //sunucuya bağlanma ve ordan language api lerini çekme
                DefaultHttpClient httpclient = new DefaultHttpClient();
                try {
                    httpclient.getCredentialsProvider().setCredentials(
                            new AuthScope(hostname, 80),
                            new UsernamePasswordCredentials(username, password));

                    HttpGet httpget = new HttpGet(urlem);


                    HttpResponse response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();


                    if (entity != null) {
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

}
