package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UnitUsaha extends AppCompatActivity {
private TextView lihatMap;
private TextView lihatKalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_usaha);
        lihatMap = (TextView) findViewById(R.id.lihatMap);
        lihatKalender = (TextView) findViewById(R.id.lihatKalender);

        lihatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UnitUsaha.this,MapsActivity.class);
                startActivity(i);
            }
        });

        lihatKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnitUsaha.this,CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
