package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan.AdapterDaftarsimpanan;
import tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan.ModelDaftarsimpanan;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class Daftarsimpanan extends Fragment {

    ListView listView;
    AdapterDaftarsimpanan adapter;
    String[] title;
    String[] description;
    int[] icon;
    ArrayList<ModelDaftarsimpanan> arrayList = new ArrayList<ModelDaftarsimpanan>();

    public Daftarsimpanan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getdata();
        
        return inflater.inflate(R.layout.fragment_daftarsimpanan, container, false);
    }

    private void getdata() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.URL_KOPERASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            for (int i = 0; i < koperasiArray.length(); i++) {

                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                Log.d("asd", response);


                                ModelDaftarsimpanan model = new ModelDaftarsimpanan(koperasiobject.getString("nama_koperasi"),
                                        koperasiobject.getString("simpanan_pokok"),
                                        koperasiobject.getString("logo_koperasi"),
                                        koperasiobject.getString("id"),
                                        koperasiobject.getString("rating_koperasi")
                                );

                                arrayList.add(model);
                            }
                            listView = getActivity().findViewById(R.id.listDaftarsimpanan);
                            adapter = new AdapterDaftarsimpanan(getContext(),arrayList);


                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity2) getActivity()).setActionBarTitle("Daftar Simpanan");
    }

}
