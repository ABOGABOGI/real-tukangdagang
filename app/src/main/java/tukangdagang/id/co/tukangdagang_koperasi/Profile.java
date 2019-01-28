package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.EMAIL_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.NAME_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.SHARED_PREF_NAME;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_IMG_KOPERASI;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_PROFILE;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_noktp;

public class Profile extends Fragment implements GoogleApiClient.OnConnectionFailedListener{

    Button btn_logout,btn_gantipwd;
    TextView scnama,info_email;
    ImageView avatar;
    private GoogleApiClient googleApiClient;
    String idprofile = "";
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        btn_logout = rootView.findViewById(R.id.btn_logout);
        btn_gantipwd = rootView.findViewById(R.id.btn_gantipwd);
        scnama= rootView.findViewById(R.id.scnama);
        info_email= rootView.findViewById(R.id.info_email);
        avatar= rootView.findViewById(R.id.avatar);

        getdata();
        gantipwd();

        logout();
        return rootView;
    }



    private void getdata() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray profileArray = obj.getJSONArray("result");
                            Log.d("resul",response);

                                JSONObject profileobject = profileArray.getJSONObject(0);
                                String loginwith = profileobject.getString("loginwith");
                                idprofile = profileobject.getString("id");
                                String status = "";
                                if(loginwith.equals("google")){
                                    status = "Google | ";
                                    Glide.with(getContext())
                                    .load(profileobject.getString("avatar"))
                                    .into(avatar);
                                }else if(loginwith.equals("fb")){
                                    status = "Facebook | ";
                                    Glide.with(getContext())
                                            .load(profileobject.getString("avatar"))
                                            .into(avatar);
                                }else{
                                    status = "";
                                    avatar.setImageResource(R.drawable.profile);
                                    btn_gantipwd.setVisibility(View.VISIBLE);
                                }

                            scnama.setText(profileobject.getString("first_name"));
                            info_email.setText(status+profileobject.getString("email"));
//

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Tidak ada Koneksi", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map< String, String > getParams() throws AuthFailureError {
                Map < String, String > params = new HashMap< >();
                params.put("email", email);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);




    }
    private void gantipwd() {
        btn_gantipwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Gantipwd.class);
                intent.putExtra("idprofile", idprofile);
                startActivity(intent);
            }
        });
    }

    private void logout() {
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Logout")
                        .setMessage("Apa Anda Ingin Keluar ?")
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //Getting out sharedpreferences
                                SharedPreferences preferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                                //Getting editor
                                SharedPreferences.Editor editor = preferences.edit();

                                //Puting the value false for loggedin
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                                //Putting blank value to email
                                editor.putString(Config.EMAIL_SHARED_PREF, "");
                                editor.putString(Config.NAME_SHARED_PREF, "");
                                editor.putString(Config.IMAGE_SHARED_PREF, "");

                                //Saving the sharedpreferences
                                editor.commit();

                                LoginManager.getInstance().logOut();
                                Intent i = new Intent(getActivity(),Login.class);
                                startActivity(i);
                                getActivity().finish();

                                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        if (status.isSuccess()) {
                                            Intent i = new Intent(getApplicationContext(),Login.class);
                                            startActivity(i);
                                            getActivity().finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"error session", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity2) getActivity()).setActionBarTitle("Profile");

    }

    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }

}
