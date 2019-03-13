package tukangdagang.id.co.tukangdagang_koperasi;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import tukangdagang.id.co.tukangdagang_koperasi.app.pagerAdapter;

public class Cari extends AppCompatActivity implements Profile.OnFragmentInteractionListener,Pinjaman.OnFragmentInteractionListener,Simpanan.OnFragmentInteractionListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari);
        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Produk"));
        tabLayout.addTab(tabLayout.newTab().setText("Jasa"));
        tabLayout.addTab(tabLayout.newTab().setText("Pedagang"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final pagerAdapter adapter = new pagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
//        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.list_koprasi, menu);
//
//        MenuItem myActionMenuItem = menu.findItem(R.id.cariAll);
////        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
////        myActionMenuItem.expandActionView(); // Expand the search menu item in order to show by default the query
////        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String s) {
////                return false;
////            }
////
////            @Override
////            public boolean onQueryTextChange(String s) {
////                if (TextUtils.isEmpty(s)){
//////                    myAdapter.filter("");
////                }
////                else {
//////                    myAdapter.filter(s);
////                }
////                return true;
////            }
////        });
//        return true;
//    }
}
