package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.daftarpinjaman.AdapterDaftarpinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.daftarpinjaman.ModelDaftarpinjaman;

import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;


public class Daftarpinjaman extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ListView listView;
    AdapterDaftarpinjaman adapter;
    String[] title;
    String[] description;
    int[] icon;
    ArrayList<ModelDaftarpinjaman> arrayList = new ArrayList<ModelDaftarpinjaman>();
    ImageView imLoading;
    Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout halamanPinjaman,halamanKosong;
    private String url_pinjaman = Config.URL+Config.Fdaftarpinjaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarpinjaman);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Daftar Pinjaman");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        imLoading = findViewById(R.id.loadingView);

        getdata();
    }

    private void getdata() {
        imLoading.setBackgroundResource(R.drawable.animasi_loading);
        AnimationDrawable frameAnimation = (AnimationDrawable) imLoading
                .getBackground();
        //Menjalankan File Animasi
        frameAnimation.start();
        imLoading.setVisibility(VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_pinjaman,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray pinjamanArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                            arrayList.clear();
                            for (int i = 0; i < pinjamanArray.length(); i++) {
                            JSONObject pinjamanobject = pinjamanArray.getJSONObject(i);
                            Log.d("asd", response);

                                ModelDaftarpinjaman model = new ModelDaftarpinjaman(pinjamanobject.getString("nama_koperasi"),
                                        pinjamanobject.getString("logo_koperasi"),
                                        pinjamanobject.getString("jumlah_pinjaman"),
                                        pinjamanobject.getString("jumlah_harus_bayar"),
                                        pinjamanobject.getString("tenor"),
                                        pinjamanobject.getString("jumlah_bayar_bulanan"),
                                        pinjamanobject.getString("jatuh_tempo"),
                                        pinjamanobject.getString("jumlah_sisa")
                                );

                                arrayList.add(model);
                            }
                            listView = findViewById(R.id.listDaftarpinjaman);
                            adapter = new AdapterDaftarpinjaman(Daftarpinjaman.this,arrayList);


                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }){
            @Override
            protected Map< String, String > getParams() throws AuthFailureError {
                Map < String, String > params = new HashMap< >();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String idprofile = sharedPreferences.getString(PROFILE_ID, "");
                params.put("idprofile", idprofile);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRefresh() {
    getdata();
    }
}
