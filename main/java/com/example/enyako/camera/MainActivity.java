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
    ImageView imgTakenPhoto;
    TextView textTargetUri;
    ImageView targetImage;
    static Uri capturedImageUri = null;
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

        Button buttonLoadImage = (Button)findViewById(R.id.loadimage);
        textTargetUri = (TextView)findViewById(R.id.targeturi);
        targetImage = (ImageView)findViewById(R.id.targetimage);
        buttonLoadImage.setOnClickListener(new btnAccessPhotoClicker());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CAM_REQUEST)
        {
            // find the empty temp file created before the intent
            /*File file = new File(Environment.getExternalStorageDirectory().toString());
            for(File temp: file.listFiles()) {
                if(temp.getName().equals("temp.jpg")){
                    file = temp;
                    break;
                }// end of if
            }// end of for

            try{
                Bitmap bitmap =  (Bitmap) data.getExtras().get("data");
                //BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                //bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imgTakenPhoto.setImageBitmap(bitmap);

                String path = Environment.getExternalStorageDirectory()
                        + "/Camera/";
                OutputStream outFile = null;
                File file2 = new File(path, "cameraImg.jpg");
                try{
                    outFile =new FileOutputStream(file2);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                    outFile.flush();
                    outFile.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }// end of write file try catch

            }catch (Exception e){
                e.printStackTrace();
            }*/// end of read and write file try catch


            /*Uri targetUri = data.getData();
            textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                imgTakenPhoto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO found no libraryfile
                e.printStackTrace();
            }*/
            //try {
                //Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                //imgTakenPhoto.setImageBitmap(thumbnail);
                //Toast.makeText(this, "Image saved to:\n" +
                //    data.getData(), Toast.LENGTH_LONG).show();
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uriSavedImage);
                //imgTakenPhoto.setImageBitmap(bitmap);
            /*} catch (FileNotFoundException e) {
                // TODO found no camerafile
                e.printStackTrace();
            } catch (IOException e) {
                // TODO fail to find camerafile
                e.printStackTrace();
            }*/

            try {
                // Call function MakeFolder to create folder structure if
                // its not created
                Bitmap imageBitmap;
                //MakeFolder();
                // Get file from temp1 file where image has been stored
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                imageBitmap = BitmapFactory.decodeFile(path, options);
                imgTakenPhoto.setImageBitmap(imageBitmap);
                //Uri uri = Uri.fromFile(file);
                textTargetUri.setText(capturedImageUri.toString());
                //Uri ImageUri = Uri.fromFile(file);
                //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));
                //imgTakenPhoto.setImageBitmap(bitmap);


                //isImageTaken = true;
                // Name for image
                //IMAGEPATH = getString(R.string.chassisImage)
                //        + System.currentTimeMillis();
                //SaveImageFile(imageBitmap,IMAGEPATH);
            } catch (Exception e) {
                Toast.makeText(this, "Picture Not taken",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


        }else if(resultCode == RESULT_OK && requestCode == LIB_REQUEST){
            Uri targetUri = data.getData();
            textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO found no libraryfile
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
            // TODO request access photo library
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, LIB_REQUEST);
        }

    }




}
