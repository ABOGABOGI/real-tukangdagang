package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URLDaftar;

public class Register extends AppCompatActivity {
    private TextView linkLogin ;
    private Button btnDaftar;
    private EditText noHp,nama,email,pwd,ulangpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Daftar Tukang Dagang");
        linkLogin = (TextView)findViewById(R.id.linklogin);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        noHp = (EditText)findViewById(R.id.noHp);
        nama = (EditText)findViewById(R.id.nama);
        email = (EditText)findViewById(R.id.email);
        pwd = (EditText)findViewById(R.id.password);
        ulangpwd = (EditText)findViewById(R.id.password);
        masuk();
        daftar();


    }

    public void daftar(){
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLDaftar, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("success")) {
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", String.valueOf(noHp.getText()));
                            params.put("email", String.valueOf(email.getText()));
                            params.put("first_name", String.valueOf(nama.getText()));
                            params.put("password", String.valueOf(pwd.getText()));
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(),"Password Yang anda Input Tidak sama",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public boolean validate() {
        boolean temp=true;

        String pass=pwd.getText().toString();
        String cpass=ulangpwd.getText().toString();
        if(!pass.equals(cpass)){
            Toast.makeText(Register.this,"Password Not matching",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;
    }

    public void masuk(){
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,Login.class);
                startActivity(i);
            }
        });
    }

}
