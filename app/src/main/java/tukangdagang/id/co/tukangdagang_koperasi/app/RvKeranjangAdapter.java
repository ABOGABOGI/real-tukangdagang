package tukangdagang.id.co.tukangdagang_koperasi.app;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.RincianJasa;

public class RvKeranjangAdapter extends RecyclerView.Adapter<RvKeranjangAdapter.MyViewHolder> {

    private Context mContext ;
    private List<ModelKeranjang> mData ;
    ArrayList<ModelKeranjang> arrayList;


    public RvKeranjangAdapter(Context mContext, List<ModelKeranjang> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelKeranjang>();
        this.arrayList.addAll(mData);
    }

    @Override
    public RvKeranjangAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.list_keranjang,parent,false);
        return new RvKeranjangAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvKeranjangAdapter.MyViewHolder holder, final int position) {

        holder.title.setText(mData.get(position).getTitle());
        holder.harga.setText(mData.get(position).getHarga());
        holder.subtotal.setText(mData.get(position).getSubtotal());
        holder.gambar.setImageResource(mData.get(position).getThumbnail());
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
//                Toast.makeText(v.getContext(), "Deleted " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mContext,RincianJasa.class);
//
//                // passing data to the book activity
//                intent.putExtra("Title",mData.get(position).getTitle());
//                intent.putExtra("Kategori",mData.get(position).getCategory());
//                intent.putExtra("Harga",mData.get(position).getHarga());
//                intent.putExtra("Subtotal",mData.get(position).getSubtotal());
//                intent.putExtra("Berat",mData.get(position).getBerat());
//                intent.putExtra("Deskripsi",mData.get(position).getDescription());
//                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
//                // start the activity
//                mContext.startActivity(intent);
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,harga,subtotal;
        ImageView gambar,hapus;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title) ;
            harga = (TextView) itemView.findViewById(R.id.harga) ;
            subtotal = (TextView) itemView.findViewById(R.id.subtotal) ;
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
            hapus = (ImageView) itemView.findViewById(R.id.hapus);
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
            for (ModelKeranjang model : arrayList){
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    mData.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }


}
