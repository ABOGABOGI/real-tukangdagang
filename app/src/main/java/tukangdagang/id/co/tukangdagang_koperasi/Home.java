package tukangdagang.id.co.tukangdagang_koperasi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.View;
//import android.widget.GridLayout;
//import android.widget.GridView;
import android.widget.Toast;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
//
//import id.tukangdagang.com.tukangdagangdevel._sliders.FragmentSlider;
//import id.tukangdagang.com.tukangdagangdevel._sliders.SliderIndicator;
//import id.tukangdagang.com.tukangdagangdevel._sliders.SliderPagerAdapter;
//import id.tukangdagang.com.tukangdagangdevel._sliders.SliderView;
//


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    //    private SliderPagerAdapter mAdapter;
//    private SliderIndicator mIndicator;
//
//    private SliderView sliderView;
//    private LinearLayout mLinearLayout;
    GridLayout mainGrid;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mainGrid = (GridLayout) rootView.findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);

        // Inflate the layout for this fragment
//        sliderView = (SliderView) rootView.findViewById(R.id.sliderView);
//        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.pagesContainer);
//        setupSlider();
        return rootView;
    }
    //    private void setupSlider() {
//        sliderView.setDurationScroll(800);
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-1.jpg"));
//        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-2.jpg"));
//        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-3.jpg"));
//        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-4.jpg"));
//
//        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
//        sliderView.setAdapter(mAdapter);
//        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
//        mIndicator.setPageCount(fragments.size());
//        mIndicator.show();
//    }
    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    intent.putExtra("info","This is activity from card item index  "+finalI);
                    startActivity(intent);

                }
            });
        }
    }

}
