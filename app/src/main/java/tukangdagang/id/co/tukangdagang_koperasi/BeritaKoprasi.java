package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.Recycler.RecyclerViewAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardFragmentPagerAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardItem;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardPagerAdapter2;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.ShadowTransformer;

import static android.view.View.VISIBLE;

public class BeritaKoprasi extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ViewPager mViewPager;
    private CardPagerAdapter2 mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private TextView namakoperasi;
    String Idkoperasi;
    private String url_imgkoperasi = Config.URL+Config.FImgKoperasi;
    private String url_anggota = Config.URL+Config.Ftampilanggota;

    private static final String TAG = "BeritaKoprasi";
    ImageView imLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String path_gambar = Config.path+Config.Gambarkoperasi;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_koprasi);
        namakoperasi = findViewById(R.id.namakoperasi);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Detail Koperasi");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String Nkoperasi = intent.getExtras().getString("namakoperasi");
        Idkoperasi = intent.getExtras().getString("idkoperasi");

        namakoperasi.setText(Nkoperasi);

        mViewPager = (ViewPager) findViewById(R.id.cardviewslider2);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        imLoading = findViewById(R.id.loadingView);
        getdata();
        getAnggota();
    }

    private void getdata() {
        imLoading.setBackgroundResource(R.drawable.animasi_loading);
        AnimationDrawable frameAnimation = (AnimationDrawable) imLoading
                .getBackground();
        //Menjalankan File Animasi
        frameAnimation.start();
        imLoading.setVisibility(VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_imgkoperasi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);


                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            mCardAdapter = new CardPagerAdapter2();
                            for (int i = 0; i < koperasiArray.length(); i++) {
                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
//                                Log.d("asdf", koperasiobject.getString("logo_koperasi"));
                                mCardAdapter.addCardItem(new CardItem(path_gambar + koperasiobject.getString("id_koperasi")+"/"+koperasiobject.getString("gambar_koperasi"),koperasiobject.getString("id"),""));
                            }
                            mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                                    dpToPixels(2, BeritaKoprasi.this));

                            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
                            mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

                            mViewPager.setAdapter(mCardAdapter);
                            mViewPager.setPageTransformer(false, mCardShadowTransformer);
                            mViewPager.setOffscreenPageLimit(3);
                            mViewPager.setClipToPadding(false);
                            mViewPager.setPadding(0,0,0,0);
                            mViewPager.setPageMargin(5);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_SHORT).show();
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }) {
            @Override
            protected Map< String, String > getParams() throws AuthFailureError {
                Map < String, String > params = new HashMap< >();
                params.put("idkoperasi", Idkoperasi);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    //coding recycler Angota
    private void getAnggota(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_anggota,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
                            JSONObject obj = new JSONObject(response);
                            JSONArray anggotaArray = obj.getJSONArray("result");
                            Log.d("resultanggota",response);
                            mImageUrls.clear();
                            mNames.clear();
                            for (int i = 0; i < anggotaArray.length(); i++) {
                                JSONObject anggotaobject = anggotaArray.getJSONObject(i);
                                mImageUrls.add(anggotaobject.getString("avatar"));
                                mNames.add(anggotaobject.getString("nama"));
                            }

                            initRecyclerView();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Override
                        protected Map< String, String > getParams() throws AuthFailureError {
                            Map < String, String > params = new HashMap< >();
                            params.put("idkoperasi", Idkoperasi);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);

                }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRefresh() {
        getdata();
        getAnggota();
    }
}
