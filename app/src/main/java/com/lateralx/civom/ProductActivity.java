package com.lateralx.civom;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ProductActivity extends AppCompatActivity {
    TextView productname;
    TextView productdescription;
    ImageView productimg;
    TextView productar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        productname = findViewById(R.id.productname);
        productdescription = findViewById(R.id.description);
        productimg= findViewById(R.id.productimg);
        productar = findViewById(R.id.textar);
        Context c= this.getBaseContext();
        setupBottomNavigationView(c);

        DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.product_storychair3x)
                .build();
        ImageLoaderConfiguration imgConfig = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(imgOptions)
                .build();
        ImageLoader.getInstance().init(imgConfig);


        Intent i = getIntent();
        Bundle b =i.getExtras();
        if(b!=null)
        {
            String j =(String) b.get("name");
            productname.setText(j);
            j= (String)b.get("description");
            String msg=j.replaceAll("\\\\n","\n\n");
            productdescription.setText(msg);
            String thumbnail=(String)b.get("thumbnail");
            ImageLoader imageLoader = ImageLoader.getInstance();
            j=(String) b.get("ar");
            productar.setText(j);
          Toast.makeText(ProductActivity.this,j,Toast.LENGTH_LONG).show();
            if(thumbnail!= null && thumbnail!= "")
                imageLoader.displayImage("http://35.154.220.170/"+thumbnail, productimg);
            else
                imageLoader.displayImage(String.valueOf(R.drawable.product_storychair3x), productimg);
        }

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


    public void openAR(View view) {
        Intent i = new Intent(this,ViewInARActivity.class);
        productar = findViewById(R.id.textar);
        i.putExtra("ar",productar.getText().toString());
        i.putExtra("done","notdone");

        new DownloadTask(ProductActivity.this, productar.getText().toString());

        if(productar.getText().toString().isEmpty()){
            Toast.makeText(ProductActivity.this,"Sorry This model is currently Unavailable for AR View",Toast.LENGTH_LONG).show();
        }
        else
        startActivity(i);
    }
}
