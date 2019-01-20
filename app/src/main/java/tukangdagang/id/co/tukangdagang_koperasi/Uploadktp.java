package tukangdagang.id.co.tukangdagang_koperasi;

import android.Manifest;
import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

public class Uploadktp extends AppCompatActivity {
Button btnChoose, btnUpload;
ImageView imgUpload;
Bitmap bitmap;
int CODE_GALLERY_REQUEST = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadktp);
        getSupportActionBar().setTitle("Upload KTP");

        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        imgUpload = (ImageView) findViewById(R.id.imgupload);
        btnUpload.setEnabled(false);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        Uploadktp.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //post image to server
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URLUpload,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                btnUpload.setEnabled(false);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                                Toast.makeText(getApplicationContext(),"Error : "+error.toString(),Toast.LENGTH_SHORT).show();
                                Log.d("tee",error.toString());
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        bitmap = ((BitmapDrawable) imgUpload.getDrawable()).getBitmap();
                        String imageData = imageToString(bitmap);
                        Log.d("nilai",imageData);
                        //Adding parameters to request
                        params.put("image", imageData);

                        //returning parameter
                        return params;
                    }
                };
                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(Uploadktp.this);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

       if(requestCode==CODE_GALLERY_REQUEST){
           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               Intent intent = new Intent(Intent.ACTION_PICK);
               intent.setType("image/*");
               startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"),CODE_GALLERY_REQUEST);
           }else{
               Toast.makeText(getApplicationContext(),"You don't have permission",Toast.LENGTH_SHORT).show();

           }
           return;
       }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
           Uri filePath = data.getData();
           try {
               InputStream inputStream = getContentResolver().openInputStream(filePath);
               Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
               btnUpload.setEnabled(true);
               imgUpload.setImageBitmap(bitmap);

           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }

       }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
}
