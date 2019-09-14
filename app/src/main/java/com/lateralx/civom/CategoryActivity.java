package com.lateralx.civom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        c= this.getBaseContext();
        setupBottomNavigationView(c);
        ViewPager viewPager = findViewById(R.id.productviewPager);

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
      //  ImageView pid =v.findViewById(R.id.productimg);
        TextView desc = v.findViewById(R.id.productdesc);
        TextView textthumbnail = v.findViewById(R.id.textthumbnail);
//        RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
//        Call<RetroPhoto> call = service.getPhoto(Integer.getInteger((String) pid.getText()));
//        call.enqueue(new Callback<RetroPhoto>() {
//            @Override
//            public void onResponse(Call<RetroPhoto> call, Response<RetroPhoto> response) {
                i.putExtra("name",String.valueOf(t.getText()));
                i.putExtra("description",String.valueOf(desc.getText()));
                i.putExtra("thumbnail",String.valueOf(textthumbnail.getText()));
          //      i.putExtra("thumbnail",String.valueOf(pid.getText()));

//            }
//
//
//
//            @Override
//            public void onFailure(Call<RetroPhoto> call, Throwable t) {
//
//              Toast.makeText(c,t.getMessage(),Toast.LENGTH_LONG).show();
//                call.clone();
//            }
//        });



        startActivity(i);
    }
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
