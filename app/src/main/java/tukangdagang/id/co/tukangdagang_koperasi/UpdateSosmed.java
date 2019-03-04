package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URLDaftar;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URLDaftar_sosmed;

public class UpdateSosmed extends AppCompatActivity {
EditText email,nama_lengkap,noHp,pwd,ulang_pwd;
Button btnDaftar;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sosmed);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Daftar");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.email);
        nama_lengkap = findViewById(R.id.nama);
        noHp =findViewById(R.id.noHp);
        pwd =findViewById(R.id.pwd);
        ulang_pwd =findViewById(R.id.ulangpwd);
        btnDaftar =findViewById(R.id.btnDaftar);
        Intent intent = getIntent();
        String nilai_email = intent.getExtras().getString("email");
        String nilai_nama = intent.getExtras().getString("nama");

        email.setText(nilai_email);
        nama_lengkap.setText(nilai_nama);
        email.setEnabled(false);
        daftar();
    }

    private void daftar() {
    btnDaftar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validate()){
                final ProgressDialog progressDialog = new ProgressDialog(UpdateSosmed.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                RequestQueue queue = Volley.newRequestQueue(UpdateSosmed.this);
//                String URL = EndPoints.BASE_URL + "/call";
                StringRequest request = new StringRequest(Request.Method.POST, URLDaftar_sosmed,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.hide();

                                try {
                                    JSONObject jObj = new JSONObject(response);
                                    success = jObj.getInt(TAG_SUCCESS);

                                    // Check for error node in json
                                    if (success == 1) {
                                        //Creating a shared preference
                                        SharedPreferences sharedPreferences = UpdateSosmed.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        //Adding values to editor
                                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                        //Saving values to editor
                                        editor.commit();
                                        Toast.makeText(getApplicationContext(),
                                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(UpdateSosmed.this, MainActivity2.class);
                                        startActivity(intent);
                                        finish();


                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                    }
                                } catch (JSONException e) {
                                    // JSON error
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();

                                NetworkResponse response = error.networkResponse;
                                String errorMsg = "";
                                if(response != null && response.data != null){
                                    String errorString = new String(response.data);
                                    Log.i("log error", errorString);
                                }
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("noHp", String.valueOf(noHp.getText()));
                        params.put("email", String.valueOf(email.getText()));
                        params.put("nama", String.valueOf(nama_lengkap.getText()));
                        params.put("password", String.valueOf(pwd.getText()));
//                        Log.i("sending ", params.toString());

                        return params;
                    }

                };


                // Add the realibility on the connection.
                request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

                // Start the request immediately
                queue.add(request);
            }
        }
    });
    }

    private boolean validate() {
        boolean temp = true;

        String pass=pwd.getText().toString();
        String cpass=ulang_pwd.getText().toString();
        String hp=noHp.getText().toString();
        String Nnama= nama_lengkap.getText().toString();

        if(!pass.equals(cpass)){
            ulang_pwd.setError("Password tidak sama");
            ulang_pwd.requestFocus();
            temp = false;
        }
        else if(hp.equals("")){
            noHp.setError("No HP tidak boleh kosong");
            noHp.requestFocus();
            temp = false;
        }else if(Nnama.equals("")){
            nama_lengkap.setError("Nama tidak boleh kosong");
            nama_lengkap.requestFocus();
            temp = false;
        }else if(pass.equals("")){
            pwd.setError("Password tidak boleh kosong");
            pwd.requestFocus();
            temp = false;
        }
        return temp;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
