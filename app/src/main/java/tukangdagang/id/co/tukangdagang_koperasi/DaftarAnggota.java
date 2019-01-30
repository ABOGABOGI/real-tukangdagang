package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.carimakanan.ModelMakanan;
import tukangdagang.id.co.tukangdagang_koperasi.carimakanan.RvMakananAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.daftaranggota.ListViewAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.daftaranggota.Model;

import static android.view.View.VISIBLE;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.EMAIL_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.JSON_URL;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URLDaftar;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_ID_KOPERASI;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance2;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance3;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alamat;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_jk;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_kecamatan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_kodepos;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_kota;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nama_belakang;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nama_depan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nohp;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nokk;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_noktp;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_provinsi;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_rtrw;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_status;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_status_nomor;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_status_upload;

public class DaftarAnggota extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ListView listView;
    ListViewAdapter adapter;
    Button daftar,daftarnanti;
    String[] title, desc;
    int[] icon;
    String Idkoperasi;
    ArrayList < Model > arrayList = new ArrayList < Model > ();
    TextView spokok, swajib;
    ImageView imLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_anggota);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Daftar Anggota Koperasi");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Idkoperasi = intent.getExtras().getString("idkoperasi");
        daftar = findViewById(R.id.daftar);
        daftarnanti = findViewById(R.id.daftarnanti);
        spokok = findViewById(R.id.spokok);
        swajib = findViewById(R.id.swajib);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        imLoading = findViewById(R.id.loadingView);
        getdata();
        daftar();
        daftarnanti();

        title = new String[] {
                "Informasi Umum",
                "Nomor",
                "Upload",
                "Kode Referal(Optional)"
        };
        desc = new String[] {
                "Silahkan isi nama lengkap,jenis kelamin dan alamat",
                "Silahkan isi No KTP,KK dan No Telp/HP",
                "Upload No KTP,KK yang telah di scan atau disimpan",
                "Silahkan isi Kode Referal Anda"
        };
        listView = findViewById(R.id.list_daftar_anggota);
        icon = new int[] {
                R.drawable.panah, R.drawable.panah, R.drawable.panah, R.drawable.panah
        };
        for (int i = 0; i < title.length; i++) {
            Model model = new Model(title[i], desc[i], icon[i]);
            //bind all strings in an array
            arrayList.add(model);
        }

        //pass results to listViewAdapter class
        adapter = new ListViewAdapter(this, arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void daftar() {
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String info_status = sharedPreferences.getString(n_info_status, null);
                String status_nomor = sharedPreferences.getString(n_status_nomor, null);
                String status_upload = sharedPreferences.getString(n_status_upload, null);
                if (info_status.equals("1") && status_nomor.equals("1")&& status_upload.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DaftarAnggota.this);
                    builder.setMessage("Anda yakin ingin mendaftar ?")
                            .setCancelable(false)
                            .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                    final ProgressDialog progressDialog = new ProgressDialog(DaftarAnggota.this);
                                    progressDialog.setMessage("Loading...");
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();

                                    //post image to server
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URLUpload,
                                            new Response.Listener < String > () {
                                                @Override
                                                public void onResponse(String response) {
                                                    //If we are getting success from server
                                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString(n_imagePreferance, null);
                                                    editor.putString(n_imagePreferance2, null);
                                                    editor.putString(n_imagePreferance3, null);
                                                    editor.putString(n_info_status, "0");
                                                    editor.putString(n_status_nomor, "0");
                                                    editor.commit();

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    //You can handle error here if you want
                                                    Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
                                                    Log.d("tee", error.toString());
                                                    progressDialog.dismiss();
                                                }
                                            }) {
                                        @Override
                                        protected Map < String, String > getParams() throws AuthFailureError {
                                            Map < String, String > params = new HashMap < > ();

                                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                            String info_nama_depan = sharedPreferences.getString(n_info_nama_depan, "");
                                            String info_nama_belakang = sharedPreferences.getString(n_info_nama_belakang, "");
                                            String info_jk = sharedPreferences.getString(n_info_jk, "");
                                            String info_alamat = sharedPreferences.getString(n_info_alamat, "");
                                            String info_rtrw = sharedPreferences.getString(n_info_rtrw, "");
                                            String info_kodepos = sharedPreferences.getString(n_info_kodepos, "");
                                            String info_provinsi = sharedPreferences.getString(n_info_provinsi, "");
                                            String info_kota = sharedPreferences.getString(n_info_kota, "");
                                            String info_kecamatan = sharedPreferences.getString(n_info_kecamatan, "");
                                            String info_noktp = sharedPreferences.getString(n_info_noktp, "");
                                            String info_nokk = sharedPreferences.getString(n_info_nokk, "");
                                            String info_nohp = sharedPreferences.getString(n_info_nohp, "");
                                            String photo = sharedPreferences.getString(n_imagePreferance, "photo");
                                            String photo2 = sharedPreferences.getString(n_imagePreferance2, "photo");
                                            String photo3 = sharedPreferences.getString(n_imagePreferance3, "photo");
                                            String email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");
                                            //Adding parameters to request
                                            params.put("nama_depan", info_nama_depan);
                                            params.put("nama_belakang", info_nama_belakang);
                                            params.put("jk", info_jk);
                                            params.put("alamat", info_alamat);
                                            params.put("rtrw", info_rtrw);
                                            params.put("kodepos", info_kodepos);
                                            params.put("provinsi", info_provinsi);
                                            params.put("kota", info_kota);
                                            params.put("kecamatan", info_kecamatan);
                                            params.put("noktp", info_noktp);
                                            params.put("nokk", info_nokk);
                                            params.put("nohp", info_nohp);
                                            params.put("image", photo);
                                            params.put("image2", photo2);
                                            params.put("image3", photo3);
                                            params.put("email", email);
                                            params.put("idkoperasi", Idkoperasi);
                                            params.put("isdraft", "1");

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
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "data belum lengkap mohon periksa kembali", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    public void daftarnanti() {
        daftarnanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String info_status = sharedPreferences.getString(n_info_status, null);
                String status_nomor = sharedPreferences.getString(n_status_nomor, null);
                String status_upload = sharedPreferences.getString(n_status_upload, null);
                if (info_status.equals("1") && status_nomor.equals("1")&& status_upload.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DaftarAnggota.this);


                                    final ProgressDialog progressDialog = new ProgressDialog(DaftarAnggota.this);
                                    progressDialog.setMessage("Loading...");
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();

                                    //post image to server
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URLUpload,
                                            new Response.Listener < String > () {
                                                @Override
                                                public void onResponse(String response) {
                                                    //If we are getting success from server
                                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString(n_imagePreferance, null);
                                                    editor.putString(n_imagePreferance2, null);
                                                    editor.putString(n_imagePreferance3, null);
                                                    editor.putString(n_info_status, "0");
                                                    editor.putString(n_status_nomor, "0");
                                                    editor.commit();

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    //You can handle error here if you want
                                                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_SHORT).show();
                                                    Log.d("tee", error.toString());
                                                    progressDialog.dismiss();
                                                }
                                            }) {
                                        @Override
                                        protected Map < String, String > getParams() throws AuthFailureError {
                                            Map < String, String > params = new HashMap < > ();

                                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                            String info_nama_depan = sharedPreferences.getString(n_info_nama_depan, "");
                                            String info_nama_belakang = sharedPreferences.getString(n_info_nama_belakang, "");
                                            String info_jk = sharedPreferences.getString(n_info_jk, "");
                                            String info_alamat = sharedPreferences.getString(n_info_alamat, "");
                                            String info_rtrw = sharedPreferences.getString(n_info_rtrw, "");
                                            String info_kodepos = sharedPreferences.getString(n_info_kodepos, "");
                                            String info_provinsi = sharedPreferences.getString(n_info_provinsi, "");
                                            String info_kota = sharedPreferences.getString(n_info_kota, "");
                                            String info_kecamatan = sharedPreferences.getString(n_info_kecamatan, "");
                                            String info_noktp = sharedPreferences.getString(n_info_noktp, "");
                                            String info_nokk = sharedPreferences.getString(n_info_nokk, "");
                                            String info_nohp = sharedPreferences.getString(n_info_nohp, "");
                                            String photo = sharedPreferences.getString(n_imagePreferance, "photo");
                                            String photo2 = sharedPreferences.getString(n_imagePreferance2, "photo");
                                            String photo3 = sharedPreferences.getString(n_imagePreferance3, "photo");
                                            String email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");
                                            //Adding parameters to request
                                            params.put("nama_depan", info_nama_depan);
                                            params.put("nama_belakang", info_nama_belakang);
                                            params.put("jk", info_jk);
                                            params.put("alamat", info_alamat);
                                            params.put("rtrw", info_rtrw);
                                            params.put("kodepos", info_kodepos);
                                            params.put("provinsi", info_provinsi);
                                            params.put("kota", info_kota);
                                            params.put("kecamatan", info_kecamatan);
                                            params.put("noktp", info_noktp);
                                            params.put("nokk", info_nokk);
                                            params.put("nohp", info_nohp);
                                            params.put("image", photo);
                                            params.put("image2", photo2);
                                            params.put("image3", photo3);
                                            params.put("email", email);
                                            params.put("idkoperasi", Idkoperasi);
                                            params.put("isdraft", "0");

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

                } else {
                    Toast.makeText(getApplicationContext(), "data belum lengkap mohon periksa kembali", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    public void getdata() {
        imLoading.setBackgroundResource(R.drawable.animasi_loading);
        AnimationDrawable frameAnimation = (AnimationDrawable) imLoading
                .getBackground();
        //Menjalankan File Animasi
        frameAnimation.start();
        imLoading.setVisibility(VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ID_KOPERASI,
                new Response.Listener < String > () {
                    @Override
                    public void onResponse(String response) {
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray makananArray = obj.getJSONArray("result");

                            JSONObject makananobject = makananArray.getJSONObject(0);
                            String pokok = makananobject.getString("simpanan_pokok");
                            String wajib = makananobject.getString("simpanan_wajib");
                            Log.d("asd", pokok);
                            Log.d("nihil", response);
                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                            spokok.setText(formatRupiah.format((double) Double.valueOf(pokok)));
                            swajib.setText(formatRupiah.format((double) Double.valueOf(wajib)));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Tidak ada Koneksi", Toast.LENGTH_SHORT).show();
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }) {
            @Override
            protected Map < String, String > getParams() throws AuthFailureError {
                Map < String, String > params = new HashMap < > ();
                params.put("idkoperasi", Idkoperasi);
                Log.d("ini", Idkoperasi);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DaftarAnggota.this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onRefresh() {
        getdata();
    }
}