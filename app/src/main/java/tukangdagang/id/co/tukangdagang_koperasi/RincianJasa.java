package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RincianJasa extends AppCompatActivity {
    private TextView tampilHarga,tampilAlamat,tampilKategori,tampilBerat,tampilDeskripsi;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_jasa);
        tampilHarga = (TextView) findViewById(R.id.tampil_harga_jasa);
        tampilAlamat = (TextView) findViewById(R.id.tampil_alamat_jasa);
        tampilKategori = (TextView) findViewById(R.id.tampil_kategori_jasa);
        tampilBerat = (TextView) findViewById(R.id.tampil_berat_jasa);
        tampilDeskripsi = (TextView) findViewById(R.id.tampil_deskripsi_jasa);
        img = (ImageView) findViewById(R.id.gambar_jasa);

        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Harga = intent.getExtras().getString("Harga");
        String Alamat = intent.getExtras().getString("Alamat");
        String Kategori = intent.getExtras().getString("Kategori");
        String Berat = intent.getExtras().getString("Berat");
        String Deskripsi = intent.getExtras().getString("Deskripsi");
        int image = intent.getExtras().getInt("Thumbnail") ;

        // Setting values

        tampilHarga.setText(Harga);
        tampilAlamat.setText(Alamat);
        tampilKategori.setText(Kategori);
        tampilBerat.setText(Berat);
        tampilDeskripsi.setText(Deskripsi);
        img.setImageResource(image);
        getSupportActionBar().setTitle(Title);


    }
}
