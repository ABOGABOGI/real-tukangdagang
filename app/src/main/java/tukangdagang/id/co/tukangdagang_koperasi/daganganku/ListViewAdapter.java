package tukangdagang.id.co.tukangdagang_koperasi.daganganku;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.BeritaKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.MainActivity;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.daganganku.Model;

public class ListViewAdapter extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<tukangdagang.id.co.tukangdagang_koperasi.daganganku.Model> modellist;
    ArrayList<tukangdagang.id.co.tukangdagang_koperasi.daganganku.Model> arrayList;

    //constructor
    public ListViewAdapter(Context context, List<tukangdagang.id.co.tukangdagang_koperasi.daganganku.Model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<tukangdagang.id.co.tukangdagang_koperasi.daganganku.Model>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mTitleTv;
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
        tukangdagang.id.co.tukangdagang_koperasi.daganganku.ListViewAdapter.ViewHolder holder;
        if (view==null){
            holder = new tukangdagang.id.co.tukangdagang_koperasi.daganganku.ListViewAdapter.ViewHolder();
            view = inflater.inflate(R.layout.row_daganganku, null);

            holder.mTitleTv = view.findViewById(R.id.title_daganganku);
            holder.mIconIv = view.findViewById(R.id.icon_daganganku);

            view.setTag(holder);

        }
        else {
            holder = (tukangdagang.id.co.tukangdagang_koperasi.daganganku.ListViewAdapter.ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(postition).getTitle());
        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());
        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code later
                if (modellist.get(postition).getTitle().equals("Daftar Barang")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, MainActivity.class);
//                    intent.putExtra("actionBarTitle", "Battery");
//                    intent.putExtra("contentTv", "This is Battery detail...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(postition).getTitle().equals("Daftar Kategori Barang")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, MainActivity.class);
//                    intent.putExtra("actionBarTitle", "Cpu");
//                    intent.putExtra("contentTv", "This is Cpu detail...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(postition).getTitle().equals("Transaksi Penjualan")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, MainActivity.class);
//                    intent.putExtra("actionBarTitle", "Display");
//                    intent.putExtra("contentTv", "This is Display detail...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(postition).getTitle().equals("Transaksi Penerimaan Barang")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, MainActivity.class);
//                    intent.putExtra("actionBarTitle", "Memory");
//                    intent.putExtra("contentTv", "This is Memory detail...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(postition).getTitle().equals("Jam Oprasional")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, MainActivity.class);
//                    intent.putExtra("actionBarTitle", "Sensor");
//                    intent.putExtra("contentTv", "This is Sensor detail...");
                    mContext.startActivity(intent);
                }
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