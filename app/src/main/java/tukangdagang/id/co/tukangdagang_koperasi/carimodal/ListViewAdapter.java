package tukangdagang.id.co.tukangdagang_koperasi.carimodal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.BeritaKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.DaftarAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.Ekoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.MainActivity;
import tukangdagang.id.co.tukangdagang_koperasi.MapsActivity;
import tukangdagang.id.co.tukangdagang_koperasi.R;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.path;

public class ListViewAdapter extends BaseAdapter{

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<Model> modellist;
    ArrayList<Model> arrayList;

    //constructor
    public ListViewAdapter(Context context, List<Model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Model>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mTitleTv, mDescTv,cm_alamat;
        ImageView mIconIv;
        Button btnDaftarKop;
        RatingBar ratingKoperasi;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row_modal, null);

            //locate the views in row_modal.xml
            holder.btnDaftarKop = view.findViewById(R.id.btn_dafatar_koprasi);
            holder.mTitleTv = view.findViewById(R.id.mainTitle);
            holder.mDescTv = view.findViewById(R.id.mainDesc);
            holder.mIconIv = view.findViewById(R.id.mainIcon);
            holder.cm_alamat = view.findViewById(R.id.cm_alamat);
            holder.ratingKoperasi = view.findViewById(R.id.ratingKoperasi);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(postition).getTitle());
        holder.mDescTv.setText(modellist.get(postition).getDesc());
        holder.ratingKoperasi.setRating(Float.valueOf(modellist.get(postition).getRating()));
        holder.cm_alamat.setText(String.valueOf(modellist.get(postition).getAlamat()));
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(path + modellist.get(postition).getIcon())

                .into(holder.mIconIv);


        holder.btnDaftarKop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,DaftarAnggota.class);
                i.putExtra("idkoperasi", modellist.get(postition).getId());
                mContext.startActivity(i);
            }
        });

        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BeritaKoprasi.class);
                intent.putExtra("namakoperasi", modellist.get(postition).getTitle());
                intent.putExtra("idkoperasi", modellist.get(postition).getId());

//                intent.putExtra("contentTv", "This is Battery detail...");
                mContext.startActivity(intent);
            }
        });


        return view;
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length()==0){
            modellist.addAll(arrayList);
        }
        else {
            for (Model model : arrayList){
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

}