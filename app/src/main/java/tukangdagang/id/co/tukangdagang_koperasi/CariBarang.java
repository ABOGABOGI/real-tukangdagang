package tukangdagang.id.co.tukangdagang_koperasi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.caribarang.ModelBarang;
import tukangdagang.id.co.tukangdagang_koperasi.caribarang.RvBarangAdapter;

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
        lstBarang.add(new ModelBarang("Batik Cap Jahe Export","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
        lstBarang.add(new ModelBarang("Sepatu Antik","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));
        lstBarang.add(new ModelBarang("Seragam SD","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.koprasi));
        lstBarang.add(new ModelBarang("Kaos Kaki Bau","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.lovely_time));
        lstBarang.add(new ModelBarang("Gantungan Kunci","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
        lstBarang.add(new ModelBarang("Tas Sekolah","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));
        lstBarang.add(new ModelBarang("Baju Baru","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.namecard));
        lstBarang.add(new ModelBarang("Baju Bekas","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.barang));
        lstBarang.add(new ModelBarang("Lemari Kaca","Barang","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.store));

        myrv = (RecyclerView) findViewById(R.id.rvBarang);
        myAdapter = new RvBarangAdapter(this,lstBarang);
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myAdapter);

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
