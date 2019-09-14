package com.lateralx.civom;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Context c= this.getBaseContext();
        setupBottomNavigationView(c);

        if (isConnectingToInternet())
            new DownloadTask(HomeScreenActivity.this, "http://35.154.220.170/cube.sfb");
        else
            Toast.makeText(HomeScreenActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();



//        String filename = "cube.sfb";
//        String fileContents = "Hello world!";
//
//
//
//        try {
//            URL url = new URL("http://35.154.220.170/cube.sfb");
//            HttpURLConnection con = null;
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.connect();
//            File file = new File(c.getFilesDir(), filename);
//
//            if (!file.exists()) {
//                file.mkdir();
//              Toast.makeText(c,"Directory Created.",Toast.LENGTH_SHORT).show();
//
//            }
//
//            File outputFile = new File(file, "cube.sfb");
//            if (!outputFile.exists()) {
//                outputFile.createNewFile();
//                Toast.makeText(c,"File Created.",Toast.LENGTH_SHORT).show();
//            }
//
//
//
//            FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location
//
//            InputStream is = con.getInputStream();//Get InputStream for connection
//
//            byte[] buffer = new byte[1024];//Set buffer type
//            int len1 = 0;//init length
//            while ((len1 = is.read(buffer)) != -1) {
//                fos.write(buffer, 0, len1);//Write new file
//            }
//            fos.close();
//            is.close();
//
//            FileOutputStream outputStream;
//
//            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//            outputStream.write(is.read());
//            outputStream.close();
//        } catch (Exception e) {
//            Toast.makeText(c,e.getMessage()+"exception",Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }


    }

    public void setupBottomNavigationView(Context context) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            View v = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_ar:
                        Toast.makeText(context, "View in AR", Toast.LENGTH_SHORT).show();
                        openARView(v);
                        break;
                    case R.id.navigation_home:
                        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
                        openHome(v);
                        break;
                    case R.id.navigation_contact:
                        Toast.makeText(context, "Contact", Toast.LENGTH_SHORT).show();

                        openContact(v);
                        break;
                }
                return true;
            }
        });

    }

    public void openHome(View v) {
        Intent i = new Intent(this,HomeScreenActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void openARView(View v) {
        Intent i = new Intent(this,ViewInARActivity.class);
        startActivity(i);
    }


    public void openContact(View v){
        Intent i = new Intent(this,ContactActivity.class);
        startActivity(i);
    }

    public void openCatalogue(View v){
        Intent i = new Intent(this,CategoryActivity.class);
        startActivity(i);
    }
    public void openWhyCivom(View v){
        Intent i = new Intent(this,whycivomActivity.class);
        startActivity(i);
    }
    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
