package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.slider.ChildAnimationExample;
import tukangdagang.id.co.tukangdagang_koperasi.slider.CustomSliderView;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardFragmentPagerAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardItem;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.CardPagerAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.slidercardview.ShadowTransformer;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_IMG_KOPERASI;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_KOPERASI;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.path;

public class Home2 extends Fragment implements  BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;
    GridLayout mainGrid;
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;


    public Home2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home2, container, false);
        // Inflate the layout for this fragment
        mDemoSlider = (SliderLayout)rootView.findViewById(R.id.slider);
        mainGrid = (GridLayout) rootView.findViewById(R.id.mainGrid);
        slider();
        checkNetworkConnectionStatus();
        //Set Event
        setSingleEvent(mainGrid);

        mViewPager = (ViewPager) rootView.findViewById(R.id.cardviewslider2);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_KOPERASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            mCardAdapter = new CardPagerAdapter();
                            for (int i = 0; i < koperasiArray.length(); i++) {
                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
//                                Log.d("asdf", koperasiobject.getString("logo_koperasi"));
                                mCardAdapter.addCardItem(new CardItem(path + koperasiobject.getString("logo_koperasi"),koperasiobject.getString("nama_koperasi")));
                            }
                            mFragmentCardAdapter = new CardFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                                    dpToPixels(2, getActivity()));

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
                        Toast.makeText(getContext(), "Tidak ada Koneksi", Toast.LENGTH_SHORT).show();
                    }
                }) {
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        return rootView;
    }
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    private void slider() {
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("1", "https://static.vecteezy.com/system/resources/previews/000/103/286/non_2x/free-flat-design-vector-background.jpg");
        url_maps.put("2", "http://idseducation.com/wp-content/uploads/2018/09/thumbnail-5.jpg");
        url_maps.put("3", "https://think360studio.com/wp-content/uploads/2016/03/flat-design.jpg");
        url_maps.put("4", "https://www.musthafa.net/wp-content/uploads/2017/02/flatdesign.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1",R.drawable.sticker);
        file_maps.put("2",R.drawable.sticker);
        file_maps.put("3",R.drawable.sticker);
        file_maps.put("4",R.drawable.sticker);

        for(String name : url_maps.keySet()){
            CustomSliderView textSliderView = new CustomSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
//                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

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

                    Intent intent = new Intent(getActivity(),CariModal.class);
                    startActivity(intent);

                }
            });

            final CardView cardView2 = (CardView) mainGrid.getChildAt(2);
            cardView2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(),Daganganku.class);
                    startActivity(intent);

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity2) getActivity()).setActionBarTitle("Koperatif");
    }

}
