package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_status;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_status_nomor;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_status_upload;


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private Button btnlogin,btnGoogle,btnfb;
    CallbackManager callbackManager;
    ProgressDialog mDialog;
    private TextView linkDaftar;
    private EditText email;
    private EditText password;

    private GoogleApiClient googleApiClient;
    public static final int SIGN_IN_CODE = 777;
    private boolean loggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnfb = findViewById(R.id.btnFb);
        linkDaftar = (TextView)findViewById(R.id.linkDaftar);
        //Initializing views
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        checkNetworkConnectionStatus();
        daftar();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);

            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });



        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile","email"));
            }
        });






        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                mDialog = new ProgressDialog(Login.this);
                mDialog.setMessage("Menerima data..");
                mDialog.show();

                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();

                        Log.d("response",response.toString());

                        getdata(object);
                    }

                });
                //request graph api
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(Login.this, "Login Cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(Login.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //jika sudah login fb
        if(AccessToken.getCurrentAccessToken() !=null){
            String tes = "https://graph.facebook.com/"+AccessToken.getCurrentAccessToken().getUserId()+"/picture?width=250&height=250";
            Intent i = new Intent(Login.this, MainActivity2.class);
            i.putExtra("imgfb",tes);
            startActivity(i);
            finish();
        }
        printKeyHash();
    }


    //fb
    private void getdata(JSONObject object) {
        try{
            URI profile_picture = new URI("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
//            nilai_email = object.getString("email");
            final String nilai_emailfb = object.getString("email");
            final String nilai_namafb = object.getString("first_name")+" "+object.getString("last_name");
            final String nilai_imgfb = profile_picture.toString();
            Log.d("gambarfb",nilai_imgfb);


            //////////////////////////////////////////////////////////////////////

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URLLoginWith,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //If we are getting success from server
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray profileArray = obj.getJSONArray("result");

                                JSONObject profileobject = profileArray.getJSONObject(0);
                                Log.d("nihil", profileobject.getString("username"));
                                String id_profile = profileobject.getString("id");
                                //Creating a shared preference
                                SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                //Adding values to editor
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                editor.putString(Config.EMAIL_SHARED_PREF, nilai_emailfb);
                                editor.putString(Config.NAME_SHARED_PREF, nilai_namafb);
                                editor.putString(Config.LOGINWITH_SHARED_PREF, "fb");
                                editor.putString(Config.IMAGE_SHARED_PREF, nilai_imgfb);
                                editor.putString(Config.PROFILE_ID, id_profile);
                                editor.putString(n_status_nomor, "0");
                                editor.putString(n_info_status, "0");
                                editor.putString(n_status_upload, "0");

                                //Saving values to editor
                                editor.commit();
                                Intent i = new Intent(Login.this, MainActivity2.class);
                                startActivity(i);
                                finish();
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Config.KEY_EMAIL, nilai_emailfb);
                    params.put("first_name", nilai_namafb);
                    params.put("loginwith", "fb");
                    params.put("gambar", nilai_imgfb);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            /////////////////////////////////////////////////////////////////////
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("tukangdagang.id.co.tukangdagang_koperasi",PackageManager.GET_SIGNATURES);

            for(Signature signature:info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash",Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void checkNetworkConnectionStatus() {
        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()){ //connected with either mobile or wifi
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected){ //wifi connected
                Log.d("koneksi","konek dengan wifi");
            }
            else if (mobileConnected){ //mobile data connected
                Log.d("koneksi","konek dengan mobile data");
            }
        }
        else { //no internet connection
            Toast.makeText(this,"Tidak Ada koneksi internet",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            final String nilai_emailGg = account.getEmail();
            final String nilai_namaGg = account.getDisplayName();
            final String nilai_imgGg = account.getPhotoUrl().toString();
            Log.d("gambargg",nilai_imgGg);
            ////////////////////////////////////////////////////

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URLLoginWith,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //If we are getting success from server
                            Log.d("tampildata",response);

                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray profileArray = obj.getJSONArray("result");

                                JSONObject profileobject = profileArray.getJSONObject(0);
                                Log.d("nihil", profileobject.getString("username"));
                                String id_profile = profileobject.getString("id");

                                //Creating a shared preference
                                SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                //Adding values to editor
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                editor.putString(Config.EMAIL_SHARED_PREF, nilai_emailGg);
                                editor.putString(Config.NAME_SHARED_PREF, nilai_namaGg);
                                editor.putString(Config.IMAGE_SHARED_PREF, nilai_imgGg);
                                editor.putString(Config.LOGINWITH_SHARED_PREF, "google");
                                editor.putString(Config.PROFILE_ID, id_profile);

                                editor.putString(n_status_nomor, "0");
                                editor.putString(n_info_status, "0");
                                editor.putString(n_status_upload, "0");

                                //Saving values to editor
                                editor.commit();
                                goMainScreen();

                            }catch (JSONException e) {
                                    e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Config.KEY_EMAIL, nilai_emailGg);
                    params.put("first_name", nilai_namaGg);
                    params.put("loginwith", "google");
                    params.put("gambar", nilai_imgGg);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            ///////////////////////////////////////////////////

        } else {
            Log.d("gagal","Login gagal");
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private void daftar(){
        linkDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the SimpananF Activity
            Intent intent = new Intent(Login.this, MainActivity2.class);
            startActivity(intent);
            finish();
        }
    }

    private void login(){
        //Getting values from edit texts
        final String nilai_email = email.getText().toString().trim();
        final String nilai_password = password.getText().toString().trim();
        if (nilai_email.equals("")){
            Toast.makeText(getApplicationContext(),"Email harus diisi",Toast.LENGTH_SHORT).show();
        }else if (nilai_password.equals("")){
            Toast.makeText(getApplicationContext(),"Password harus diisi",Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(Login.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();


            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("tampildata",response);
                            //If we are getting success from server
                            if (!response.equalsIgnoreCase(Config.LOGIN_FAILURE)) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray profileArray = obj.getJSONArray("result");

                                    JSONObject profileobject = profileArray.getJSONObject(0);
                                    Log.d("nihil", profileobject.getString("username"));
                                    String id_profile = profileobject.getString("id");

                                    //Creating a shared preference
                                    SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                    //Creating editor to store values to shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();


                                    //Adding values to editor
                                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                    editor.putString(Config.EMAIL_SHARED_PREF, nilai_email);
                                    editor.putString(Config.PROFILE_ID, id_profile);
                                    editor.putString(Config.LOGINWITH_SHARED_PREF, "");

                                    editor.putString(n_status_nomor, "0");
                                    editor.putString(n_info_status, "0");
                                    editor.putString(n_status_upload, "0");


                                    //Saving values to editor
                                    editor.commit();

                                    //Starting profile activity
                                    Intent intent = new Intent(Login.this, MainActivity2.class);
                                    startActivity(intent);
                                    finish();



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                //If the server response is not success
                                //Displaying an error message on toast
                                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data",Toast.LENGTH_LONG).show();
                            //You can handle error here if you want
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Config.KEY_EMAIL, nilai_email);
                    params.put(Config.KEY_PASSWORD, nilai_password);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

}
