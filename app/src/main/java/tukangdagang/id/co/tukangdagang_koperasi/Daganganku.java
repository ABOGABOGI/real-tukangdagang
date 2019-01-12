package tukangdagang.id.co.tukangdagang_koperasi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import tukangdagang.id.co.tukangdagang_koperasi.daganganku.ListViewAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.daganganku.Model;

public class Daganganku extends AppCompatActivity {
    ListView listView;
    ListViewAdapter adapter;
    String[] title;
    int[] icon;
    ArrayList<Model> arrayList = new ArrayList<Model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daganganku);

        title = new String[]{"Daftar Barang", "Daftar Kategori Barang", "Transaksi Penjualan", "Transaksi Penerimaan Barang", "Jam Oprasional"};
        listView = findViewById(R.id.listDaganganku);
        icon = new int[]{R.drawable.label, R.drawable.label, R.drawable.label, R.drawable.label, R.drawable.label};
        for (int i =0; i<title.length; i++){
            Model model = new Model(title[i], icon[i]);
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
