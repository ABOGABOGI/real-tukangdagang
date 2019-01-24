package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.daftaranggota.ListViewAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.daftaranggota.Model;

public class DaftarAnggota extends AppCompatActivity {
    ListView listView;
    ListViewAdapter adapter;
    Button daftar;
    String[] title,desc;
    int[] icon;
    ArrayList<Model> arrayList = new ArrayList<Model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_anggota);
        getSupportActionBar().setTitle("Daftar Anggota Koperasi");

        daftar = findViewById(R.id.daftar);

        title = new String[]{"Informasi Umum", "Nomor", "Upload", "Kode Referal(Optional)"};
        desc = new String[]{"Silahkan isi nama lengkap,jenis kelamin dan alamat", "Silahkan isi No KTP,KK dan No Telp/HP", "Upload No KTP,KK yang telah di scan atau disimpan", "Silahkan isi Kode Referal Anda"};
        listView = findViewById(R.id.list_daftar_anggota);
        icon = new int[]{R.drawable.panah, R.drawable.panah, R.drawable.panah, R.drawable.panah};
        for (int i =0; i<title.length; i++){
            Model model = new Model(title[i], desc[i],icon[i] );
            //bind all strings in an array
            arrayList.add(model);
        }

        //pass results to listViewAdapter class
        adapter = new ListViewAdapter(this, arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);

        kirim();

    }
    public void kirim(){
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(DaftarAnggota.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                //post image to server
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URLUpload,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                daftar.setEnabled(false);
                                progressDialog.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("imagePreferance", null);
                                editor.putString("imagePreferance2", null);
                                editor.putString("imagePreferance3", null);
                                editor.commit();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                                Toast.makeText(getApplicationContext(),"Error : "+error.toString(),Toast.LENGTH_SHORT).show();
                                Log.d("tee",error.toString());
                                progressDialog.dismiss();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();

                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        String photo = sharedPreferences.getString("imagePreferance", "photo");
                        String photo2 = sharedPreferences.getString("imagePreferance2", "photo");
                        String photo3 = sharedPreferences.getString("imagePreferance3", "photo");
                        //Adding parameters to request
                        params.put("image", photo);
                        params.put("image2", photo2);
                        params.put("image3", photo3);

                        //returning parameter
                        return params;
                    }
                };
                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(DaftarAnggota.this);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);

            }
        });
    }




}
