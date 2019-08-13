package com.lateralx.civom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ContactActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

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

    public void email()
    {
        Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "support@civom.co.in"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry For:");
        intent.putExtra(Intent.EXTRA_TEXT, "Hello!");
        startActivity(intent);

    }
}
