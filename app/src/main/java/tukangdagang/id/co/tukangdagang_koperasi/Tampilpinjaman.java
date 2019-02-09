package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.path;

public class Tampilpinjaman extends AppCompatActivity{
    ImageView imLoading, logoKoperasi;
    TextView namaKoperasi,totalPinjaman,totalBayar,tenor,jatuhTempo,tagihan,sisaBayar;
    Button btnDaftarPinjaman,btnCariPinjaman;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilpinjaman);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Detail Pinjaman Aktif");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        logoKoperasi = findViewById(R.id.logo_koperasi);
        namaKoperasi = findViewById(R.id.nama_koperasi);
        totalPinjaman =findViewById(R.id.total_pinjaman);
        totalBayar = findViewById(R.id.total_bayar);
        tenor = findViewById(R.id.tenor);
        jatuhTempo = findViewById(R.id.jatuh_tempo);
        tagihan = findViewById(R.id.tagihan);
        sisaBayar = findViewById(R.id.sisa_bayar);

        Intent intent = getIntent();
        String Nnamakoperasi = intent.getExtras().getString("namakoperasi");
        String Nlogokoperasi = intent.getExtras().getString("logokoperasi");
        String Ntotalpinjaman = intent.getExtras().getString("totalpinjaman");
        String Ntotalbayar = intent.getExtras().getString("totalbayar");
        String Ntenor = intent.getExtras().getString("tenor");
        String Njatuhtempo = intent.getExtras().getString("jatuhtempo");
        String Ntagihan = intent.getExtras().getString("tagihan");
        String Nsisabayar = intent.getExtras().getString("sisabayar");
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        namaKoperasi.setText(Nnamakoperasi);
        totalPinjaman.setText(formatRupiah.format((double) Double.valueOf(Ntotalpinjaman)));
        totalBayar.setText(formatRupiah.format((double) Double.valueOf(Ntotalbayar)));
        tenor.setText(Ntenor+" Bulan");
        jatuhTempo.setText(Njatuhtempo+ " Hari lagi");
        tagihan.setText(formatRupiah.format((double) Double.valueOf(Ntagihan)));
        sisaBayar.setText(formatRupiah.format((double) Double.valueOf(Nsisabayar)));
        Glide.with(this)
                .load(path+Nlogokoperasi)
                .into(logoKoperasi);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
