package tukangdagang.id.co.tukangdagang_koperasi.app;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tukangdagang.id.co.tukangdagang_koperasi.fragment.faCarijasa;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.faCaripedagang;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.faCariproduk;

public class pagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public pagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                faCariproduk tab1 = new faCariproduk();
                return tab1;
            case 1:
                faCarijasa tab2 = new faCarijasa();
                return  tab2;
            case 2:
                faCaripedagang tab3 = new faCaripedagang();
                return  tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
