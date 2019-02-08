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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan.AdapterDaftarsimpanan;
import tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan.ModelDaftarsimpanan;

import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.path;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.pathKoperasi;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pinjaman extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ImageView imLoading, logoKoperasi;
    TextView namaKoperasi,totalPinjaman,totalBayar,tenor,jatuhTempo,tagihan,sisaBayar;
    Button btnDaftarPinjaman,btnCariPinjaman;
    ScrollView halamanPinjaman;
    RelativeLayout halamankosong;
    Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private ImageView toolbarTitle;


    public Pinjaman() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pinjaman, container, false);
        //bind view
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_main);
        toolbarTitle = (ImageView) rootView.findViewById(R.id.toolbar_title);
        //set toolbar
//        getActivity().setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //menghilangkan titlebar bawaan
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        // Inflate the layout for this fragment
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        imLoading = rootView.findViewById(R.id.loadingView);
        logoKoperasi = rootView.findViewById(R.id.logo_koperasi);
        namaKoperasi = rootView.findViewById(R.id.nama_koperasi);
        totalPinjaman = rootView.findViewById(R.id.total_pinjaman);
        totalBayar = rootView.findViewById(R.id.total_bayar);
        tenor = rootView.findViewById(R.id.tenor);
        jatuhTempo = rootView.findViewById(R.id.jatuh_tempo);
        tagihan = rootView.findViewById(R.id.tagihan);
        sisaBayar = rootView.findViewById(R.id.sisa_bayar);
        btnDaftarPinjaman = rootView.findViewById(R.id.btn_daftar_pinjaman);
        halamanPinjaman = rootView.findViewById(R.id.halaman_pinjaman);
        halamankosong = rootView.findViewById(R.id.datakosong);
        btnCariPinjaman = rootView.findViewById(R.id.btn_cari_pinjaman);
        cariPinjaman();
        daftarPinjaman();
        getdata();
        return rootView;
    }

    private void daftarPinjaman() {
        btnDaftarPinjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Daftarpinjaman.class);
                startActivity(intent);
            }
        });
    }

    private void cariPinjaman() {
        btnCariPinjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CariPinjaman.class);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_DAFTAR_PINJAMAN,
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
//                            arrayList.clear();
//                            for (int i = 0; i < pinjamanArray.length(); i++) {
                                Log.d("nilaip", String.valueOf(pinjamanArray.length()));
                                String dataPinjaman = String.valueOf(pinjamanArray.length());
                                if(dataPinjaman.equals("0")){
                                    halamanPinjaman.setVisibility(View.GONE);
                                    halamankosong.setVisibility(VISIBLE);
                                }
                                JSONObject pinjamanobject = pinjamanArray.getJSONObject(0);
                                Log.d("asd", response);
                               namaKoperasi.setText(pinjamanobject.getString("nama_koperasi"));
                               Glide.with(getActivity())
                                    .load(path+pinjamanobject.getString("logo_koperasi"))
                                    .into(logoKoperasi);
                                totalPinjaman.setText(formatRupiah.format((double) Double.valueOf(pinjamanobject.getString("jumlah_pinjaman"))));
                                totalBayar.setText(formatRupiah.format((double) Double.valueOf(pinjamanobject.getString("jumlah_harus_bayar"))));
                                tenor.setText(pinjamanobject.getString("tenor") +" Bulan");
                                tagihan.setText(formatRupiah.format((double) Double.valueOf(pinjamanobject.getString("jumlah_bayar_bulanan"))));
                                jatuhTempo.setText(pinjamanobject.getString("jatuh_tempo") +" Hari lagi");
                                sisaBayar.setText(formatRupiah.format((double) Double.valueOf(pinjamanobject.getString("jumlah_sisa"))));


//                                ModelDaftarpinjaman model = new ModelDaftarpinjaman(pinjamanobject.getString("nama_koperasi"),
//                                        pinjamanobject.getString("jumlah"),
//                                        pinjamanobject.getString("logo_koperasi"),
//                                        pinjamanobject.getString("no_anggota"),
//                                        pinjamanobject.getString("hari")
//                                );

//                                arrayList.add(model);
//                            }
//                            listView = getActivity().findViewById(R.id.listDaftarsimpanan);
//                            adapter = new AdapterDaftarpinjaman(getContext(),arrayList);
//
//
//                            listView.setAdapter(adapter);

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
//        ((MainActivity2) getActivity()).setActionBarTitle("Pinjaman Aktif");
//    }

}
