package com.matchify.photoviewprototype;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {


    ImageView mImageView;
    public static final int PHOTO_CHOOSER_REQUEST_CODE = 1;
    public static final int CAMERA_CHOOSER_REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView)findViewById(R.id.stretched_image);

        /*Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select chooser"),5);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void choosePhoto(View view){

        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select chooser"),PHOTO_CHOOSER_REQUEST_CODE);
    }

    public void takePhoto(View view){

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_CHOOSER_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.v("REQUEST_CODE",Integer.toString(requestCode));
        Log.v("RESULT_CODE",Integer.toString(resultCode));
        //Uri selectedImageUri = data.getData();

        if(requestCode == PHOTO_CHOOSER_REQUEST_CODE){

            if(resultCode == RESULT_OK){
                Log.v("WELL INSIDE","WELL INSIDE");
                Uri uri = data.getData();

                if(uri == null){
                    return;
                }

                else{

                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();
                    int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(column_index);

                    Drawable drawable = Drawable.createFromPath(path);
                    mImageView.setImageDrawable(drawable);

                }

            }
        }
        else if(requestCode == CAMERA_CHOOSER_REQUEST_CODE){

           if(resultCode == RESULT_OK){

               Bundle extras = data.getExtras();
               Bitmap imageBitmap = (Bitmap) extras.get("data");
               mImageView.setImageBitmap(imageBitmap);

           }

        }
    }
}
