package com.example.enyako.camera;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    Button btnTakePhoto;
    Button btnLoadImg;
    ImageView imgTakenPhoto;
    TextView textTargetUri;
    //ImageView targetImage;
    Uri capturedImageUri = null;
    String path = "/sdcard/MyImages/img.png";
    File file = new File(path);

    private static final int CAM_REQUEST = 1313;
    private static final int LIB_REQUEST = 1300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePhoto = (Button) findViewById(R.id.button1);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageview1);
        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());

        btnLoadImg = (Button)findViewById(R.id.loadimage);
        textTargetUri = (TextView)findViewById(R.id.targeturi);
        //targetImage = (ImageView)findViewById(R.id.targetimage);
        btnLoadImg.setOnClickListener(new btnAccessPhotoClicker());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CAM_REQUEST){      // TODO access camera
            Bitmap imageBitmap;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                imageBitmap = BitmapFactory.decodeFile(path, options);
                imgTakenPhoto.setImageBitmap(imageBitmap);

                /*note: donno why cant get content uri from file path*/
                //Uri uri = Uri.parse("file://"+path);
                //textTargetUri.setText(uri.toString());
                textTargetUri.setText(capturedImageUri.toString());
                //imgTakenPhoto.setImageURI(uri);
                //imgTakenPhoto.setImageURI(capturedImageUri);

            } catch (Exception e) {
                Toast.makeText(this, "Picture Not taken",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


        }else if(resultCode == RESULT_OK && requestCode == LIB_REQUEST){        // TODO access library
            Uri targetUri = data.getData();
            textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                imgTakenPhoto.setImageBitmap(bitmap);
                //imgTakenPhoto.setImageURI(targetUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    class btnTakePhotoClicker implements Button.OnClickListener
    {

        @Override
        public void onClick(View v) {
            capturedImageUri = Uri.fromFile(file);

            Intent cameraintent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
            startActivityForResult(cameraintent, CAM_REQUEST);
        }

    }


    class btnAccessPhotoClicker implements Button.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, LIB_REQUEST);
        }

    }




}
