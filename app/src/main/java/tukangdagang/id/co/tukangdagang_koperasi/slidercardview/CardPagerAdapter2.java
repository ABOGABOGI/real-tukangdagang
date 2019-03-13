package tukangdagang.id.co.tukangdagang_koperasi.slidercardview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.R;


public class CardPagerAdapter2 extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    Context mContext;
    private float mBaseElevation;

    public CardPagerAdapter2() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter2, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardViewA);
        View.OnClickListener onClickListener = null;

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        Intent intent = new Intent(view.getContext(),UnitUsaha.class);
//
//                        view.getContext().startActivity(intent);

            }
        };

        cardView.setOnClickListener(onClickListener);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }
    @Override
    public float getPageWidth(final int position) {
        return 0.6f;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
//        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        ImageView img = (ImageView) view.findViewById(R.id.img);
//        titleTextView.setText(item.getTitle());
//        Glide.with(view.getContext())
//                .load("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg")
//                .into(img);
        Picasso.with(view.getContext())
                .load(item.getImg())
                .into(img);
//        img.setImageResource(R.drawable.poster);
    }

}
