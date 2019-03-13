package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import me.wangyuwei.particleview.ParticleView;
import tukangdagang.id.co.tukangdagang_koperasi.app.AppController;

public class Splash extends AppCompatActivity {

    ParticleView mPvGithub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPvGithub = (ParticleView) findViewById(R.id.pv_github);

        mPvGithub.startAnim();

        mPvGithub.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                Intent intent = new Intent(Splash.this, WelcomeActivity.class);
                Splash.this.startActivity(intent);
                finish();
            }
        });

    }
}
