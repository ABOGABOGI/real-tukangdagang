package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan.AdapterDaftarsimpanan;
import tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan.ModelDaftarsimpanan;

import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class Daftarsimpanan extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    AdapterDaftarsimpanan adapter;
    String[] title;
    String[] description;
    int[] icon;
    ArrayList<ModelDaftarsimpanan> arrayList = new ArrayList<ModelDaftarsimpanan>();
    ImageView imLoading;
    Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout halamanSimpanan,halamanKosong;
    Button btn_cari_koperasi;
    private Toolbar toolbar;
    private ImageView toolbarTitle;

    private String url_simpanan = Config.URL+Config.Fdaftarsimpanan;

    public Daftarsimpanan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daftarsimpanan, container, false);
        // Inflate the layout for this fragment
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        imLoading = rootView.findViewById(R.id.loadingView);
        halamanSimpanan = rootView.findViewById(R.id.halaman_simpanan);
        halamanKosong = rootView.findViewById(R.id.datakosong);
        btn_cari_koperasi = rootView.findViewById(R.id.btn_cari_koperasi);
        //bind view
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_main);
//        toolbarTitle = (ImageView) rootView.findViewById(R.id.toolbar_title);
        //set toolbar
//        getActivity().setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //menghilangkan titlebar bawaan
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        Carikoperasi();

        getdata();
        
        return rootView;
    }

    private void Carikoperasi() {
        btn_cari_koperasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CariKoperasi.class);
                startActivity(intent);
            }
        });
    }

    private void getdata() {
        imLoading.setBackgroundResource(R.drawable.animasi_loading);
        AnimationDrawable frameAnimation = (AnimationDrawable) imLoading
                .getBackground();
        //Menjalankan File Animasi
        frameAnimation.start();
        imLoading.setVisibility(VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_simpanan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            arrayList.clear();
                            String dataSimpanan = String.valueOf(koperasiArray.length());
                            if(dataSimpanan.equals("0")){
                                halamanSimpanan.setVisibility(View.GONE);
                                halamanKosong.setVisibility(VISIBLE);
                            }
                            for (int i = 0; i < koperasiArray.length(); i++) {

                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                Log.d("asd", response);


                                ModelDaftarsimpanan model = new ModelDaftarsimpanan(koperasiobject.getString("nama_koperasi"),
                                        koperasiobject.getString("jumlah"),
                                        koperasiobject.getString("logo_koperasi"),
                                        koperasiobject.getString("no_anggota"),
                                        koperasiobject.getString("hari"),
                                        koperasiobject.getString("nama_pendaftar"),
                                        koperasiobject.getString("avatar"),
                                        koperasiobject.getString("simpanan_pokok"),
                                        koperasiobject.getString("simpanan_wajib"),
                                        koperasiobject.getString("simpanan_sukarela")
                                );

                                arrayList.add(model);
                            }
                            listView = getActivity().findViewById(R.id.listDaftarsimpanan);
                            adapter = new AdapterDaftarsimpanan(getContext(),arrayList);


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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        getdata();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
//    @Override
//    public void onResume(){
//        super.onResume();
//        ((MainActivity2) getActivity()).setActionBarTitle("Daftar Simpanan");
//    }

}
