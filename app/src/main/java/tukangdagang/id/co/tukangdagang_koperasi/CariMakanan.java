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

import tukangdagang.id.co.tukangdagang_koperasi.carimakanan.ModelMakanan;
import tukangdagang.id.co.tukangdagang_koperasi.carimakanan.RvMakananAdapter;

public class CariMakanan extends AppCompatActivity {

    List<ModelMakanan> lstMakanan ;
    RvMakananAdapter myAdapter;
    RecyclerView myrv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_makanan);
        getSupportActionBar().setTitle("Cari Makanan");

        lstMakanan = new ArrayList<>();
        lstMakanan.add(new ModelMakanan("Gulali Manis","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
        lstMakanan.add(new ModelMakanan("Cilok Bapri","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));
        lstMakanan.add(new ModelMakanan("Seblak Seuhah","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.koprasi));
        lstMakanan.add(new ModelMakanan("Batagor Bandung","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.lovely_time));
        lstMakanan.add(new ModelMakanan("Mie Ayam Abah","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
        lstMakanan.add(new ModelMakanan("Mie Tek-tek","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));
        lstMakanan.add(new ModelMakanan("Mie Bakso","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.namecard));
        lstMakanan.add(new ModelMakanan("Martabak","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.barang));
        lstMakanan.add(new ModelMakanan("Basreng","makanan","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.store));

        myrv = (RecyclerView) findViewById(R.id.rvMakanan);
        myAdapter = new RvMakananAdapter(this,lstMakanan);
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
