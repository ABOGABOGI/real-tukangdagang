package tukangdagang.id.co.tukangdagang_koperasi.caripinjaman;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.BeritaKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.DaftarAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.R;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.path;

public class CaripinjamanAdapter extends RecyclerView.Adapter<CaripinjamanAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Model> mData ;
    ArrayList<Model> arrayList;

    public CaripinjamanAdapter(Context mContext, List<Model> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<Model>();
        this.arrayList.addAll(mData);
    }

    @Override
    public tukangdagang.id.co.tukangdagang_koperasi.caripinjaman.CaripinjamanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_cari_pinjaman,parent,false);
        return new tukangdagang.id.co.tukangdagang_koperasi.caripinjaman.CaripinjamanAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CaripinjamanAdapter.MyViewHolder holder, final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        holder.mTitleTv.setText(mData.get(position).getTitle());
        holder.mDescTv.setText(formatRupiah.format((double) Double.valueOf(mData.get(position).getMinimal()))+" - "+formatRupiah.format((double) Double.valueOf(mData.get(position).getMaximal())));
        holder.ratingKoperasi.setRating(Float.valueOf(mData.get(position).getRating()));
        holder.cm_alamat.setText(String.valueOf(mData.get(position).getAlamat()));
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(path + mData.get(position).getIcon())

                .into(holder.mIconIv);

        holder.btnDaftarKop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DaftarAnggota.class);
                i.putExtra("idkoperasi", mData.get(position).getId());
                mContext.startActivity(i);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BeritaKoprasi.class);
                intent.putExtra("namakoperasi", mData.get(position).getTitle());
                intent.putExtra("idkoperasi", mData.get(position).getId());

//                intent.putExtra("contentTv", "This is Battery detail...");
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTv, mDescTv,cm_alamat;
        ImageView mIconIv;
        Button btnDaftarKop;
        RatingBar ratingKoperasi;

        public MyViewHolder(View itemView) {
            super(itemView);

            btnDaftarKop = (Button) itemView.findViewById(R.id.btn_dafatar_koprasi) ;
            mTitleTv = (TextView) itemView.findViewById(R.id.mainTitle) ;
            mDescTv = (TextView) itemView.findViewById(R.id.mainDesc) ;
            mIconIv = (ImageView) itemView.findViewById(R.id.mainIcon) ;
            cm_alamat = (TextView) itemView.findViewById(R.id.cm_alamat) ;
            ratingKoperasi = (RatingBar) itemView.findViewById(R.id.ratingKoperasi) ;



        }
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        mData.clear();
        if (charText.length()==0){
            mData.addAll(arrayList);
        }
        else {
            for (Model model : arrayList){
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    mData.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }


}
