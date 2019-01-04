package tukangdagang.id.co.tukangdagang_koperasi.carimakanan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.RincianBarang;
import tukangdagang.id.co.tukangdagang_koperasi.RincianMakanan;
import tukangdagang.id.co.tukangdagang_koperasi.caribarang.ModelBarang;
import tukangdagang.id.co.tukangdagang_koperasi.caribarang.RvBarangAdapter;

import static tukangdagang.id.co.tukangdagang_koperasi.app.params.path;


public class RvMakananAdapter extends RecyclerView.Adapter<RvMakananAdapter.MyViewHolder> {

    private Context mContext ;
    private List<ModelMakanan> mData ;
    ArrayList<ModelMakanan> arrayList;


    public RvMakananAdapter(Context mContext, List<ModelMakanan> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelMakanan>();
        this.arrayList.addAll(mData);
    }

    @Override
    public tukangdagang.id.co.tukangdagang_koperasi.carimakanan.RvMakananAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item,parent,false);
        return new tukangdagang.id.co.tukangdagang_koperasi.carimakanan.RvMakananAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvMakananAdapter.MyViewHolder holder, final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        final String Harga = formatRupiah.format((double)mData.get(position).getHarga());
        holder.title.setText(mData.get(position).getTitle());
        holder.harga.setText(Harga);
        holder.alamat.setText(mData.get(position).getAlamat());
        Glide.with(mContext)
                .load(path + mData.get(position).getThumbnail())

                .into(holder.gambar);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,RincianBarang.class);

                // passing data
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Kategori",mData.get(position).getCategory());
                intent.putExtra("Harga",Harga);
                intent.putExtra("Alamat",mData.get(position).getAlamat());
                intent.putExtra("Berat",mData.get(position).getBerat());
                intent.putExtra("Deskripsi",mData.get(position).getDescription());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                // start the activity
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,harga,alamat;
        ImageView gambar;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title) ;
            harga = (TextView) itemView.findViewById(R.id.harga) ;
            alamat = (TextView) itemView.findViewById(R.id.alamat) ;
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


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
            for (ModelMakanan model : arrayList){
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    mData.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }


}
