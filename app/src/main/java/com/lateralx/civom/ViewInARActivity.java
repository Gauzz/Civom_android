package com.lateralx.civom;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.lateralx.civom.Adapter.ARCardFragmentPagerAdapter;
import com.lateralx.civom.Model.RetroPhoto;
import com.lateralx.civom.Network.RetrofitClientInstance;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewInARActivity extends AppCompatActivity {
    private static final String TAG = ViewInARActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private ArFragment arFragment;
    private ModelRenderable andyRenderable;
    private String df;
    private Uri model;
    private ViewPager viewPager;
    private List<RetroPhoto> lrp;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_in_ar);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
//        if(!previouslyStarted) {
//            SharedPreferences.Editor edit = prefs.edit();
//            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
//            edit.commit();
//            openIntro();
//        }

        DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.product_storychair3x)
                .build();
        ImageLoaderConfiguration imgConfig = new ImageLoaderConfiguration.Builder(ViewInARActivity.this)
                .defaultDisplayImageOptions(imgOptions)
                .build();
        ImageLoader.getInstance().init(imgConfig);
        Intent i = getIntent();
        Bundle b =i.getExtras();
        if(b!=null)
        {
             String ar=(String) b.get("ar");
            selectModel(ar.replace("https://sales.lateralx.com/temp/",""));
        //    if(b.get("done").toString() != "done")
                Toast.makeText(ViewInARActivity.this,"Please Wait while the model downloads",Toast.LENGTH_LONG).show();

        }
        else {
            RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<List<RetroPhoto>> call = service.getAllPhotos();
            call.enqueue(new Callback<List<RetroPhoto>>() {
                @Override
                public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                    lrp = response.body();

                    if (isConnectingToInternet())
                        for (int i = 0; i < lrp.size(); i++) {
                            if (lrp.get(i).getFbx() != null && lrp.get(i).getFbx() != "") {
                                new DownloadTask(ViewInARActivity.this, lrp.get(i).getFbx());

                            }
                        }
                    else
                        Toast.makeText(ViewInARActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();

                    viewPager = (ViewPager) findViewById(R.id.viewPager);
                    ARCardFragmentPagerAdapter pagerAdapter = new ARCardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, ViewInARActivity.this), lrp);
                    ARShadowTransformer fragmentCardARShadowTransformer = new ARShadowTransformer(viewPager, pagerAdapter);
                    fragmentCardARShadowTransformer.enableScaling(true);

                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setPageTransformer(false, fragmentCardARShadowTransformer);
                    viewPager.setOffscreenPageLimit(5);
                    //selectModel();

                    if (!checkIsSupportedDeviceOrFinish(ViewInARActivity.this)) {
                        return;
                    }
                    fragmentCardARShadowTransformer.setArModel(ViewInARActivity.this);


                 //   arFragment.getArSceneView().getScene().addOnPeekTouchListener(this::handleOnTouch);

                }


                @Override
                public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                    Toast.makeText(ViewInARActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().



        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (andyRenderable == null) {
                        Toast.makeText(ViewInARActivity.this,"Please Wait while the model downloads",Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable andy and add it to the anchor.
                    TransformableNode object = new TransformableNode(arFragment.getTransformationSystem());
                    object.setParent(anchorNode);
                    object.setRenderable(andyRenderable);
                    object.getScaleController().setMinScale(0.99f);
                    object.getScaleController().setMaxScale(1.0f);
                    object.select();


                });

    }


    public void selectModel(String modelname) {

       model=Uri.parse(Environment.getExternalStorageDirectory() + "/"
                       + Utils.downloadDirectory+"/"+modelname);
        ModelRenderable.builder()
                .setSource(this, model)
                .build()
                .thenAccept(renderable -> andyRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toastm =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toastm.setGravity(Gravity.CENTER, 0, 0);
                            toastm.show();
                            return null;
                        });
    }

    private void openIntro() {
        Intent i = new Intent(this,IntroActivity.class);
        Intent i2 = getIntent();
        Bundle b =i2.getExtras();
        if(b!=null)
        {
            String ar=(String) b.get("ar");
            i.putExtra("ar",b.get("ar").toString());

        }

        startActivity(i);
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
    public void downModel(View v){

        TextView modname =(TextView)v.findViewById(R.id.textView6);
        if (isConnectingToInternet()) {
            new DownloadTask(ViewInARActivity.this, modname.getText().toString());

        }
        else
            Toast.makeText(ViewInARActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();
        if(modname.getText().toString().contains("temp"))
        selectModel(modname.getText().toString().replace("https://sales.lateralx.com/temp/",""));
        else
            selectModel(modname.getText().toString().replace("https://raw.githubusercontent.com/Gauzz/civommodels/master/",""));

    }



    public void deleteModel(View view) {
        HitTestResult hitTestResult;
        MotionEvent motionEvent;


    }
}
