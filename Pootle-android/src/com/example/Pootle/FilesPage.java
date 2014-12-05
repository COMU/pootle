package com.example.Pootle;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by busra on 26.11.2014.
 */
public class FilesPage extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        String pname;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.filespage);

        pname=getIntent().getStringExtra("projectname");
    }
}
