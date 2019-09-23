package com.lateralx.civom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    HomeScreenActivity h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Context c= this;
        setupBottomNavigationView(c);
    }

    public void setupBottomNavigationView(Context context) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_ar:
                        Toast.makeText(context, "View in AR", Toast.LENGTH_SHORT).show();
                        openARView();
                        break;
                    case R.id.navigation_home:
                        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
                        openHome();
                        break;
                    case R.id.navigation_contact:
                        Toast.makeText(context, "Contact", Toast.LENGTH_SHORT).show();
                        View v = null;
                        openContact(v);
                        break;
                }
                return true;
            }
        });

    }


    //for dialer app
    public  void call(View v)
    {
        Uri u = Uri.parse("tel:" + "987654321");

        // Create the intent and set the data for the
        // intent as the phone number.
        Intent i = new Intent(Intent.ACTION_DIAL, u);

        try
        {
            // Launch the Phone app's dialer with a phone
            // number to dial a call.
            startActivity(i);
        }
        catch (SecurityException s)
        {
            // show() method display the toast with
            // exception message.
        }
    }

    public void email(View v)
    {
        Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "support@civom.co.in"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry For:");
        intent.putExtra(Intent.EXTRA_TEXT, "Hello!");
        startActivity(intent);

    }

    public void openHome() {
        Intent i = new Intent(this,HomeScreenActivity.class);
        startActivity(i);
    }

    public void openARView() {
        Intent i = new Intent(this,ViewInARActivity.class);
        startActivity(i);
    }


    public void openContact(View v){
        Intent i = new Intent(this,ContactActivity.class);
        startActivity(i);
    }
}
