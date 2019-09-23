package com.lateralx.civom;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


import java.lang.reflect.Field;

public class TestimonialActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimonial);
   //     Context c= this.getBaseContext();
   //     setupBottomNavigationView(c);
        VideoView videoView =(VideoView)findViewById(R.id.videoView);

        //Creating MediaController
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        //specify the location of media file

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://com.lateralx.civom/raw/video"));
        videoView.requestFocus();
        videoView.start();
        if(!videoView.isPlaying()){
            Uri mUri = null;
            try {
                Field mUriField = VideoView.class.getDeclaredField("mUri");
                mUriField.setAccessible(true);
                mUri = (Uri)mUriField.get(videoView);
                Toast.makeText(TestimonialActivity.this,mUri.getPath(),Toast.LENGTH_LONG);
            } catch(Exception e) {}
        }

    }

//    public void setupBottomNavigationView(Context context) {
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            View v = null;
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.navigation_ar:
//                        Toast.makeText(context, "View in AR", Toast.LENGTH_SHORT).show();
//                        openARView(v);
//                        break;
//                    case R.id.navigation_home:
//                        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
//                        openHome(v);
//                        break;
//                    case R.id.navigation_contact:
//                        Toast.makeText(context, "Contact", Toast.LENGTH_SHORT).show();
//
//                        openContact(v);
//                        break;
//                }
//                return true;
//            }
//        });
//
//    }
//
//    public void openHome(View v) {
//        Intent i = new Intent(this,HomeScreenActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);
//    }
//
//    public void openARView(View v) {
//        Intent i = new Intent(this,ViewInARActivity.class);
//        startActivity(i);
//    }
//
//
//    public void openContact(View v){
//        Intent i = new Intent(this,ContactActivity.class);
//        startActivity(i);
//    }
//

}
