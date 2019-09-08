package com.lateralx.civom;

import android.content.Intent;
import android.os.Bundle;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends OnboarderActivity {
    List<OnboarderPage> onboarderPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboarderPages = new ArrayList<OnboarderPage>();

        // Create your first page
        OnboarderPage onboarderPage1 = new OnboarderPage("Tap", "To place an object in your space",R.drawable.tapadd);

        OnboarderPage onboarderPage2 = new OnboarderPage("Tap and Drag", "To move object in your space",R.drawable.tapdrag);

        OnboarderPage onboarderPage3 = new OnboarderPage("Two finger Rotate", "To rotate the object in your space",R.drawable.rotatepng);

        // You can define title and description colors (by default white)
        onboarderPage1.setTitleColor(R.color.colorPrimary);
        onboarderPage1.setDescriptionColor(R.color.colorPrimary);

        // Don't forget to set background color for your page
        onboarderPage1.setBackgroundColor(R.color.intro1);
        onboarderPage2.setBackgroundColor(R.color.intro2);
        onboarderPage3.setBackgroundColor(R.color.intro3);

        // Add your pages to the list
        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        onboarderPages.add(onboarderPage3);
        // And pass your pages to 'setOnboardPagesReady' method
        setOnboardPagesReady(onboarderPages);

    }

    @Override
    public void onFinishButtonPressed()
    {
        Intent i = new Intent(this,ViewInARActivity.class);
        startActivity(i);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onSkipButtonPressed() {
        super.onSkipButtonPressed();
    }


}
