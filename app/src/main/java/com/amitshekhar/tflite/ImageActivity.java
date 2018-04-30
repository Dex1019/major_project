package com.amitshekhar.tflite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImageActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 100;
    ImageView mLoadImage;
    TextView mtextResult;

    private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite";
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;

//    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();

    private Classifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        mLoadImage = findViewById(R.id.iv_load_internal_image);
        mtextResult = findViewById(R.id.tv_internal_image_result);

        mLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        initTensorFlowAndLoadModel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                    mLoadImage.setImageBitmap(bitmap);// To display selected image in image view
                    showLocalResult(bitmap);
                }

        }


    }

    private void showLocalResult(Bitmap bitmap) {
        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
        String s = results.toString();
        s = s.substring(1, s.length() - 1);
        s = s.replace(",", "\n");
        mtextResult.setText(s);
    }


    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE);

//                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }
}
