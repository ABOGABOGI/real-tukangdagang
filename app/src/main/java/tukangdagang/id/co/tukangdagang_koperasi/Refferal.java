package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_refferal;

public class Refferal extends AppCompatActivity {
    EditText etrefferal;
    Button btn_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refferal);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Input Kode Refferal");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        etrefferal=findViewById(R.id.etrefferal);
        btn_ref=findViewById(R.id.btn_ref);
        simpan();
        setdata();
    }

    private void simpan() {
        btn_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String n_refferal = etrefferal.getText().toString();
                editor.putString(n_info_refferal, n_refferal);
                editor.commit();
                onSupportNavigateUp();
            }
        });
    }

    private void setdata() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String info_refferal = sharedPreferences.getString(n_info_refferal, "");
            if (!info_refferal.equals("")) {
                etrefferal.setText(info_refferal);
            }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
