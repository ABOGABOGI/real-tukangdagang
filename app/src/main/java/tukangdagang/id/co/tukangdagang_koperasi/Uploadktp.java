package tukangdagang.id.co.tukangdagang_koperasi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance2;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance3;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_status_upload;

public class Uploadktp extends AppCompatActivity {
Button btnUpload;
ImageView imgUpload,imgUpload2,imgUpload3;
Bitmap bitmap,bitmap2,bitmap3;
    public static final int CODE_GALLERY_REQUEST = 999;
    public static final int CODE_CAMERA_REQUEST = 998;
private String [] items = {"Camera","Gallery"};
int nn = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadktp);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Upload KTP");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnUpload = (Button) findViewById(R.id.btnUpload);
        imgUpload = (ImageView) findViewById(R.id.imgupload);
        imgUpload2 = (ImageView) findViewById(R.id.imgupload2);
        imgUpload3 = (ImageView) findViewById(R.id.imgupload3);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String photo = sharedPreferences.getString(n_imagePreferance, "photo");
        String photo2 = sharedPreferences.getString(n_imagePreferance2, "photo");
        String photo3 = sharedPreferences.getString(n_imagePreferance3, "photo");
        assert photo != null;
        assert photo2 != null;
        assert photo3 != null;
        if(!photo.equals("photo"))
        {
            byte[] b = Base64.decode(photo, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(b);
            bitmap = BitmapFactory.decodeStream(is);
            imgUpload.setImageBitmap(bitmap);
        }
        if(!photo2.equals("photo"))
        {
            byte[] b = Base64.decode(photo2, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(b);
            bitmap = BitmapFactory.decodeStream(is);
            imgUpload2.setImageBitmap(bitmap);
        }
        if(!photo3.equals("photo"))
        {
            byte[] b = Base64.decode(photo3, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(b);
            bitmap = BitmapFactory.decodeStream(is);
            imgUpload3.setImageBitmap(bitmap);
        }
//        btnUpload.setEnabled(false);

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
                nn =1;

            }
        });
        imgUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
                nn =2;
            }
        });
        imgUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
                nn =3;
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                String photo = sharedPreferences.getString(n_imagePreferance, "photo");
                String photo2 = sharedPreferences.getString(n_imagePreferance2, "photo");
                String photo3 = sharedPreferences.getString(n_imagePreferance3, "photo");
                if(!photo.equals("photo") && !photo2.equals("photo") && !photo3.equals("photo")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(n_status_upload, "1");
                    editor.commit();
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(),"Data belum lengkap,Pastikan data di isi semua",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void openImage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera")){
                    EasyImage.openCamera(Uploadktp.this,CODE_CAMERA_REQUEST);
                }else if(items[i].equals("Gallery")){
                    EasyImage.openGallery(Uploadktp.this, CODE_GALLERY_REQUEST);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
        @Override
        public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
            SharedPreferences sharedPreferences = Uploadktp.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            switch (type){
                case CODE_CAMERA_REQUEST:

                    try {
               InputStream inputStream = getContentResolver().openInputStream(Uri.fromFile(imageFile));
               Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

               if(nn==1) {
                   imgUpload.setImageBitmap(bitmap);
                   bitmap = ((BitmapDrawable) imgUpload.getDrawable()).getBitmap();
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString(n_imagePreferance, imageToString(bitmap));
                   editor.commit();

               }else if(nn==2){
                   imgUpload2.setImageBitmap(bitmap);
                   bitmap = ((BitmapDrawable) imgUpload2.getDrawable()).getBitmap();
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString(n_imagePreferance2, imageToString(bitmap));
                   editor.commit();
               }
               else if(nn==3){
                   imgUpload3.setImageBitmap(bitmap);
                   bitmap = ((BitmapDrawable) imgUpload3.getDrawable()).getBitmap();
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString(n_imagePreferance3, imageToString(bitmap));
                   editor.commit();
               }
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }
                    break;
                case CODE_GALLERY_REQUEST:
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(Uri.fromFile(imageFile));
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        btnUpload.setEnabled(true);
                        if(nn==1) {
                            imgUpload.setImageBitmap(bitmap);
                            bitmap = ((BitmapDrawable) imgUpload.getDrawable()).getBitmap();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(n_imagePreferance, imageToString(bitmap));
                            editor.commit();
                        }else if(nn==2){
                            imgUpload2.setImageBitmap(bitmap);
                            bitmap = ((BitmapDrawable) imgUpload2.getDrawable()).getBitmap();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(n_imagePreferance2, imageToString(bitmap));
                            editor.commit();
                        }else if(nn==3){
                            imgUpload3.setImageBitmap(bitmap);
                            bitmap = ((BitmapDrawable) imgUpload3.getDrawable()).getBitmap();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(n_imagePreferance3, imageToString(bitmap));
                            editor.commit();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }         break;
            }

        }
    });
}


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

}
