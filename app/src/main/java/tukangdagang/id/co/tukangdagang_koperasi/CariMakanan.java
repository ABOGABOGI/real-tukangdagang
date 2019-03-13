package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.carimakanan.ModelMakanan;
import tukangdagang.id.co.tukangdagang_koperasi.carimakanan.RvMakananAdapter;


public class CariMakanan extends AppCompatActivity {

    List<ModelMakanan> lstMakanan ;
    RvMakananAdapter myAdapter;
    RecyclerView myrv ;
    private String url_produk = Config.URL+Config.Fproduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_makanan);
        getSupportActionBar().setTitle("Cari Makanan");

        lstMakanan = new ArrayList<>();
//        lstMakanan.add(new ModelMakanan("Gulali Manis","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
//        lstMakanan.add(new ModelMakanan("Cilok Bapri","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));
//        lstMakanan.add(new ModelMakanan("Seblak Seuhah","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.koprasi));
//        lstMakanan.add(new ModelMakanan("Batagor Bandung","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.lovely_time));
//        lstMakanan.add(new ModelMakanan("Mie Ayam Abah","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
//        lstMakanan.add(new ModelMakanan("Mie Tek-tek","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));
//        lstMakanan.add(new ModelMakanan("Mie Bakso","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.namecard));
//        lstMakanan.add(new ModelMakanan("Martabak","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.barang));
//        lstMakanan.add(new ModelMakanan("Basreng","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.store));
//
//        myrv = (RecyclerView) findViewById(R.id.rvMakanan);
//        myAdapter = new RvMakananAdapter(this,lstMakanan);
//        myrv.setLayoutManager(new GridLayoutManager(this,2));
//        myrv.setAdapter(myAdapter);
        getdata();
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(15000.0000);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
//        detailHarga.setText();

        Log.d("rupi",formatRupiah.format((double)15000.0000));

    }

    private void getdata(){
        final ProgressDialog progressDialog = new ProgressDialog(CariMakanan.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_produk,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray makananArray = obj.getJSONArray("result");
                            progressDialog.dismiss();

                            for (int i = 0; i < makananArray.length(); i++) {

                                JSONObject makananobject = makananArray.getJSONObject(i);
                                String link = makananobject.getString("code") ;
                                Log.d("asd", link);
                                Log.d("nihil",response);



                                ModelMakanan modelmakanan = new ModelMakanan(makananobject.getString("name"),
                                        makananobject.getString("category_id"),
                                        makananobject.getDouble("price"),
                                        makananobject.getString("city"),
                                        makananobject.getString("type"),
                                        makananobject.getString("service"),
                                        makananobject.getString("image")
                                );

                                lstMakanan.add(modelmakanan);
                            }
                            myrv = (RecyclerView) findViewById(R.id.rvMakanan);
//                            myAdapter = new RvMakananAdapter(CariMakanan.this, lstMakanan);
                            myAdapter = new RvMakananAdapter(CariMakanan.this,lstMakanan);


                            myrv.setLayoutManager(new GridLayoutManager(CariMakanan.this,2));
                            myrv.setAdapter(myAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
}
