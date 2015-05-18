package com.example.bsrakdag.pootle;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bsrakdag on 23.04.2015.
 */
public class AddServerFragment extends Fragment {
    ArrayList<String> listserver = new ArrayList<String>();
    String gecici, gecici2;
    public String[] geciciserver;
    Button addserverbutton, removeserverbutton;
    EditText servername, serverpassword, servernickname, username;
    Spinner serverlist;
    TextView empty;
    int indeks;
    ArrayAdapter<String> dataAdapter;
    private Veritabani dataBase;
    private String[] sutunlar = {"_servername", "username", "password", "nickname"};

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_server,
                container, false);
        //GERİ DÖNMEK İÇİN
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Fragment içi düzenin oluşturulduğu alan
        AddServerFragment.this.addserverbutton = (Button) view.findViewById(R.id.addserverbutton);
        AddServerFragment.this.removeserverbutton = (Button) view.findViewById(R.id.removeserverbutton);

        AddServerFragment.this.servername = (EditText) view.findViewById(R.id.addservername);
        AddServerFragment.this.servernickname = (EditText) view.findViewById(R.id.addservernickname);
        AddServerFragment.this.serverpassword = (EditText) view.findViewById(R.id.addserverpassword);
        AddServerFragment.this.username = (EditText) view.findViewById(R.id.addserverusername);
        AddServerFragment.this.empty = (TextView) view.findViewById(R.id.emptyservername);


        AddServerFragment.this.serverlist = (Spinner) view.findViewById(R.id.spinnerservername);

        //kayıtlı sunucuları sayfa ilk açıldığında göstermek için

        listserver.clear();
        bilgiGetir();
        if (listserver == null) {
            serverlist.setVisibility(View.INVISIBLE);
            removeserverbutton.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.VISIBLE);
            empty.setText("KAYITLI SUNUCU YOK");

        } else {
            dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listserver);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            serverlist.setAdapter(dataAdapter);
            serverlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    AddServerFragment.this.indeks = arg2;
                    // TODO Auto-generated method stub
                    String selectedAndroid = arg0.getSelectedItem().toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }


            });
        }


        addserverbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                addservername();


            }
        });
        AddServerFragment.this.removeserverbutton = (Button) view.findViewById(R.id.removeserverbutton);
        removeserverbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                removeservername();
                Toast.makeText(getActivity(), "İŞLEM BAŞARILI", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    public void addservername() {


        kayitGir(servername.getText().toString(), username.getText().toString(), serverpassword.getText().toString(), servernickname.getText().toString());
        Toast.makeText(getView().getContext(), "Kaydedildi", Toast.LENGTH_LONG).show();
        servername.setText("");
        username.setText("");
        serverpassword.setText("");
        servernickname.setText("");

        //spinner yeniden yükleniyor.
        listserver.clear();
        bilgiGetir();
        dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listserver);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serverlist.setAdapter(dataAdapter);
        serverlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                AddServerFragment.this.indeks = arg2;
                // TODO Auto-generated method stub
                String selectedAndroid = arg0.getSelectedItem().toString();
                Toast.makeText(getActivity(), "Seçtiğiniz Seçenek: " + selectedAndroid, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

    }

    public void removeservername() {
        String silinecek = listserver.get(indeks);
        geciciserver = silinecek.split(" ");
        silinecek = geciciserver[0];
        AddServerFragment.this.dataBase = new Veritabani(getView().getContext());
        SQLiteDatabase db = dataBase.getWritableDatabase();
        String table_name = "ServerTablosu";
        String where = "_servername='" + silinecek + "'";
        db.delete(table_name, where, null);


        listserver.clear();
        bilgiGetir();
        if (listserver == null) {
            serverlist.setVisibility(View.INVISIBLE);
            removeserverbutton.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.VISIBLE);
            empty.setText("KAYITLI SUNUCU YOK");

        } else {
            dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listserver);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            serverlist.setAdapter(dataAdapter);
            serverlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    AddServerFragment.this.indeks = arg2;
                    // TODO Auto-generated method stub
                    String selectedAndroid = arg0.getSelectedItem().toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }


            });

        }

    }

    private void kayitGir(String name, String username, String password, String nickname) {
        AddServerFragment.this.dataBase = new Veritabani(getView().getContext());
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("_servername", name);
        content.put("nickname", nickname);
        content.put("password", password);
        content.put("username", username);
        db.insertOrThrow("ServerTablosu", null, content);
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
            AddServerFragment.this.listserver.add(i, gecici);
            i = i + 1;
        }
        okunan.close();

    }
}


