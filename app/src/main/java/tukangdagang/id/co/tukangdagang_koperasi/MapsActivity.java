package tukangdagang.id.co.tukangdagang_koperasi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng center, latLng;
    public String judul,judul1,id1;

    private String url_koperasi = Config.URL+Config.Fkoperasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_koperasi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray barangArray = obj.getJSONArray("result");
                            Log.d("hai",response);
                            for (int i = 0; i < barangArray.length(); i++) {


                                JSONObject barangobject = barangArray.getJSONObject(i);
//                                String link = barangobject.getString("lat") ;
//                                Log.d("asd", link);
                                judul = barangobject.getString("nama_koperasi") ;


                                LatLng alamat = new LatLng(Double.parseDouble(barangobject.getString("lat_koperasi")), Double.parseDouble(barangobject.getString("lng_koperasi")));

                                mMap.addMarker(new MarkerOptions().position(alamat).title(judul));
//                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.koprasi)));


                                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        for (int i = 0; i < barangArray.length(); i++){
                                            JSONObject barangobject = null;
                                            try {
                                                barangobject = barangArray.getJSONObject(i);
                                                judul1 = barangobject.getString("nama_koperasi") ;
                                                id1 = barangobject.getString("id") ;
//                                                Toast.makeText(getApplicationContext(),String.valueOf(marker.getTitle()),Toast.LENGTH_SHORT).show();
                                                if(judul1.equals(marker.getTitle())){
                                                    Intent intent = new Intent(MapsActivity.this,BeritaKoprasi.class);
                                                    intent.putExtra("namakoperasi", String.valueOf(judul1));
                                                    intent.putExtra("idkoperasi", String.valueOf(id1));
                                                    startActivity(intent);
//                                                    Toast.makeText(getApplicationContext(), finalI1,Toast.LENGTH_SHORT).show();
                                                            }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }



                                        }
//



                                    }
                                });



                                mMap.isBuildingsEnabled();
                                mMap.isTrafficEnabled();
                                mMap.getUiSettings().isCompassEnabled();
                                mMap.getUiSettings().isZoomControlsEnabled();
                                mMap.getUiSettings().isRotateGesturesEnabled();




                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        LatLng myPosition;
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        Location lprovider = locationManager.getLastKnownLocation(provider);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.d("kjkj", String.valueOf(location));


        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng coordinate = new LatLng(latitude, longitude);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 17);
            mMap.animateCamera(yourLocation);
            mMap.addCircle(
                    new CircleOptions()
                            .center(new LatLng(location.getLatitude(), location.getLongitude()))
                            .radius(500.0)
                            .strokeWidth(3f)
                            .strokeColor(Color.RED)
                            .fillColor(Color.argb(23,150,50,50))
            );

        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
