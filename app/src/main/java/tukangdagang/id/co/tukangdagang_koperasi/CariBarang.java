package tukangdagang.id.co.tukangdagang_koperasi;

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

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.caribarang.ModelBarang;
import tukangdagang.id.co.tukangdagang_koperasi.caribarang.RvBarangAdapter;

import static tukangdagang.id.co.tukangdagang_koperasi.app.params.JSON_URL;

public class CariBarang extends AppCompatActivity {


    List<ModelBarang> lstBarang ;
    RvBarangAdapter myAdapter;
    RecyclerView myrv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_barang);
        getSupportActionBar().setTitle("Cari Barang");
        lstBarang = new ArrayList<>();
        getdata();
        Log.d("hitungan","jao");

    }

    private void getdata(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray barangArray = obj.getJSONArray("result");

                            for (int i = 0; i < barangArray.length(); i++) {

                                JSONObject barangobject = barangArray.getJSONObject(i);
                                String link = barangobject.getString("code") ;
                                Log.d("asd", link);


                                ModelBarang modelbarang = new ModelBarang(barangobject.getString("name"),
                                        barangobject.getString("category_id"),
                                        barangobject.getDouble("price"),
                                        barangobject.getString("city"),
                                        barangobject.getString("type"),
                                        barangobject.getString("service"),
                                        barangobject.getString("image")
                                        );

                                lstBarang.add(modelbarang);
                            }
                            myrv = (RecyclerView) findViewById(R.id.rvBarang);
                            myAdapter = new RvBarangAdapter(CariBarang.this, lstBarang);


                            myrv.setLayoutManager(new GridLayoutManager(CariBarang.this,2));
                            myrv.setAdapter(myAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
