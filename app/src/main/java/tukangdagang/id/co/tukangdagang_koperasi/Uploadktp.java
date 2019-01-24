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
        getSupportActionBar().setTitle("Upload KTP");


        btnUpload = (Button) findViewById(R.id.btnUpload);
        imgUpload = (ImageView) findViewById(R.id.imgupload);
        imgUpload2 = (ImageView) findViewById(R.id.imgupload2);
        imgUpload3 = (ImageView) findViewById(R.id.imgupload3);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String photo = sharedPreferences.getString("imagePreferance", "photo");
        String photo2 = sharedPreferences.getString("imagePreferance2", "photo");
        String photo3 = sharedPreferences.getString("imagePreferance3", "photo");
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



//            String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
//            String nama = sharedPreferences.getString(Config.NAME_SHARED_PREF,"Not Available");
//            Log.d("fbb",email);
//            //Showing the current logged in email to textview
//                TextView tv_nama;
//                tv_nama = findViewById(R.id.textView10);
//                tv_nama.setText(nama);
//
//                final ProgressDialog progressDialog = new ProgressDialog(Uploadktp.this);
//                progressDialog.setMessage("Loading...");
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progressDialog.show();
//
//                //post image to server
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URLUpload,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                //If we are getting success from server
//                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
//                                btnUpload.setEnabled(false);
//                                progressDialog.dismiss();
//                                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putString("imagePreferance", null);
//                                editor.putString("imagePreferance2", null);
//                                editor.putString("imagePreferance3", null);
//                                editor.commit();
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                //You can handle error here if you want
//                                Toast.makeText(getApplicationContext(),"Error : "+error.toString(),Toast.LENGTH_SHORT).show();
//                                Log.d("tee",error.toString());
//                            }
//                        }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String,String> params = new HashMap<>();
//                        bitmap = ((BitmapDrawable) imgUpload.getDrawable()).getBitmap();
//                        bitmap2 = ((BitmapDrawable) imgUpload2.getDrawable()).getBitmap();
//                        bitmap3 = ((BitmapDrawable) imgUpload3.getDrawable()).getBitmap();
//                        String imageData = imageToString(bitmap);
//                        String imageData2 = imageToString(bitmap2);
//                        String imageData3 = imageToString(bitmap3);
//                        Log.d("nilai",imageData);
//                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                        String photo = sharedPreferences.getString("imagePreferance", "photo");
//                        String photo2 = sharedPreferences.getString("imagePreferance2", "photo");
//                        String photo3 = sharedPreferences.getString("imagePreferance3", "photo");
//                        //Adding parameters to request
//                        params.put("image", photo);
//                        params.put("image2", photo2);
//                        params.put("image3", photo3);
//
//                        //returning parameter
//                        return params;
//                    }
//                };
//                //Adding the string request to the queue
//                RequestQueue requestQueue = Volley.newRequestQueue(Uploadktp.this);
//                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        10000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                requestQueue.add(stringRequest);
                Intent inten = new Intent(Uploadktp.this,DaftarAnggota.class);
                startActivity(inten);
                finish();

            }
        });


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
                   editor.putString("imagePreferance", imageToString(bitmap));
                   editor.commit();

               }else if(nn==2){
                   imgUpload2.setImageBitmap(bitmap);
                   bitmap = ((BitmapDrawable) imgUpload2.getDrawable()).getBitmap();
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString("imagePreferance2", imageToString(bitmap));
                   editor.commit();
               }
               else if(nn==3){
                   imgUpload3.setImageBitmap(bitmap);
                   bitmap = ((BitmapDrawable) imgUpload3.getDrawable()).getBitmap();
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString("imagePreferance3", imageToString(bitmap));
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
                            editor.putString("imagePreferance", imageToString(bitmap));
                            editor.commit();
                        }else if(nn==2){
                            imgUpload2.setImageBitmap(bitmap);
                            bitmap = ((BitmapDrawable) imgUpload2.getDrawable()).getBitmap();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("imagePreferance2", imageToString(bitmap));
                            editor.commit();
                        }else if(nn==3){
                            imgUpload3.setImageBitmap(bitmap);
                            bitmap = ((BitmapDrawable) imgUpload3.getDrawable()).getBitmap();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("imagePreferance3", imageToString(bitmap));
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
