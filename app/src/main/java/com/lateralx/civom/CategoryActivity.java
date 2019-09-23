package com.lateralx.civom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lateralx.civom.Adapter.CardFragmentPagerAdapter;
import com.lateralx.civom.Model.RetroPhoto;
import com.lateralx.civom.Network.RetrofitClientInstance;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private ProgressDialog progressDoalog;
    private List<RetroPhoto> lrp;
    ViewPager viewPager;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        c= this.getBaseContext();
        setupBottomNavigationView(c);
        viewPager = findViewById(R.id.productviewPager);

        DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.product_storychair3x)
                .build();
        ImageLoaderConfiguration imgConfig = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(imgOptions)
                .build();
        ImageLoader.getInstance().init(imgConfig);

        progressDoalog = new ProgressDialog(CategoryActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
        Call<List<RetroPhoto>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDoalog.dismiss();
                lrp =response.body();
                //generateDataList(response.body());
                CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, c),lrp);
                ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
                fragmentCardShadowTransformer.enableScaling(true);

                viewPager.setAdapter(pagerAdapter);
                viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
                viewPager.setOffscreenPageLimit(3);

            }



            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(CategoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                call.clone();
            }
        });
        final EditText edittext = (EditText) findViewById(R.id.editTextSearch);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchProduct(v);
                    return true;
                }
                return false;
            }
        });

    }

    private void generateDataList(List<RetroPhoto> body) {

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

    public void openProduct(View v){
        Intent i = new Intent(this, ProductActivity.class);
        TextView t =v.findViewById(R.id.titlecard);
        TextView desc = v.findViewById(R.id.productdesc);
        TextView textthumbnail = v.findViewById(R.id.textthumbnail);
        TextView ar =v.findViewById(R.id.productar);

            i.putExtra("name",String.valueOf(t.getText()));
            i.putExtra("description",String.valueOf(desc.getText()));
            i.putExtra("thumbnail",String.valueOf(textthumbnail.getText()));
            i.putExtra("ar",String.valueOf(ar.getText()));
        startActivity(i);
    }
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public void searchProduct(View view) {
        TextView searchtextview = (TextView)findViewById(R.id.editTextSearch);
        String term = searchtextview.getText().toString();
        Intent intent= new Intent(CategoryActivity.this,RetrofitActivity.class);
        intent.putExtra("term",term);
        startActivity(intent);
    }

    public void populate(View view) {
        progressDoalog = new ProgressDialog(CategoryActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        Button button = (Button) view;
        String term = (String)button.getText();
        /*Create handle for the RetrofitInstance interface*/
        RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
        Call<List<RetroPhoto>> call = service.filter(term);
        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDoalog.dismiss();
                lrp =response.body();
                //generateDataList(response.body());
                CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, c),lrp);
                ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
                fragmentCardShadowTransformer.enableScaling(true);

                viewPager.setAdapter(pagerAdapter);
                viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
                viewPager.setOffscreenPageLimit(3);

            }



            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(CategoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                call.clone();
            }
        });
    }

}
