package tukangdagang.id.co.tukangdagang_koperasi;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.carikoperasi.CaripinjamanAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.carikoperasi.Model;

import static android.view.View.VISIBLE;

public class CariKoperasi extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ShimmerFrameLayout mShimmerViewContainer;
    List<Model> lstCaripinjaman ;
    CaripinjamanAdapter myAdapter;
    RecyclerView myrv ;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager  manager;
    Boolean isScrolling = false;
    int currentItems,totalItems,scrollOutItems;
    ProgressBar progressBar;
    int ival = 0;
    int totaldata = 0;
    int limit =0;
    private String url = Config.URL+Config.Fkoperasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_koperasi);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Cari Pinjaman");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressBar = findViewById(R.id.progress);
        lstCaripinjaman = new ArrayList<>();
        manager = new LinearLayoutManager(this);

        getdata();

    }

    private void getdata(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            // stop animating Shimmer and hide the layout
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            lstCaripinjaman.clear();
                            totaldata = koperasiArray.length();
                            for (int i = ival; i <5; i++) {

                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                Log.d("asd", response);


                                Model model = new Model(koperasiobject.getString("nama_koperasi"),
                                        koperasiobject.getString("pinjaman_min_koperasi"),
                                        koperasiobject.getString("pinjaman_max_koperasi"),
                                        koperasiobject.getString("logo_koperasi"),
                                        koperasiobject.getString("id"),
                                        koperasiobject.getString("rating_koperasi"),
                                        koperasiobject.getString("alamat_koperasi")
                                );

                                lstCaripinjaman.add(model);
                                ival++;
                            }
                            myrv = findViewById(R.id.listKoprasi);
                            myAdapter = new CaripinjamanAdapter(CariKoperasi.this,lstCaripinjaman);

                            myrv.setLayoutManager(manager);
                            //add ItemDecoration
                            myrv.addItemDecoration(new DividerItemDecoration(CariKoperasi.this,1));
                            myrv.setAdapter(myAdapter);
                            myrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                                        isScrolling=true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    currentItems = manager.getChildCount();
                                    totalItems = manager.getItemCount();
                                    scrollOutItems = manager.findFirstVisibleItemPosition();
                                    if(isScrolling && (currentItems+scrollOutItems == totalItems)){
                                        isScrolling=false;
                                        Log.d("ival", String.valueOf(totaldata));
                                        if(ival< totaldata) {
                                            fetchdata();

                                        }
                                    }
                                }
                            });

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
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void fetchdata() {
if(ival<totaldata) {
    progressBar.setVisibility(VISIBLE);
}
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    progressBar.setVisibility(View.GONE);
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray koperasiArray = obj.getJSONArray("result");
                                    Log.d("resul",response);
                                    limit = ival +5;
                                    for (int i = ival; i < limit; i++) {
                                        JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                        Log.d("asd", response);


                                        Model model = new Model(koperasiobject.getString("nama_koperasi"),
                                                koperasiobject.getString("pinjaman_min_koperasi"),
                                                koperasiobject.getString("pinjaman_max_koperasi"),
                                                koperasiobject.getString("logo_koperasi"),
                                                koperasiobject.getString("id"),
                                                koperasiobject.getString("rating_koperasi"),
                                                koperasiobject.getString("alamat_koperasi")
                                        );

                                        lstCaripinjaman.add(model);
                                        ival++;
                                    }
                                    myrv = findViewById(R.id.listKoprasi);
                                    myAdapter = new CaripinjamanAdapter(CariKoperasi.this,lstCaripinjaman);

                                    myrv.setLayoutManager(manager);
                                    //add ItemDecoration
                                    myrv.addItemDecoration(new DividerItemDecoration(CariKoperasi.this,1));
                                    myrv.setAdapter(myAdapter);
                                    myrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                        @Override
                                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                            super.onScrollStateChanged(recyclerView, newState);
                                            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                                                isScrolling=true;
                                            }
                                        }

                                        @Override
                                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                            super.onScrolled(recyclerView, dx, dy);
                                            currentItems = manager.getChildCount();
                                            totalItems = manager.getItemCount();
                                            scrollOutItems = manager.findFirstVisibleItemPosition();
                                            if(isScrolling && (currentItems+scrollOutItems == totalItems)){
                                                isScrolling=false;
                                                fetchdata();
                                            }
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(CariKoperasi.this);
                requestQueue.add(stringRequest);
                myAdapter.notifyDataSetChanged();
            }
        },2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_koprasi, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search_koprasi);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
//        myActionMenuItem.expandActionView(); // Expand the search menu item in order to show by default the query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    myAdapter.filter("");
                }
                else {
                    myAdapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id==R.id.action_settings){
//            //do your functionality here
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRefresh() {
        ival=0;
        getdata();

    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

}