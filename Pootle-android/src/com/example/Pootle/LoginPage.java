package com.example.Pootle;


import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import java.io.*;
import java.lang.String;
import android.widget.EditText;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.io.InputStream;

public class LoginPage extends Activity {
    /**
     * Called when the activity is first created.
     */
    String username;
    String password;
    String hostname;
    String url;
    EditText text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
}

    public void baglan(View view) throws IOException {
        text =  (EditText)findViewById(R.id.username);
        username=text.getText().toString();


        text = (EditText)findViewById(R.id.passwd);
        password=text.getText().toString();


        text= (EditText)findViewById(R.id.server);
        hostname=text.getText().toString();

        url= "http://"+hostname+":8000/api/v1/";


        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(hostname, 8000),
                    new UsernamePasswordCredentials(username, password));

            HttpGet httpget = new HttpGet(url);

            System.out.println("executing request" + httpget.getRequestLine());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                System.out.println(EntityUtils.toString(entity));
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

}


