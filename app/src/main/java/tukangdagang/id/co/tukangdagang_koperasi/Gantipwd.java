package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

public class Gantipwd extends AppCompatActivity {
Button btn_gantipwd;
EditText pwd,pwdbaru,ulangpwd;
String idprofile ="";
    private String url_gantipwd = Config.URL+Config.FGantipwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gantipwd);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Ganti Password");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_gantipwd = findViewById(R.id.btn_gantipwd);
        pwd = findViewById(R.id.pwd);
        pwdbaru = findViewById(R.id.pwdbaru);
        ulangpwd = findViewById(R.id.ulangpwd);
        Intent intent = getIntent();
        idprofile = intent.getExtras().getString("idprofile");

        ganti();

    }

    private void ganti() {
        btn_gantipwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String n_pwd = pwd.getText().toString();
                final String n_pwdbaru = pwdbaru.getText().toString();
                String n_ulangpwd = ulangpwd.getText().toString();
                if (!n_ulangpwd.equals(n_pwdbaru)){
                    Toast.makeText(getApplicationContext(),"password tidak sama",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Gantipwd.this);
                    builder.setMessage("Anda yakin ingin Mengubah Password ?")
                            .setCancelable(false)
                            .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                    final ProgressDialog progressDialog = new ProgressDialog(Gantipwd.this);
                                    progressDialog.setMessage("Loading...");
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();


                    StringRequest stringRequest = new StringRequest(Request.Method.POST,url_gantipwd ,
                            new Response.Listener < String > () {
                                @Override
                                public void onResponse(String response) {
                                    //If we are getting success from server
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                    Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("tee", error.toString());
                                    progressDialog.dismiss();
                                }
                            }) {
                        @Override
                        protected Map< String, String > getParams() throws AuthFailureError {
                            Map < String, String > params = new HashMap< >();

                            params.put("id", idprofile);
                            params.put("pwd", n_pwd);
                            params.put("pwdbaru", n_pwdbaru);

                            //returning parameter
                            return params;
                        }
                    };
                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(Gantipwd.this);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(stringRequest);

                                }
                            })
                     .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

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
