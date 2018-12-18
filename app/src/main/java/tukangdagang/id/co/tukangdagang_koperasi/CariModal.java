package tukangdagang.id.co.tukangdagang_koperasi;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

import tukangdagang.id.co.tukangdagang_koperasi.carimodal.ListViewAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.carimodal.Model;

public class CariModal extends AppCompatActivity {
    ListView listView;
    ListViewAdapter adapter;
    String[] title;
    String[] description;
    int[] icon;
    ArrayList<Model> arrayList = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_modal);

        ActionBar actionBar = getSupportActionBar();

        title = new String[]{"Koprasi Bangun Bersama", "Koprasi Tanpa Riba", "Koprasi Tabungan Bersama", "Koprasi Suka Makmur", "Koprasi Milik Kita"};
        description = new String[]{"Rp.1 Juta - Rp.200 Juta", "Rp.1 Juta - Rp.200 Juta", "Rp.1 Juta - Rp.200 Juta", "Rp.1 Juta - Rp.200 Juta", "Rp.1 Juta - Rp.200 Juta"};
        icon = new int[]{R.drawable.koprasi, R.drawable.koprasi, R.drawable.koprasi, R.drawable.koprasi, R.drawable.koprasi};

        listView = findViewById(R.id.listKoprasi);

        for (int i =0; i<title.length; i++){
            Model model = new Model(title[i], description[i], icon[i]);
            //bind all strings in an array
            arrayList.add(model);
        }

        //pass results to listViewAdapter class
        adapter = new ListViewAdapter(this, arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);

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
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(s);
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
}