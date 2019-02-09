package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener  {

    private static final String TAG = "MainActivity";
    TextView smsCountTxt;
    int pendingSMSCount = 10;
    private long backPrassedTime;

    BottomNavigationView bottomNavigationView;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView img_profile;
    private TextView tv_email,tv_nama;
    private ActionBarDrawerToggle  actionBarDrawerToggle;

    private GoogleApiClient googleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Menginisiasi  NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_apps);

        img_profile =headerView.findViewById(R.id.img_profile);
        tv_nama =headerView.findViewById(R.id.tv_nama);
        tv_email = headerView.findViewById(R.id.tv_email);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
        String namafb = extras.getString("first_name");
        String emailfb = extras.getString("emailfb");
        String imgfb = extras.getString("imgfb");
//
    tv_nama.setText(namafb);
    tv_email.setText(emailfb);

//            //Fetching email from shared preferences
//            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//            String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
//            String nama = sharedPreferences.getString(Config.NAME_SHARED_PREF,"Not Available");
//            Log.d("fbb",email);
//            //Showing the current logged in email to textview
//            tv_nama.setText(nama);
//            tv_email.setText(email);

    Glide.with(this)
            .load(imgfb)
//                .crossFade()
//                .placeholder(R.mipmap.ic_launcher)
            .into(img_profile);

}

        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Kode di sini akan merespons setelah drawer menutup disini kita biarkan kosong
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                //  Kode di sini akan merespons setelah drawer terbuka disini kita biarkan kosong
                super.onDrawerOpened(drawerView);
            }
        };
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

            tv_nama.setText(account.getDisplayName());
            tv_email.setText(account.getEmail());
//            idTextView.setText(account.getId());
//            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//            String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
//            String nama = sharedPreferences.getString(Config.NAME_SHARED_PREF,"Not Available");
//            Log.d("fbb",email);
//            //Showing the current logged in email to textview
//            tv_nama.setText(nama);
//            tv_email.setText(email);

            Glide.with(this).load(account.getPhotoUrl()).into(img_profile);

        }
//        else {
//            Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show();
//        }
    }


/////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_notifications);

        View actionView = MenuItemCompat.getActionView(menuItem);
        smsCountTxt = (TextView) actionView.findViewById(R.id.notification_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
//        MenuItem searchViewItem = menu.findItem(R.id.action_search);
//        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
//        searchViewAndroidActionBar.setQueryHint("Cari Tukang Dagang");
//        searchViewAndroidActionBar.setBackgroundColor(Color.WHITE);
//        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchViewAndroidActionBar.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()) {

            case R.id.action_notifications: {
//                Toast.makeText(this,"ini notifikasi",Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(MainActivity.this,Notifikasi.class);
                startActivity(inten);
                return true;
            }
//            case R.id.search: {
////                Toast.makeText(this,"ini notifikasi",Toast.LENGTH_SHORT).show();
//                Intent inten = new Intent(MainActivity.this,Cari.class);
//                startActivity(inten);
//                return true;
//            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (smsCountTxt != null) {
            if (pendingSMSCount == 0) {
                if (smsCountTxt.getVisibility() != View.GONE) {
                    smsCountTxt.setVisibility(View.GONE);
                }
            } else {
                smsCountTxt.setText(String.valueOf(Math.min(pendingSMSCount, 99)));
                if (smsCountTxt.getVisibility() != View.VISIBLE) {
                    smsCountTxt.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    Home homeFragment = new Home();
    Favourite favouriteFragment = new Favourite();
    Keranjang keranjangFragment = new Keranjang();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.navigation_apps:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.navigation_faforit:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, favouriteFragment).commit();
                return true;

            case R.id.navigation_keranjang:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, keranjangFragment).commit();
                return true;


        }
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.navigation1: {
                Toast.makeText(getApplicationContext(), "Beranda Telah Dipilih", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.navigation6: {
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Apa Anda Ingin Keluar ?")
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");
                        editor.putString(Config.NAME_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        LoginManager.getInstance().logOut();
                        Intent i = new Intent(MainActivity.this,Login.class);
                        startActivity(i);
                        finish();

                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if (status.isSuccess()) {
                                    Intent i = new Intent(MainActivity.this,Login.class);
                                    startActivity(i);
                                    finish();
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
                return true;
            }
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {

        if (backPrassedTime + 2000 > System.currentTimeMillis()){

            System.exit(0);
            super.onBackPressed();
            finish();
            return;
        }else{
            Toast.makeText(getBaseContext(),"Tekan sekali lagi untuk keluar",Toast.LENGTH_SHORT).show();
        }
        backPrassedTime = System.currentTimeMillis();

    }
}
