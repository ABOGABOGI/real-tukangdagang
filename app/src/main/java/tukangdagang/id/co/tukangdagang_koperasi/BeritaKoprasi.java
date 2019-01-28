package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import tukangdagang.id.co.tukangdagang_koperasi.carimodal.ListViewAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.carimodal.Model;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardFragmentPagerAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardItem;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardPagerAdapter2;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.ShadowTransformer;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_ID_KOPERASI;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_IMG_KOPERASI;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_KOPERASI;

public class BeritaKoprasi extends AppCompatActivity {
    private ViewPager mViewPager;
    private CardPagerAdapter2 mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private TextView namakoperasi;

    private static final String TAG = "BeritaKoprasi";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_koprasi);
        namakoperasi = findViewById(R.id.namakoperasi);

        Intent intent = getIntent();
        String Nkoperasi = intent.getExtras().getString("namakoperasi");
        final String Idkoperasi = intent.getExtras().getString("idkoperasi");

        namakoperasi.setText(Nkoperasi);

        mViewPager = (ViewPager) findViewById(R.id.cardviewslider2);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_IMG_KOPERASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            mCardAdapter = new CardPagerAdapter2();
                            for (int i = 0; i < koperasiArray.length(); i++) {
                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
//                                Log.d("asdf", koperasiobject.getString("logo_koperasi"));
                                mCardAdapter.addCardItem(new CardItem(Config.pathKoperasi + koperasiobject.getString("id_koperasi")+"/"+koperasiobject.getString("gambar_koperasi"),koperasiobject.getString("id")));
                            }
                            mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                                    dpToPixels(2, BeritaKoprasi.this));

                            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
                            mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

                            mViewPager.setAdapter(mCardAdapter);
                            mViewPager.setPageTransformer(false, mCardShadowTransformer);
                            mViewPager.setOffscreenPageLimit(3);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Tidak ada Koneksi", Toast.LENGTH_SHORT).show();
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


        getImages();
    }
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    //coding recycler Angota
    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
}
