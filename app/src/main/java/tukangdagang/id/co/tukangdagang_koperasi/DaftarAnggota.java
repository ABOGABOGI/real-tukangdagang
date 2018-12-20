package tukangdagang.id.co.tukangdagang_koperasi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import tukangdagang.id.co.tukangdagang_koperasi.daftaranggota.ListViewAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.daftaranggota.Model;

public class DaftarAnggota extends AppCompatActivity {
    ListView listView;
    ListViewAdapter adapter;
    String[] title,desc;
    int[] icon;
    ArrayList<Model> arrayList = new ArrayList<Model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_anggota);
        getSupportActionBar().setTitle("Daftar Anggota Koperasi");

        title = new String[]{"Informasi Umum", "Nomor", "Upload", "Kode Referal(Optional)"};
        desc = new String[]{"Silahkan isi nama lengkap,jenis kelamin dan alamat", "Silahkan isi No KTP,KK dan No Telp/HP", "Upload No KTP,KK yang telah di scan atau disimpan", "Silahkan isi Kode Referal Anda"};
        listView = findViewById(R.id.list_daftar_anggota);
        icon = new int[]{R.drawable.panah, R.drawable.panah, R.drawable.panah, R.drawable.panah};
        for (int i =0; i<title.length; i++){
            Model model = new Model(title[i], desc[i],icon[i] );
            //bind all strings in an array
            arrayList.add(model);
        }

        //pass results to listViewAdapter class
        adapter = new ListViewAdapter(this, arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);

    }




}
