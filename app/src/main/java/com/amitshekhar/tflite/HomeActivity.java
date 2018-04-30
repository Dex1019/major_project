package com.amitshekhar.tflite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    ImageView mCaptureImage, mLoadImage;
    EditText mQuerySearch;
    Button mSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mCaptureImage = findViewById(R.id.iv_capture_image);
        mLoadImage = findViewById(R.id.iv_load_image);

//        mQuerySearch = findViewById(R.id.search_text);
//        mSearchBtn = findViewById(R.id.search_btn);


        mCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        mLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ImageActivity.class));
            }
        });


//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String Result = mQuerySearch.getText().toString();
//                // String BaseUrl = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=";
//                String BaseUrl = "https://en.wikipedia.org/wiki/";
//
//                String newUrl = BaseUrl + Result;
//
//                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                CustomTabsIntent customTabsIntent = builder.build();
//                customTabsIntent.launchUrl(HomeActivity.this, Uri.parse(newUrl));
//
//            }
//        });
    }
}
