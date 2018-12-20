package tukangdagang.id.co.tukangdagang_koperasi.carijasa;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.RincianJasa;
import tukangdagang.id.co.tukangdagang_koperasi.carijasa.ModelJasa;

public class RvJasaAdapter extends RecyclerView.Adapter<tukangdagang.id.co.tukangdagang_koperasi.carijasa.RvJasaAdapter.MyViewHolder> {

    private Context mContext ;
    private List<ModelJasa> mData ;
    ArrayList<ModelJasa> arrayList;


    public RvJasaAdapter(Context mContext, List<ModelJasa> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelJasa>();
        this.arrayList.addAll(mData);
    }

    @Override
    public tukangdagang.id.co.tukangdagang_koperasi.carijasa.RvJasaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item,parent,false);
        return new tukangdagang.id.co.tukangdagang_koperasi.carijasa.RvJasaAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(tukangdagang.id.co.tukangdagang_koperasi.carijasa.RvJasaAdapter.MyViewHolder holder, final int position) {

        holder.title.setText(mData.get(position).getTitle());
        holder.harga.setText(mData.get(position).getHarga());
        holder.alamat.setText(mData.get(position).getAlamat());
        holder.gambar.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,RincianJasa.class);

                // passing data to the book activity
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Kategori",mData.get(position).getCategory());
                intent.putExtra("Harga",mData.get(position).getHarga());
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
            for (ModelJasa model : arrayList){
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    mData.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }


}
