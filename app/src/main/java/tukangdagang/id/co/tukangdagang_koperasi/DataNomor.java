package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nohp;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nokk;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_noktp;

public class DataNomor extends AppCompatActivity {
Button btn_simpan_no;
EditText etnoKTP,etKK,etHP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_nomor);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Informasi Nomor");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        btn_simpan_no = findViewById(R.id.btn_simpan_no);
        etnoKTP = findViewById(R.id.etnoKTP);
        etKK = findViewById(R.id.etnoKK);
        etHP = findViewById(R.id.etnoHP);
        simpan();
        setdata();
    }

    private void setdata() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String status_nomor = sharedPreferences.getString(Config.n_status_nomor, "");
        if (status_nomor.equals("1")) {
            String info_noktp = sharedPreferences.getString(n_info_noktp, "");
            String info_nokk = sharedPreferences.getString(n_info_nokk, "");
            String info_nohp = sharedPreferences.getString(n_info_nohp, "");
            etnoKTP.setText(info_noktp);
            etKK.setText(info_nokk);
            etHP.setText(info_nohp);
        }
    }

    private void simpan() {
        btn_simpan_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etnoKTP.getText().toString().equals("") &&
                        !etKK.getText().toString().equals("") &&
                        !etHP.getText().toString().equals("")){
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String n_noktp = etnoKTP.getText().toString();
                    String n_nokk = etKK.getText().toString();
                    String n_nohp = etHP.getText().toString();
                    editor.putString("info_noktp", n_noktp);
                    editor.putString("info_nokk", n_nokk);
                    editor.putString("info_nohp", n_nohp);
                    editor.putString("status_nomor", "1");
                    editor.commit();
                    onSupportNavigateUp();
                }else{
                    Toast.makeText(getApplicationContext(),"Data belum lengkap,Pastikan data di isi semua",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
