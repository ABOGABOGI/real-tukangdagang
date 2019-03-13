package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;


public class RincianBarang extends AppCompatActivity {
    private TextView tampilHarga,tampilAlamat,tampilKategori,tampilBerat,tampilDeskripsi;
    private ImageView img;
    private String path_gambar = Config.path+Config.logokoperasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_barang);

        tampilHarga = (TextView) findViewById(R.id.tampil_harga_barang);
        tampilAlamat = (TextView) findViewById(R.id.tampil_alamat_barang);
        tampilKategori = (TextView) findViewById(R.id.tampil_kategori_barang);
        tampilBerat = (TextView) findViewById(R.id.tampil_berat_barang);
        tampilDeskripsi = (TextView) findViewById(R.id.tampil_deskripsi_barang);
        img = (ImageView) findViewById(R.id.gambar_barang);

        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Harga = intent.getExtras().getString("Harga");
        String Alamat = intent.getExtras().getString("Alamat");
        String Kategori = intent.getExtras().getString("Kategori");
        String Berat = intent.getExtras().getString("Berat");
        String Deskripsi = intent.getExtras().getString("Deskripsi");
        String image = intent.getExtras().getString("Thumbnail") ;

        // Setting values

        tampilHarga.setText(Harga);
        tampilAlamat.setText(Alamat);
        tampilKategori.setText(Kategori);
        tampilBerat.setText(Berat);
        tampilDeskripsi.setText(Deskripsi);
//        img.setImageResource(image);
        Glide.with(this)
                .load(path_gambar + image)
//                .crossFade()
//                .placeholder(R.mipmap.ic_launcher)
                .into(img);
        getSupportActionBar().setTitle(Title);


    }
}
