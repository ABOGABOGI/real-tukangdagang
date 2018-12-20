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

import tukangdagang.id.co.tukangdagang_koperasi.carijasa.ModelJasa;
import tukangdagang.id.co.tukangdagang_koperasi.carijasa.RvJasaAdapter;

public class CariJasa extends AppCompatActivity {

    List<ModelJasa> lstJasa ;
    RvJasaAdapter myAdapter;
    RecyclerView myrv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_jasa);
        getSupportActionBar().setTitle("Cari Jasa");

        lstJasa = new ArrayList<>();
        lstJasa.add(new ModelJasa("Cukur Rambut","Jasa","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
        lstJasa.add(new ModelJasa("Install Ulang","Jasa","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));
        lstJasa.add(new ModelJasa("Service HP","Jasa","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.koprasi));
        lstJasa.add(new ModelJasa("Sapu Jalanan","Jasa","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.lovely_time));
        lstJasa.add(new ModelJasa("Sewa Sepeda","Jasa","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
        lstJasa.add(new ModelJasa("Sewa Mobil","Jasa","RP. 200.000","Grand Riscon Rancaekek","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));

        myrv = (RecyclerView) findViewById(R.id.rvJasa);
        myAdapter = new RvJasaAdapter(this,lstJasa);
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
