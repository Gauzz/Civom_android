package com.lateralx.civom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lateralx.civom.Adapter.CustomAdapter;
import com.lateralx.civom.Model.RetroPhoto;
import com.lateralx.civom.Network.RetrofitClientInstance;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private String term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        Context c= this.getBaseContext();
        setupBottomNavigationView(c);
        Intent i = getIntent();
        Bundle b =i.getExtras();
        if(b!=null)
        {
            term =(String) b.get("term");

        }
        DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.civom_logo_lowres)
                .build();
        ImageLoaderConfiguration imgConfig = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(imgOptions)
                .build();
        ImageLoader.getInstance().init(imgConfig);
        progressDoalog = new ProgressDialog(RetrofitActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
        Call<List<RetroPhoto>> call = service.search(term);
        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void generateDataList(List<RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RetrofitActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
        Intent i = new Intent(this,RetrofitActivity.class);
        startActivity(i);
    }

    public void viewProduct(View view) {
        Intent i = new Intent(RetrofitActivity.this, ProductActivity.class);
        TextView t =view.findViewById(R.id.title0);
        TextView desc = view.findViewById(R.id.textdescription);
        TextView textthumbnail = view.findViewById(R.id.textthumb);
        TextView textar = view.findViewById(R.id.textpar);
        i.putExtra("name",String.valueOf(t.getText()));
        i.putExtra("description",String.valueOf(desc.getText()));
        i.putExtra("thumbnail",String.valueOf(textthumbnail.getText()));
        i.putExtra("ar",String.valueOf(textar.getText()));
        startActivity(i);

    }
}
