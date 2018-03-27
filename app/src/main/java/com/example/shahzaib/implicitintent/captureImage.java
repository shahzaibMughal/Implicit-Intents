package com.example.shahzaib.implicitintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class captureImage extends AppCompatActivity {


    int CAPTURE_IMAGE_REQUEST_CODE = 0;


    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        imageView = findViewById(R.id.imageView);

        findViewById(R.id.captureImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,address where you want to save the captured image);

                if(intent.resolveActivity(getPackageManager())!=null)
                {
                    startActivityForResult(intent,CAPTURE_IMAGE_REQUEST_CODE);
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get and show the received thumnail of the captured image
        if(requestCode == CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageThumbnail = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageThumbnail);
        }

    }
}
