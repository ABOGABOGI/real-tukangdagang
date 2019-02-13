package tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan;

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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.BeritaKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.DaftarAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.Simpanan;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.path;

public class AdapterDaftarsimpanan extends BaseAdapter{

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<ModelDaftarsimpanan> modellist;
    ArrayList<ModelDaftarsimpanan> arrayList;

    //constructor
    public AdapterDaftarsimpanan(Context context, List<ModelDaftarsimpanan> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ModelDaftarsimpanan>();
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
            view = inflater.inflate(R.layout.list_daftarsimpanan, null);

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
        String waktu = modellist.get(postition).getWaktu();
        if(modellist.get(postition).getWaktu().equals("null")){
            waktu ="0";
        }
        holder.mDescTv.setText(waktu+" Hari | "+formatRupiah.format((double) Double.valueOf(modellist.get(postition).getJumlah())));

        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(path + modellist.get(postition).getIcon())

                .into(holder.mIconIv);


        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Simpanan.class);
                intent.putExtra("nama", modellist.get(postition).getNama());
                intent.putExtra("avatar", modellist.get(postition).getAvatar());
                intent.putExtra("noAnggota", modellist.get(postition).getId());
                intent.putExtra("pokok", modellist.get(postition).getPokok());
                intent.putExtra("wajib", modellist.get(postition).getWajib());
                intent.putExtra("sukarela", modellist.get(postition).getSukarela());

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
            for (ModelDaftarsimpanan modelDaftarsimpanan : arrayList){
                if (modelDaftarsimpanan.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modellist.add(modelDaftarsimpanan);
                }
            }
        }
        notifyDataSetChanged();
    }

}