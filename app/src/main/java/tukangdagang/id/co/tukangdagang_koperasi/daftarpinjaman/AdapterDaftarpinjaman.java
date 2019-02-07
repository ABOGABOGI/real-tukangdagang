package tukangdagang.id.co.tukangdagang_koperasi.daftarpinjaman;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.Simpanan;
import tukangdagang.id.co.tukangdagang_koperasi.Tampilpinjaman;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.path;

public class AdapterDaftarpinjaman extends BaseAdapter{

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<ModelDaftarpinjaman> modellist;
    ArrayList<ModelDaftarpinjaman> arrayList;

    //constructor
    public AdapterDaftarpinjaman(Context context, List<ModelDaftarpinjaman> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ModelDaftarpinjaman>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mTitleTv, mDescTv;
        ImageView mIconIv;

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
            view = inflater.inflate(R.layout.list_daftarpinjaman, null);

            //locate the views in row_cari_pinjamanpinjaman.xml
            holder.mTitleTv = view.findViewById(R.id.mainTitle);
            holder.mDescTv = view.findViewById(R.id.mainDesc);
            holder.mIconIv = view.findViewById(R.id.mainIcon);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(postition).getTitle());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.mDescTv.setText(modellist.get(postition).getJatuhtempo()+" Hari lagi");

        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(path + modellist.get(postition).getIcon())

                .into(holder.mIconIv);


        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Tampilpinjaman.class);
                intent.putExtra("namakoperasi", modellist.get(postition).getTitle());
                intent.putExtra("logokoperasi", modellist.get(postition).getIcon());
                intent.putExtra("totalpinjaman", modellist.get(postition).getTotalpinjaman());
                intent.putExtra("totalbayar", modellist.get(postition).getTotalbayar());
                intent.putExtra("tenor", modellist.get(postition).getTenor());
                intent.putExtra("tagihan", modellist.get(postition).getTagihan());
                intent.putExtra("jatuhtempo", modellist.get(postition).getJatuhtempo());
                intent.putExtra("sisabayar", modellist.get(postition).getSisabayar());

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
            for (ModelDaftarpinjaman modelDaftarsimpanan : arrayList){
                if (modelDaftarsimpanan.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modellist.add(modelDaftarsimpanan);
                }
            }
        }
        notifyDataSetChanged();
    }

}