package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.home2.HomeAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.slider.CustomSliderView;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardFragmentPagerAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardPagerAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.ShadowTransformer;

import static android.view.View.VISIBLE;

public class Home2 extends Fragment implements  BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,SwipeRefreshLayout.OnRefreshListener {
    private ShimmerFrameLayout mShimmerViewContainer;
    private SliderLayout mDemoSlider;
    GridLayout mainGrid;
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    ImageView imLoading;
    Context mContext;
    String sukses ="0";
    TextView smsCountTxt;
    int pendingSMSCount = 10;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
//    private ImageView toolbarTitle;
    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mIdkoperasi = new ArrayList<>();
    private static final String TAG = "Home";
    boolean loggedIn = false;
    private String url_koperasi = Config.URL+Config.Fkoperasi;
    private String url_slider = Config.URL+Config.Fslide;
    private String path_gambar = Config.path+Config.Slide;

    public Home2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home2, container, false);
        //bind view
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_main);
//        toolbarTitle = (ImageView) rootView.findViewById(R.id.toolbar_title);
        //set toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //menghilangkan titlebar bawaan
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Inflate the layout for this fragment
        mDemoSlider = (SliderLayout)rootView.findViewById(R.id.slider);
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        mainGrid = (GridLayout) rootView.findViewById(R.id.mainGrid);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        imLoading = rootView.findViewById(R.id.loadingView);
        checkNetworkConnectionStatus();
        //Set Event
        setSingleEvent(mainGrid);

        mViewPager = (ViewPager) rootView.findViewById(R.id.cardviewslider2);
        getSlider();
//        tampilCari();
        getCardSlider();
        return rootView;
    }


    private void getSlider() {
        imLoading.setBackgroundResource(R.drawable.animasi_loading);
        AnimationDrawable frameAnimation = (AnimationDrawable) imLoading
                .getBackground();
        //Menjalankan File Animasi
        frameAnimation.start();
        imLoading.setVisibility(VISIBLE);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_slider,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("deno",response);
                            HashMap<String,String> url_maps = new HashMap<String, String>();
                            for (int i = 0; i < koperasiArray.length(); i++) {
                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                url_maps.put(koperasiobject.getString("id"), path_gambar+koperasiobject.getString("gambar_utama"));
                            }
                                if(sukses.equals("0")) {
                                    for (String name : url_maps.keySet()) {
                                        CustomSliderView textSliderView = new CustomSliderView(getContext());
                                        // initialize a SliderLayout
                                        textSliderView
//                    .description(name)
                                                .image(url_maps.get(name))
                                                .setScaleType(BaseSliderView.ScaleType.Fit);
//                                            .setOnSliderClickListener(this);

                                        //add your extra information
                                        textSliderView.bundle(new Bundle());
                                        textSliderView.getBundle()
                                                .putString("extra", name);

                                        mDemoSlider.addSlider(textSliderView);
                                        sukses="1";
                                    }

                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_SHORT).show();
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }) {
        };
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    //coding recycler Koperasi
    private void getCardSlider(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_koperasi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
                            JSONObject obj = new JSONObject(response);
                            JSONArray anggotaArray = obj.getJSONArray("result");
                            Log.d("resultanggota",response);
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            mImageUrls.clear();
                            mNames.clear();
                            for (int i = 0; i < anggotaArray.length(); i++) {
                                JSONObject anggotaobject = anggotaArray.getJSONObject(i);
                                mImageUrls.add(anggotaobject.getString("logo_koperasi"));
                                mNames.add(anggotaobject.getString("nama_koperasi"));
                                mIdkoperasi.add(anggotaobject.getString("id"));
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
                        Toast.makeText(getActivity().getApplicationContext(), "Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_SHORT).show();

                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void initRecyclerView(){
        try {
            Log.d(TAG, "initRecyclerView: init recyclerview");

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(layoutManager);
            HomeAdapter adapter = new HomeAdapter(getContext(), mNames, mImageUrls, mIdkoperasi);
            recyclerView.setAdapter(adapter);
        }catch (Exception err){
            Toast.makeText(getActivity(), err.toString(), Toast.LENGTH_SHORT).show();
        }
    }


//    public static float dpToPixels(int dp, Context context) {
//        return dp * (context.getResources().getDisplayMetrics().density);
//    }



    private void checkNetworkConnectionStatus() {
        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()){ //connected with either mobile or wifi
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected){ //wifi connected
                Log.d("koneksi","konek dengan wifi");
            }
            else if (mobileConnected){ //mobile data connected
                Log.d("koneksi","konek dengan mobile data");
            }
        }
        else { //no internet connection
            Toast.makeText(getActivity(),"Tidak Ada koneksi internet",Toast.LENGTH_LONG).show();
            imLoading.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final int finalI = i;
            final CardView cardView0 = (CardView) mainGrid.getChildAt(0);
            cardView0.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(),MapsActivity.class);
                    startActivity(intent);

                }
            });

            final CardView cardView1 = (CardView) mainGrid.getChildAt(1);
            cardView1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), CariKoperasi.class);
                    startActivity(intent);

                }
            });

            final CardView cardView2 = (CardView) mainGrid.getChildAt(2);
            cardView2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                    //Fetching the boolean value form sharedpreferences
                    loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                    if(!loggedIn){
                        //We will start the SimpananF Activity
                        Intent intent = new Intent(getActivity(), Login.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getActivity(), Daganganku.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRefresh() {
        getSlider();
        getCardSlider();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
//    @Override
//    public void onResume(){
//        super.onResume();
//        ((MainActivity2) getActivity()).setActionBarTitle("Koperatif");
//    }
@Override
public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Confirm this fragment has menu items.
    setHasOptionsMenu(true);
}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            // TODO Add your menu entries here
            inflater.inflate(R.menu.main_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
            final MenuItem menuItem = menu.findItem(R.id.action_notifications);

            View actionView = MenuItemCompat.getActionView(menuItem);
            smsCountTxt = (TextView) actionView.findViewById(R.id.notification_badge);

            setupBadge();

            actionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOptionsItemSelected(menuItem);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_notifications: {
//                Toast.makeText(this,"ini notifikasi",Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(getActivity(),Notifikasi.class);
                startActivity(inten);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (smsCountTxt != null) {
            if (pendingSMSCount == 0) {
                if (smsCountTxt.getVisibility() != View.GONE) {
                    smsCountTxt.setVisibility(View.GONE);
                }
            } else {
                smsCountTxt.setText(String.valueOf(Math.min(pendingSMSCount, 99)));
                if (smsCountTxt.getVisibility() != View.VISIBLE) {
                    smsCountTxt.setVisibility(View.VISIBLE);
                }
            }
        }
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
